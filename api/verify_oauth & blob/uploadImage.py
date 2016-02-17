from blob import uploadBlob

def uploadImage(username, blob, filename, token, secret, tags):

    rtnBlobList = uploadBlob(username, blob, filename, token, secret)
    if len(rtnBlobList)  == 1:
        return rtnBlobList[0]

    return 'success'


def main():
    print(uploadImage('fred', 'fun', 'filename','4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR','BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF','fun'))

main()
