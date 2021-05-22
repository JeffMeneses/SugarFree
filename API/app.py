from flask import Flask
import pymongo

app = Flask(__name__)

# Database
client = pymongo.MongoClient('localhost', 27017)
db = client.user_login_system

# Routes
from user import routes

@app.route('/')
def home():
    return "Home"

#if __name__=="__main__":
#    app.run(debug=True)