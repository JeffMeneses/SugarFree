from flask import Flask, jsonify, request
from app import app, db
from pymongo import TEXT
import json
from bson import json_util
import uuid

# PLACE HOLDER
recipes_db = [
    {"id": 0, "title": "Torta salgada", "intructions": "Um texto passo a passo da receita"},
    {"id": 1, "title": "Macarrão", "intructions": "Um texto passo a passo da receita"}
]

invalidIngredients = ["açúcar", "arroz branco", "farinha de trigo", "refrigerante",
                     "leite integral", "iogurte integral", "enlatado", "chocolate", "doce de leite"]

def checkIngredients(ingredients):
    ok = True

    for item in invalidIngredients:
        if item in ingredients:
            ok = False
            break

    if ok == True:
        print("receita inserida")
        return 200

    print("ingrediente indevido encontrado")
    return 400

@app.route('/recipes', methods=['GET', 'POST'])
def recipes():
    if request.method == 'GET':      
        recipes = db.recipes

        output = []

        for item in recipes.find():
            output.append({
                'title': item['title'],
                'instructions': item['instructions'],
                'image': item['image'],
                'avgRating': item['avgRating'],
                'countRating': item['countRating'],
                'category': item['category'],
                'tags': item['tags']
            })

        return jsonify(output), 200
    
    if request.method == 'POST':
        recipe = {
            "_id": uuid.uuid4().hex,
            "title": request.json.get('title'),
            "ingredients": request.json.get('ingredients'),
            "instructions": request.json.get('instructions'),
            "category": request.json.get('category'),
            "tags": request.json.get('tags'),
            "image": request.json.get('image'),
            "avgRating": 0,
            "countRating": 0,
            "ratings": []
        }

        if checkIngredients(recipe["ingredients"]) == 400:
            return jsonify({"error": "Hum, ingrediente indevido encontrado.", "statusCode": 400}), 400

        if db.recipes.insert_one(recipe):
            return jsonify({"success": "Receita inserida com sucesso.", "statusCode": 200}), 200

        return jsonify({"error": "A inserção de receita falhou.", "statusCode": 400}), 400

@app.route('/recipesCategory/<string:category>', methods=['GET'])
def recipesCategory(category):
    recipes_list  = list(db.recipes.find({"category": category}))
    return json.dumps(recipes_list, default=json_util.default)

@app.route('/recipeId/<string:id>', methods=['GET'])
def recipeId(id):
    recipes_list  = list(db.recipes.find({"_id": id}))
    return json.dumps(recipes_list, default=json_util.default)

@app.route('/recipeTitle/<string:title>', methods=['GET'])
def recipeTitle(title):
    recipes_list  = list(db.recipes.find({"title": title}))

    if recipes_list:
        return json.dumps(recipes_list, default=json_util.default)
    return jsonify({"error": "Ops, essa receita não está disponpivel no app", "statusCode": 400}), 400

@app.route('/partialRecipeTitle/<string:title>', methods=['GET'])
def partialRecipeTitle(title):

    db.recipes.create_index([("$**", TEXT)])
    recipes_list  = list(db.recipes.find({"$text":{"$search":"\""+title+"\""}}))

    if recipes_list:
        return json.dumps(recipes_list, default=json_util.default)
    return jsonify({"error": "Ops, essa receita não está disponpivel no app", "statusCode": 400}), 400 

def recipesIdList(idList):
    recipes_list  = list(db.recipes.find({"_id":{"$in": idList}}))
    return json.dumps(recipes_list, default=json_util.default)

@app.route('/randomNRecipes', methods=['GET'])
def randomNRecipes():
    recipes_list  = list(db.recipes.aggregate([{"$sample": {"size": 10}}]))

    if recipes_list:
        return json.dumps(recipes_list, default=json_util.default)
    return jsonify({"error": "Ops, ocorreu um problema", "statusCode": 400}), 400 