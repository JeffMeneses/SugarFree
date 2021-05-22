from flask import Flask, jsonify, request
from app import db
import uuid

class User:

    def signup(self):

        user = {
            "_id": uuid.uuid4().hex,
            "name": request.json.get('name'),
            "email": request.json.get('email'),
            "password": request.json.get('password')
        }

        if db.users.find_one({"email": user['email']}):
            return jsonify({"error": "O email já está em uso."}), 400

        if db.users.insert_one(user):
            return jsonify(user), 200

        return jsonify({"error": "O cadastro falhou."}), 400