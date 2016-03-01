from blob import uploadBlob, deleteBlob
from verify_oauth import verifyOauth
from make_meta_data import makeMetadata
from static.app_json import *

def uploadImageJSON(username, blob, filename, token, secret, tags):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
        
    rtnBlobList = uploadBlob(username, blob, filename)
    if len(rtnBlobList) < 2:
        return upload_image_blob_error_json


    rtnDocumentdbMsg = makeMetadata(username, filename, tags, rtnBlobList[0], rtnBlobList[1])  
    if rtnDocumentdbMsg  != "success":
        return upload_image_db_error_json
        
    return upload_image_success_json


def deleteImageJSON(username, blobURL, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
    
    rtnBlobList = deleteBlob(blobURL)
    if rtnBlobList[0]  != "success":
        return delete_image_blob_error_json

    return delete_image_success_json

def getImagesJSON(timestamp, tags, username, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json

    rtnJSON = {'imgs' : 'get_images_placeholder'}
    return rtnJSON

def updateTagsJSON(blobURL, tags, username, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
    
    return update_tags_success_json

def main():
    print(uploadImageJSON('fin', '/Users/rjhunter/Desktop/bridge.jpg', 'Todd','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF','fun'))
    print(deleteImageJSON('fin', "https://ad440rjh.blob.core.windows.net/fin/2016-02-21130446459836_Todd",'4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))
    print(getImagesJSON('10/2/15 4:40AM',['fun','luck'], 'fred','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))
    print(updateTagsJSON("www.blob.com", ['fun','luck'],'fred','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))


# call main
if __name__ == "__main__":
    main()
