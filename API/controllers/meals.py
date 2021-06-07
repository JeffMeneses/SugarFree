from flask import Flask, jsonify, request
from app import app, db
import json
from bson import json_util
import uuid

# PLACE HOLDER
meals_db = [
    {
        "idUser": 1,
        "name": "José apenas",
        "mealData": 
        {
            "morningSnack": [
                
            ],
            "breakfast": [
                {
                    "foodName": "cenoura",
                    "quantity": "50",
                    "unit": "grama(s)"
                },
                {
                    "foodName": "leite",
                    "quantity": "500",
                    "unit": "mililitro(s)"
                }
            ],
            "lunch": [
                {
                    "foodName": "arroz",
                    "quantity": "300",
                    "unit": "grama(s)"
                },
                {
                    "foodName": "carne",
                    "quantity": "300",
                    "unit": "grama(s)"
                }
            ],
            "afternoonSnack": [
                
            ],
            "dinner": [
                {
                    "foodName": "cenoura",
                    "quantity": "50",
                    "unit": "gramas"
                },
                {
                    "foodName": "leite",
                    "quantity": "500",
                    "unit": "mililitro(s)"
                }
            ],
            "eveningSnack": [
                
            ]
        }
    },
    {
        "idUser": 2,
        "name": "Julia apenas",
        "mealData": 
        {
            "morningSnack": [
                
            ],
            "breakfast": [
                {
                    "foodName": "brócolis",
                    "quantity": "50",
                    "unit": "grama(s)"
                },
                {
                    "foodName": "suco",
                    "quantity": "500",
                    "unit": "mililitro(s)"
                }
            ],
            "lunch": [
                {
                    "foodName": "feijão",
                    "quantity": "300",
                    "unit": "grama(s)"
                },
                {
                    "foodName": "frango",
                    "quantity": "300",
                    "unit": "grama(s)"
                }
            ],
            "afternoonSnack": [
                
            ],
            "dinner": [
                {
                    "foodName": "cenoura",
                    "quantity": "50",
                    "unit": "gramas"
                },
                {
                    "foodName": "leite",
                    "quantity": "500",
                    "unit": "mililitro(s)"
                }
            ],
            "eveningSnack": [
                
            ]
        }
    }
]

@app.route('/meals', methods=['POST'])
def meals():
    if request.method == 'POST':
        meal = {
        "idUser": request.json.get('idUser'),
        "name": request.json.get('name'),
        "morningSnack": [],
        "breakfast": [],
        "lunch": [],
        "afternoonSnack": [],
        "dinner": [],
        "eveningSnack": []
    }

        if db.meals.insert_one(meal):
            return jsonify({"success": "Refeição inserida com sucesso.", "statusCode": 200}), 200

        return jsonify({"error": "A inserção de refeição falhou.", "statusCode": 400}), 400

@app.route('/food', methods=['POST'])
def food():

    mealUpdate = db.meals.update_one(
    {"idUser": int(request.json.get('idUser'))}, 
    {
        "$push": {
            request.json.get('mealName'): 
            {
                "idFood": uuid.uuid4().hex,
                "foodName": request.json.get('foodName'),
                "quantity": request.json.get('quantity'),
                "unit": request.json.get('unit')
            }
        }
    })

    print(request.json.get('idUser'), request.json.get('mealName'), request.json.get('foodName'), request.json.get('quantity'), request.json.get('unit'))

    if mealUpdate.modified_count:
        return jsonify({"success": "Alimento inserido com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A inserção de alimento falhou.", "statusCode": 400}), 400

@app.route('/mealCategory/<int:idUser>/<string:category>', methods=['GET'])
def mealCategory(idUser, category):
    meals_list  = list(db.meals.find({"idUser": idUser}))
    categoryResponse = json.loads(json.dumps(meals_list, default=json_util.default))

    return jsonify(categoryResponse[0][category])

@app.route('/removeFood/<int:idUser>/<string:category>/<string:idFood>', methods=['GET'])
def removeFood(idUser, category, idFood):
    mealUpdate = db.meals.update_one(
        {"idUser": idUser},
        { 
            "$pull": {
                category: 
                {
                    "idFood": idFood
                }
            }
        })

    if mealUpdate.modified_count:
        return jsonify({"success": "Alimento removido com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A remoção de alimento falhou.", "statusCode": 400}), 400