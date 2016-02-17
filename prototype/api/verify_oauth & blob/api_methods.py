from blob import uploadBlob, deleteBlob

def uploadImage(username, blob, filename, token, secret, tags):

    rtnBlobList = uploadBlob(username, blob, filename, token, secret)
    if len(rtnBlobList)  == 1:
        return rtnBlobList[0]

    return 'success'


def deleteImage(username, blobURL, token, secret):
    rtnBlobList = deleteBlob(username, blobURL, token, secret)
    if len(rtnBlobList)  == 1:
        return rtnBlobList[0]

    return 'success'

def getImages(timestamp, tags, username, token, secret):
    return null

def updateTags(blobURL, tags, username, token, secret):
    return null

def main():
    #print(uploadImage('TKJR  E', 'fun', 'Todd','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF','fun'))
    print(deleteImage('FRED', "https://ad440rjh.blob.core.windows.net/fred/2016-02-16183542215195",'4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF'))
# call main
if __name__ == "__main__":
    main()
