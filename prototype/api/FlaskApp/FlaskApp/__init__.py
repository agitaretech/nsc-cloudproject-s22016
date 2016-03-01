from flask import Flask
from flask import request
from flask import jsonify
from azure_components.api_methods import *

app = Flask(__name__)


@app.route("/getImages", methods=['GET'])
def getImages():
    timestamp = request.headers.get('timestamp')
    tags = request.headers.get('tags')
    username = request.headers.get('username')
    token = request.headers.get('token')
    secret = request.headers.get('secret')
    prev = request.headers.get('prev')
    if opt_param is None:
        prev = false
    rtn_json = getImagesJSON(timestamp, prev, tags, username, token, secret)
    return jsonify(request=rtn_json)

@app.route("/uploadImage", methods=['POST'])
def uploadImage():
    username = request.headers.get('username')
    blob = request.headers.get('blob')
    filename = request.headers.get('filename')
    token = request.headers.get('token')
    secret = request.headers.get('secret')
    tags = request.headers.get('tags')
    
    rtn_json = uploadImageJSON(username, blob, filename, token, secret, tags)
    return jsonify(request=rtn_json)

@app.route("/deleteImage", methods=['DELETE'])
def deleteImage():
    blobURL = request.headers.get('blobURL')
    token = request.headers.get('token')
    secret = request.headers.get('secret')
    rtn_json = deleteImageJSON(username, blobURL, token, secret)
    return jsonify(request=rtn_json)

@app.route("/updateTags", methods=['PUT'])
def updateTags():
    blobURL = request.headers.get('blobURL')
    tags = request.headers.get('tags')
    token = request.headers.get('token')
    secret = request.headers.get('secret')

    
    rtn_json = updateTagsJSON(blobURL, tags, username, token, secret)
    return jsonify(request=rtn_json)

if __name__ == "__main__":
    app.debug=True 
    app.run()


