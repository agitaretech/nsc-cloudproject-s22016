from blob import uploadBlob, deleteBlob
from verify_oauth import verifyOauth
from make_meta_data import makeMetadata
from get_records import getRecords
from delete_record import deleteRecord
from static.app_json import *

def uploadImageJSON(username, blob, filename, token, secret, tags):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
        
    rtnBlobList = uploadBlob(username, blob, filename)
    if len(rtnBlobList) < 2:
        return upload_image_blob_error_json


    rtnDocumentdbMsg = makeMetadata(username, filename, tags.split(), rtnBlobList[0], rtnBlobList[1])  
    if rtnDocumentdbMsg  != 'success':
        return upload_image_db_error_json
        
    return upload_image_success_json


def deleteImageJSON(blobURL, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
    
    rtnBlobList = deleteBlob(blobURL)
    if rtnBlobList != 'success':
        return delete_image_blob_error_json

    rtnDbMsg = deleteRecord(blobURL)
    if rtnDbMsg != 'success':
        return delete_image_db_error_json

    return delete_image_success_json

def getImagesJSON(timestamp, prev, tags, username, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json

    rec_json = getRecords(username, timestamp, prev, tags)
    if rec_json == 'error':
        return get_image_error_json

    rtn_json = {'status': 'success', 'imgs': rec_json}         
    return rtn_json

def updateTagsJSON(blobURL, tags, username, token, secret):
    oauth_verify_code = verifyOauth(token, secret)
    if oauth_verify_code != 200:
        return oauth_error_json
    
    return update_tags_success_json

def main():
    pass
    #print(uploadImageJSON('fin', '/Users/rjhunter/Desktop/bridge.jpg', 'Todd','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF','love happy hell'))
    #print(deleteImageJSON('https://ad440rjh.blob.core.windows.net/fin/2016-03-08041507752291_Todd','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))
    #print(getImagesJSON(0,'false',['love'], 'fin','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))
    #print(updateTagsJSON("www.blob.com", ['fun','luck'],'fred','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))


# call main
if __name__ == "__main__":
    main()
