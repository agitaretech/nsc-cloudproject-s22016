from flask import Flask
from flask import request
import tasks

app = Flask(__name__)


@app.route("/getImage", methods=['GET'])
def getImage():
        
    return request.args.get('email')
    

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


