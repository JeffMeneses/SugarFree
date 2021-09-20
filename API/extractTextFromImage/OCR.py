from controllers.recipes import recipeId
from flask import Flask, jsonify, request
from app import app, db
import re

import pytesseract as tess
tess.pytesseract.tesseract_cmd = r'C:\Users\jeffe\AppData\Local\Programs\Tesseract-OCR\tesseract.exe'
from PIL import Image
import base64
import io

IngredientsTitleToken = ["ingredientes", "ingredientes:", "ingredientes :"]
InstructionsTitleToken = ["instruções", "instruções:", "instruções :", "modo de preparo", "modo de preparo:", "modo de preparo :", "preparo", "preparo:", "preparo :", "preparação"
                        , "preparação:", "preparação :"]

@app.route('/extractTextFromImage', methods=['POST'])
def extractTextFromImage():

    recipeInfo = {
            "title": '',
            "ingredients": '',
            "instructions": '',
        }

    if request.method == 'POST':
        recipeImages = request.json.get('recipeImages')
    
    for item in recipeImages:
        rawText = tesseractImgToTxt(item['image'])
        formattedText = formatText(rawText)

        if formattedText['title']:
            recipeInfo['title'] = formattedText['title']
        if formattedText['ingredients']:
            recipeInfo['ingredients'] = formattedText['ingredients']
        if formattedText['instructions']:
            recipeInfo['instructions'] = formattedText['instructions']

    print(recipeInfo)

    return jsonify({"success": "Texto extraído com sucesso.", "statusCode": 200, "result": recipeInfo}), 200


def tesseractImgToTxt(img64):

    image_string = io.BytesIO(base64.b64decode(img64))
    img = Image.open(image_string)
    text = tess.image_to_string(img, lang='por', config="--psm 6")

    return text

def formatText(rawText):

    recipeInfo = {
            "title": '',
            "ingredients": '',
            "instructions": '',
        }

    lines = rawText.splitlines()
    lines = list(filter(None, lines))

    title = ''
    for line in lines:
        if line.lower() in IngredientsTitleToken:
            recipeInfo['ingredients'] = getIngredients(lines[lines.index(line)+1:])
        elif line.lower() in InstructionsTitleToken:
            recipeInfo['instructions'] = getInstructions(lines[lines.index(line)+1:])
        elif lines.index(line) == 0:
            recipeInfo['title'] = line

    title = title.lstrip()

    return recipeInfo

def getIngredients(lines):
    ingredientsStr = '\n'.join(lines)
    ingredientsStr = re.sub(r'Y%', r'1/2', ingredientsStr)

    #print("\n[Ingredientes]\n",ingredientsStr)
    return ingredientsStr

def getInstructions(lines):
    instructionsStr = ' '.join(lines)
    instructionsStr = re.sub(';', ';\n', instructionsStr)

    #print("\n[Instruções]\n",instructionsStr)
    return instructionsStr