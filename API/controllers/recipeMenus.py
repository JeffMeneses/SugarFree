from flask import Flask, jsonify, request
from app import app, db
import json
from bson import json_util
import uuid

@app.route('/recipeMenu', methods=['POST'])
def recipeMenu():
    if request.method == 'POST':
        recipeMenu = {
        "idRecipeMenu": uuid.uuid4().hex,
        "idUser": int(request.json.get('idUser')),
        "name": request.json.get('name'),
        "weekDays": "",
        "breakfast": [],
        "lunch": [],
        "dinner": []
    }

        if db.recipeMenus.insert_one(recipeMenu):
            return jsonify({"success": "Cardápio inserido com sucesso.", "statusCode": 200}), 200

        return jsonify({"error": "A inserção de cardápio falhou.", "statusCode": 400}), 400

@app.route('/recipeMenu/<int:idUser>', methods=['GET'])
def recipeMenuByIdUser(idUser):
    recipeMenu_list  = list(db.recipeMenus.find({"idUser": idUser}))
    return json.dumps(recipeMenu_list, default=json_util.default)

@app.route('/meal', methods=['POST'])
def meal():

    RecipeMenuUpdate = db.recipeMenus.update_one(
    {"idRecipeMenu": request.json.get('idRecipeMenu')}, 
    {
        "$push": {
            request.json.get('category'): 
            {
                "idMeal": uuid.uuid4().hex,
                "mealName": request.json.get('mealName'),
                "type": request.json.get('type')
            }
        }
    })

    if RecipeMenuUpdate.modified_count:
        return jsonify({"success": "Refeição inserida com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A inserção de refeição falhou.", "statusCode": 400}), 400

@app.route('/mealCategory/<string:idRecipeMenu>/<string:category>', methods=['GET'])
def mealCategory(idRecipeMenu, category):
    meals_list  = list(db.recipeMenus.find({"idRecipeMenu": idRecipeMenu}))
    categoryResponse = json.loads(json.dumps(meals_list, default=json_util.default))

    return jsonify(categoryResponse[0][category])

@app.route('/removeRecipeMenu/<string:idRecipeMenu>', methods=['GET'])
def removeRecipeMenu(idRecipeMenu):
    recipeMenuUpdate = db.recipeMenus.delete_one(
        {"idRecipeMenu": idRecipeMenu})
        
    print(recipeMenuUpdate.deleted_count)

    if recipeMenuUpdate.deleted_count:
        return jsonify({"success": "Cardápio removido com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A remoção de cardápio falhou.", "statusCode": 400}), 400

@app.route('/shareRecipeMenu/<string:idRecipeMenu>', methods=['GET'])
def shareRecipeMenu(idRecipeMenu):
    meals_list  = list(db.recipeMenus.find({"idRecipeMenu": idRecipeMenu}))
    recipeMenuItem = json.loads(json.dumps(meals_list, default=json_util.default))

    return jsonify(recipeMenuItem)

@app.route('/removeMeal/<string:idRecipeMenu>/<string:idMeal>/<string:category>', methods=['GET'])
def removeMeal(idRecipeMenu, idMeal, category):
    recipeMenuUpdate = db.recipeMenus.update_one(
        {"idRecipeMenu": idRecipeMenu},
        { 
            "$pull": {
                category: 
                {
                    "idMeal": idMeal
                }
            }
        })

    print(recipeMenuUpdate.modified_count)

    if recipeMenuUpdate.modified_count:
        return jsonify({"success": "Refeição removida com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A remoção de refeição falhou.", "statusCode": 400}), 400

@app.route('/updateWeekDays', methods=['POST'])
def updateWeekDays():

    RecipeMenuUpdate = db.recipeMenus.update_one(
    {"idRecipeMenu": request.json.get('idRecipeMenu')}, 
    {
        "$set": {
            "weekDays": request.json.get('weekDays'),
        }
    })

    if RecipeMenuUpdate.modified_count:
        return jsonify({"success": "Cardápio atualizada com sucesso.", "statusCode": 200}), 200

    return jsonify({"error": "A atualização do cardápio falhou.", "statusCode": 400}), 400