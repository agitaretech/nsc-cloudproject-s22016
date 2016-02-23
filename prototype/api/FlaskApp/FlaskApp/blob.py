from azure.storage.blob import BlobService
import datetime
import string
from app_keys import blob_account_name, blob_account_key


accountName = blob_account_name
accountKey = blob_account_key

blob_service = BlobService(accountName, accountKey)
uploaded = False

def uploadBlob(username, file, filename, token, secret):
    try:
        global uploaded
        username = username.lower()
        returnList = []

        blob_service.create_container(username, x_ms_blob_public_access='container')

        #datetime gives the system's current datetime, I convert to string in order to .replace
        #characters that wouldn't work in a URL like ':','.', and ' '
        time = datetime.datetime.utcnow()
        timeReplaced = str(time).replace(':','').replace('.','').replace(' ','') + "_" + filename

        URLstring = "https://" + accountName + ".blob.core.windows.net/" + username + "/" + timeReplaced 

        uploaded = False
        blob_service.put_block_blob_from_path(
            username,
            timeReplaced,
            file,
            x_ms_blob_content_type='image/png',
            progress_callback=progress_callback
            )
        
        #if upload is successful, return a list with the timestamp and the final URL
        #else return an empty list

        if uploaded:
            returnList = [time, URLstring]
            return returnList
        else:
            return returnList["error"]

    except Exception:
        return ["error"]

def deleteBlob(username, blobURL):
    try:
        username = username.lower()
        exploded = blobURL.split("/")
        blob_service.delete_blob(username, exploded[len(exploded)-1])
        return ["success"]
    except Exception:
        return ["error"]

def listBlobs(username):
    username = username.lower()
    blobs = []
    marker = None
    while True:
        batch = blob_service.list_blobs(username, marker=marker)
        blobs.extend(batch)

        if not batch.next_marker:
            break
        marker = batch.next_marker

    for blob in blobs:
        print(blob.name)
        
def progress_callback(current, total):
    global uploaded
    #print ("Current bytes uploaded: ", current)
    #print ("===============")
    #print ("Total bytes of file: ", total)
    #print ()
    if(current==total):
        uploaded = True
