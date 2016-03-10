import pydocumentdb.document_client as document_client
from static.app_keys import db_client, db_client_key, db_name, db_collection

#updateRecord receives the blobURL to locate the document
#and tags which are to be used to replace the current tags in the document
def updateRecord(blobURL, tags):

    try:
        #these function calls are used to locate the db client, database, collection, then the actual document
        #based off the URL provided
        client = document_client.DocumentClient(db_client, {'masterKey': db_client_key})
        db = next((data for data in client.ReadDatabases() if data['id'] == db_name))
        coll = next((coll for coll in client.ReadCollections(db['_self']) if coll['id'] == db_collection))        
        doc = next((doc for doc in client.ReadDocuments(coll['_self']) if doc['photo_url'] == blobURL))

        doc['tags'] = tags #changes the tags field to the new one provided by the user
        #replaces the current document with the new document, with updated tags
        replaced_document = client.ReplaceDocument(doc['_self'], doc)
        return "success"
    
    except Exception:
        return "error"
    
def main():
    tags = ['mom', 'dad', 'trees', 'cat']
    print(updateRecord('www.sad.com', tags))    
    
if __name__ == "__main__":
    main()
