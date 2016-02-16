from azure.storage.blob import BlobService
import datetime
import string
from verify_oauth import verify_oauth

accountName = 'jesse15'
accountKey = ''

blob_service = BlobService(accountName, accountKey)
uploaded = False

def uploadBlob(username, file, filename, token, secret):
    global uploaded
    returnList = []
    if verify_oauth(token, secret) != 200:
        returnList = ["could not verify oAuth credentials"]
        return returnList
        
    blob_service.create_container(username, x_ms_blob_public_access='container')

    #datetime gives the system's current datetime, I convert to string in order to .replace
    #characters that wouldn't work in a URL like ':','.', and ' '
    time = str(datetime.datetime.now())
    timeReplaced = time.replace(':','').replace('.','').replace(' ','')

    URLstring = "https://" + accountName + ".blob.core.windows.net/" + username + "/" + timeReplaced + "_" + filename

    uploaded = False
    blob_service.put_block_blob_from_path(
        username,
        timeReplaced,
        '/Users/rjhunter/GitHub/nsc-cloudproject-s22016/api/verify_oauth\\ \\&\\ blob/bridge.jpg',
        x_ms_blob_content_type='image/png',
        progress_callback=progress_callback
        )
    
    #if upload is successful, return a list with the timestamp and the final URL
    #else return an empty list

    if uploaded:
        return returnList[time, URLstring]
    else:
        return returnList["failure to upload to Azure Blob Storage"]
        
def deleteBlob(username, blobURL, token, secret):

    if verify_oauth(token, secret) != 200:
        returnList = ["could not verify oAuth credentials"]
        return returnList

    exploded = blobURL.split("/")
    blob_service.delete_blob(username, exploded[len(exploded)-1])
    return ["sucess","test"]

def listBlobs(username):
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
    print ("Current bytes uploaded: ", current)
    print ("===============")
    print ("Total bytes of file: ", total)
    print ()
    if(current==total):
        uploaded = True
