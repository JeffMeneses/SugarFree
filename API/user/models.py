from flask import Flask, jsonify, request, session
from app import db
import uuid

class User:

    def startSession(self, user):
        del user['password']
        session['loggedIn'] = True
        session['user'] = user
        return jsonify(user), 200


    def signup(self):

        user = {
            "_id": uuid.uuid4().hex,
            "name": request.json.get('name'),
            "email": request.json.get('email'),
            "password": request.json.get('password'),
            "selectedRecipes": request.json.get('selectedRecipes')
        }

        if db.users.find_one({"email": user['email']}):
            return jsonify({"error": "O email já está em uso.", "statusCode": 400}), 400

        if db.users.insert_one(user):
            self.startSession(user)
            return jsonify({"success": "Cadastro concluído com sucesso.", "statusCode": 200}), 200

        return jsonify({"error": "O cadastro falhou.", "statusCode": 400}), 400
    
    def signout(self):
        session.clear()
        return jsonify({"success": "Sessão finalizada."}), 200

    def login(self):

        user = db.users.find_one(
            {"email": request.json.get('email')}
        )

        if user and user['password']==request.json['password']:
            self.startSession(user)
            return jsonify({"userID": user['_id'], "selectedRecipes": user['selectedRecipes']}), 200

        return jsonify({"error": "Credenciais de login inválidas."}), 401