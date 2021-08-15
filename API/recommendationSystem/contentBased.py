from controllers.recipes import recipesIdList
from flask import Flask, jsonify, request
import json
import pandas as pd
from pandas import DataFrame
from app import app, db
import numpy as np

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import sigmoid_kernel

# Crating dataFrame
recipes_df = DataFrame(list(db.recipes.find({})))
recipes_df = recipes_df.drop(columns=['category', 'tags', 'image', 'likes'])
recipes_df = recipes_df.replace(r'\n', ' ', regex=True)

# Content Based Recommendation System
tfv = TfidfVectorizer(min_df=3,  max_features=None, 
            strip_accents='unicode', analyzer='word',token_pattern=r'\w{1,}',
            ngram_range=(1, 3),
            stop_words = 'english')

# Filling NaNs with empty string
recipes_df['ingredients'] = recipes_df['ingredients'].fillna('')

# Fitting the TF-IDF on the 'overview' text
tfv_matrix = tfv.fit_transform(recipes_df['ingredients'])

# Compute the sigmoid kernel
sig = sigmoid_kernel(tfv_matrix, tfv_matrix)

# Reverse mapping of indices and movie titles
indices = pd.Series(recipes_df.index, index=recipes_df['_id']).drop_duplicates()

def get_similar_recipes(title, sig=sig):
    # Get the index corresponding to title
    idx = indices[title]

    # Get the pairwsie similarity scores 
    sig_scores = list(enumerate(sig[idx]))

    # Sort the recipes 
    sig_scores = sorted(sig_scores, key=lambda x: x[1], reverse=True)

    # Scores of the 10 most similar recipes
    sig_scores = sig_scores[1:11]

    # Recipe indices
    recipe_indices = [i[0] for i in sig_scores]

    # Top 10 most similar recipes
    return sig_scores

@app.route('/recommendation', methods=['POST'])
def recommendation():

    if request.method == 'POST':
        userLikedRecipes = request.json.get('userLikedRecipes')

    similar_recipes = pd.DataFrame()
    for recipe in userLikedRecipes:
        similar_recipes = similar_recipes.append(get_similar_recipes(recipe))

    similar_recipes.columns = ['id', 'score']
    similar_recipes = similar_recipes.groupby(["id"])["score"].sum().nlargest(5)
    similar_recipes = similar_recipes.drop(indices[userLikedRecipes], errors='ignore')

    recipe_indices = similar_recipes.index
    result = recipes_df['_id'].iloc[recipe_indices]
    
    resultRecipes = recipesIdList(result.values.tolist())

    return jsonify({"success": "Recomendação gerada com sucesso.", "statusCode": 200, "result": json.loads(resultRecipes)}), 200