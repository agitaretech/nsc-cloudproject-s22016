from azure.storage.blob import BlobService
import datetime
import string
import base64
from static.app_keys import blob_account_name, blob_account_key

#get accountName and accountKey from app_keys module
accountName = blob_account_name
accountKey = blob_account_key
#create the blob_service object which connects to the Azure Storage account
blob_service = BlobService(accountName, accountKey)
#flag variable to verify upload
uploaded = False

#uploadBlob takes in the username which is used for the storage container name
#file is the file to be uploaded
#filename is concatenated onto the URL for user readability
#token and secret are used for oAuth verification which must happen at every step.
def uploadBlob(username, file, filename):
    try:
        global uploaded
        username = username.lower()
        returnList = []
        #decode base64 image string
        decodedFile = file.decode("base64")

        blob_service.create_container(username, x_ms_blob_public_access="container")

        #get current datetime in UTC for a completely unique identifier
        time = datetime.datetime.utcnow()
        #convet to string and replace characters illegal for a URL
        #do it in a new variable since "time" is sent to DocumentDB as a pure datetime object
        timeReplaced = str(time).replace(':','').replace('.','').replace(' ','') + "_" + filename
        #build the URL ahead of the upload and have it ready to send to DB if successful
        URLstring = "https://" + accountName + ".blob.core.windows.net/" + username + "/" + timeReplaced 
        uploaded = False
        #put the blob into storage
        #username is the container name, timeReplaced is the blob name
        #progress_callback calls the method of the same name and
        #checks upload status in bytes at the server tick rate
        blob_service.put_block_blob_from_bytes(
            username,
            timeReplaced,
            decodedFile,
            x_ms_blob_content_type='image/png',
            progress_callback=progress_callback
            )
        
        #if upload is successful, return a list with the timestamp and the final URL
        #to be put in database, else return an error
        if uploaded:
            returnList = [time, URLstring]
            return returnList
        else:
            return returnList["error"]

    except Exception:
        return ["error"]

#deleteBlob takes in the URL from http request headers
#finds the blob to be deleted by exploding the url and using
#last two elements as container name, blob name
def deleteBlob(blobURL):
    try:
        exploded = blobURL.split("/")#split based on /'s
        #second to last element is container name(username), last element is blob name
        blob_service.delete_blob(exploded[len(exploded)-2], exploded[len(exploded)-1])
        return 'success'
    except Exception:
        return 'error'
    
#unused function incase list of all blobs in container is desired.
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
#calls progress_callback function in the Azure SDK
#current is total bytes that have been uploaded so far
#total is the total number of bytes in the file
def progress_callback(current, total):
    global uploaded
    #if current bytes uploaded == total file size, upload successful
    if(current==total):
        uploaded = True


def main():
    pass
    #encoded = base64.b64encode(open('/Users/rjhunter/Desktop/bridge.jpg', "rb").read())
    #print(encoded)
    #fh = open("/Users/rjhunter/Desktop/testEncode.txt", "wb")
    #fh.write(encoded)
    #fh.close()
    #print(uploadBlob('fin',encoded,'test123'))
    
if __name__ == "__main__":
    main()
