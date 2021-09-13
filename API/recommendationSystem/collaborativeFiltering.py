import pandas as pd
from controllers.recipes import recipesIdList
from scipy import sparse
from sklearn.metrics.pairwise import cosine_similarity

from pandas import DataFrame
from app import app, db
from flask import Flask, jsonify, request
import json

# Crating dataFrame
recipeList = DataFrame(list(db.recipes.find({}, {"_id":1})))
userList = DataFrame(list(db.users.find({}, {"_id":1})))
ratingsByRecipe = (list(db.recipes.find({}, {"_id":1, "ratings":1})))

ratings = DataFrame(0, index=userList['_id'], columns=recipeList['_id'])
for recipe in ratingsByRecipe:
  for userRating in recipe['ratings']:
    ratings.at[userRating['userID'], recipe['_id']] = userRating['rating']

ratings = ratings.fillna(0)

# Standardizing rating values
def standardize(row):
    new_row = (row - row.mean())/(row.max() - row.min()+ 10**-100)
    return new_row

ratings_std = ratings.apply(standardize)

# Creating similarity DataFrame
item_similarity = cosine_similarity(ratings_std.T)
item_similarity_df = pd.DataFrame(item_similarity,index=ratings.columns,columns=ratings.columns)
#print(item_similarity_df)

def get_similar_recipes(recipe_name, user_rating):
    similar_score = item_similarity_df[recipe_name]*(user_rating-2.5)
    similar_score = similar_score.sort_values(ascending=False)
    return similar_score

@app.route('/cfRecommendation', methods=['POST'])
def cfRecommendation():

    if request.method == 'POST':
        userRatingRecipes = request.json.get('userRatingRecipes')
        #print("userRatingRecipes: ",userRatingRecipes)

    similar_recipes = pd.DataFrame()
    for recipe in userRatingRecipes:
        similar_recipes = similar_recipes.append(get_similar_recipes(recipe['_id'], recipe['rating']))

    similar_recipes = similar_recipes.sum().sort_values(ascending=False)

    for recipe in userRatingRecipes:
        similar_recipes = similar_recipes.drop(recipe['_id'], errors='ignore')

    #print(similar_recipes)
    similar_recipes = similar_recipes[similar_recipes > 0]
    similar_recipes = similar_recipes[:5]

    recipe_indices = similar_recipes.index
    result = recipe_indices.values.tolist()

    if(result):
        resultRecipes = recipesIdList(result)
        return jsonify({"success": "Recomendação gerada com sucesso.", "statusCode": 200, "result": json.loads(resultRecipes)}), 200

    return jsonify({"error": "Ops, a recomendação não pôde ser gerada.", "statusCode": 400}), 400