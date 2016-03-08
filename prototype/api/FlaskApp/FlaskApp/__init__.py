from flask import Flask
from flask import request
from flask import jsonify
from azure_components.api_methods import *

#declare flask app
app = Flask(__name__)

#route for get images call
@app.route("/getImages", methods=['GET'])
def getImages():
    #header variables for get api method
    timestamp = request.headers.get('timestamp')
    if timestamp is None:
        timestamp = 0
    tags = request.headers.get('tags')
    if tags is None:
        tags_new = []
    else:
        tags_new = tags.split()
    username = request.headers.get('username')
    token = request.headers.get('token')
    secret = request.headers.get('secret')
    prev = request.headers.get('prev')
    # checks if prev variable is set
    if prev is None:
        # updates variable to false
        prev = 'false'
    #calls get image method to get JSON from api method (get)
    rtn_json = getImagesJSON(timestamp, prev, tags_new, username, token, secret)
    # return json request
    return jsonify(request=rtn_json)

#route for uploading images
@app.route("/uploadImage", methods=['POST'])
def uploadImage():
    #header variables for post api method
    username = request.headers.get('username')
    blob = request.headers.get('blob')
    filename = request.headers.get('filename')
    token = request.headers.get('token')
    secret = request.headers.get('secret')
    tags = request.headers.get('tags')

    #calls upload image method (post)
    rtn_json = uploadImageJSON(username, blob, filename, token, secret, tags)
    #returns json succes or error json message
    return jsonify(request=rtn_json)

#route for deleting images
@app.route("/deleteImage", methods=['DELETE'])
def deleteImage():
    #header variables for delete api method
    blobURL = request.headers.get('blobURL')
    token = request.headers.get('token')
    secret = request.headers.get('secret')

    #calls delete image method (delete)
    rtn_json = deleteImageJSON(blobURL, token, secret)
    #returns json succes or error json message
    return jsonify(request=rtn_json)

#route for updating tags
@app.route("/updateTags", methods=['PUT'])
def updateTags():
    #header variables for put api method
    blobURL = request.headers.get('blobURL')
    tags = request.headers.get('tags')
    token = request.headers.get('token')
    secret = request.headers.get('secret')

    #calls update tags method (put)
    rtn_json = updateTagsJSON(blobURL, tags, username, token, secret)
    #returns json succes or error json message
    return jsonify(request=rtn_json)

#initializes app and runs debug
if __name__ == "__main__":
    app.debug=True 
    app.run()


