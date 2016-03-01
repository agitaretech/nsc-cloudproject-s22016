from azure.storage.blob import BlobService
import datetime
import string

accountName = 'jesse15'
accountKey = ''

blob_service = BlobService(accountName, accountKey)
uploaded = False

def main():
    
    URL = "https://jesse15.blob.core.windows.net/testuser/2016-02-12173717188246"
    uploadBlob("testuser", URL)
                             
def uploadBlob(username, file):

    blob_service.create_container(username, x_ms_blob_public_access='container')

    #datetime gives the system's current datetime, I convert to string in order to .replace
    #characters that wouldn't work in a URL like ':','.', and ' '
    time = str(datetime.datetime.now())
    timeReplaced = time.replace(':','').replace('.','').replace(' ','')

    URLstring = "https://" + accountName + ".blob.core.windows.net/" + username + "/" + timeReplaced


    ##for now, until we figure out how to get a file from the client, this is just a local file upload
    global uploaded
    uploaded = False
    blob_service.put_block_blob_from_path(
        username,
        timeReplaced,
        'C:\\Users\\jesse\\Pictures\\peter.png',
        x_ms_blob_content_type='image/png',
        progress_callback=progress_callback
        )
    if uploaded:
        #this is where 'time' and 'URLstring' variables
        #will be sent off to be stored in metadata
        
       print("Upload Successful!")
        
        
    
def deleteBlob(username, blobURL):
    exploded = blobURL.split("/")
    blob_service.delete_blob(username, exploded[len(exploded)-1])

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

main()
