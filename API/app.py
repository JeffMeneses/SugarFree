from flask import Flask
import pymongo

app = Flask(__name__)
app.secret_key = b'U\x16~\xa9h@\x8e\xb3\xe7\xd8\xf54\xce\xed\x1c\r'

# Database
client = pymongo.MongoClient('localhost', 27017)
db = client.user_login_system

# Routes
from user import routes
from controllers import recipes, meals, recipeMenus
from recommendationSystem import contentBased

@app.route('/')
def home():
    return "Home"