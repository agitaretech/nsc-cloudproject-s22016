from flask import Flask
from flask import request
from flask import jsonify
from api_methods import *

app = Flask(__name__)


@app.route("/getImages", methods=['GET'])
def getImages():
    timestamp = request.args.get('timestamp')
    tags = request.args.get('tags')
    username = request.args.get('username')
    token = request.args.get('token')
    secret = request.args.get('secret')
    #prev parameter
    rtn_json = getImagesJSON(timestamp, tags, username, token, secret)
    return jsonify(request=rtn_json)

@app.route("/uploadImage", methods=['POST'])
def uploadImage():
    username = request.args.get('username')
    blob = request.args.get('blob')
    filename = request.args.get('filename')
    token = request.args.get('token')
    secret = request.args.get('secret')
    tags = request.args.get('tags')
    
    rtn_json = uploadImageJSON(username, blob, filename, token, secret, tags)
    return jsonify(request=rtn_json)

@app.route("/deleteImage", methods=['DELETE'])
def deleteImage():
    username = request.args.get('username')
    blobURL = request.args.get('blobURL')
    token = request.args.get('token')
    secret = request.args.get('secret')
    #remove username
    rtn_json = deleteImageJSON(username, blobURL, token, secret)
    return jsonify(request=rtn_json)

@app.route("/updateTags", methods=['PUT'])
def updateTags():
    blobURL = request.args.get('blobURL')
    tags = request.args.get('tags')
    username = request.args.get('username')
    token = request.args.get('token')
    secret = request.args.get('secret')
    #remove username
    
    rtn_json = updateTagsJSON(blobURL, tags, username, token, secret)
    return jsonify(request=rtn_json)

if __name__ == "__main__":
    app.debug=True 
    app.run()


