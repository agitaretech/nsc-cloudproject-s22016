import pydocumentdb.document_client as document_client
from static.app_keys import db_client, db_client_key, db_name, db_collection

def deleteRecord(blobURL):
    try:
        client = document_client.DocumentClient(db_client, {'masterKey': db_client_key})
        db = next((data for data in client.ReadDatabases() if data['id'] == db_name))
        coll = next((coll for coll in client.ReadCollections(db['_self']) if coll['id'] == db_collection))
        # Read documents and take first since id should not be duplicated.
        doc = next((doc for doc in client.ReadDocuments(coll['_self']) if doc['photo_url'] == blobURL))
        client.DeleteDocument(doc['_self'])
        return 'success'

    except Exception:
        return 'error'
def main():
    fun = deleteRecords('www.test.com')
    print(fun)
    

# call main
if __name__ == "__main__":
    main()
