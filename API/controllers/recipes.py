from flask import Flask, jsonify, request
from app import app

# PLACE HOLDER
recipes_db = [
    {"id": 0, "title": "Torta salgada", "intructions": "Um texto passo a passo da receita"},
    {"id": 1, "title": "Macarrão", "intructions": "Um texto passo a passo da receita"}
]

@app.route('/recipes', methods=['GET', 'POST'])
def recipes():
    if request.method == 'GET':
        return jsonify(recipes_db)
    
    if request.method == 'POST':
        recipes_db.append(request.json)
        return jsonify(recipes_db)