from flask import Flask, jsonify, request
from app import app, db
import uuid

# PLACE HOLDER
recipes_db = [
    {"id": 0, "title": "Torta salgada", "intructions": "Um texto passo a passo da receita"},
    {"id": 1, "title": "Macarrão", "intructions": "Um texto passo a passo da receita"}
]

@app.route('/recipes', methods=['GET', 'POST'])
def recipes():
    if request.method == 'GET':      
        recipes = db.recipes

        output = []

        for item in recipes.find():
            output.append({'title': item['title'], 'instructions': item['instructions']})

        return jsonify(output), 200
    
    if request.method == 'POST':
        recipe = {
            "_id": uuid.uuid4().hex,
            "title": request.json.get('title'),
            "instructions": request.json.get('instructions')
        }

        if db.recipes.insert_one(recipe):
            return jsonify({"success": "Receita inserida com sucesso.", "statusCode": 200}), 200

        return jsonify({"error": "A inserção de receita falhou.", "statusCode": 400}), 400