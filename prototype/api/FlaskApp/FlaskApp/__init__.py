from flask import Flask
from flask import request

app = Flask(__name__)


@app.route("/getImages", methods=['GET'])
def getImage():
    rtnJson = getImages()
    return rtnJson;
    #return request.args.get('email')
    

@app.route("/uploadImage", methods=['GET','POST'])
def insertImage():
    return "Insert Image!"

@app.route("/deleteImage", methods=['GET','DELETE'])
def deleteImage():
    return "Delete Image!"

@app.route("/updateTags", methods=['GET','PUT'])
def updateTags():
    return "Update Tags!"


if __name__ == "__main__":
    app.debug=True 
    app.run()


