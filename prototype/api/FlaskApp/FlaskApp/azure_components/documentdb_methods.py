import pydocumentdb.document_client as document_client
import verify_oauth
import datetime
from datetime import timedelta
from static.app_keys import db_client, db_client_key, db_name, db_collection
	

#Need a function specifically oriented toward making the call.
#This is *not* async, which will need to change.
#Ideally, after this is complete, we'd send the confirmation to the UI that the upload completed.
def createRecord(user, originalFilename, tags, time, url):
    try:

        epoc = datetime.datetime(2016, 2, 23, 3, 0, 00, 000000);
        val = (time - epoc).total_seconds()*1000000;
                
        client = document_client.DocumentClient(db_client, {'masterKey': db_client_key});
        
        #Not sure we need this. Client may be it.
        db = next((data for data in client.ReadDatabases() if data['id'] == db_name));
        
        coll = next((coll for coll in client.ReadCollections(db['_self']) if coll['id'] == db_collection));

        #create document. Tags is an array, as passed.
        document = client.CreateDocument(coll['_self'],
                        {   "user_id": user,
                                "file_name": originalFilename,
                                "photo_url": url,
                                "photo_id": val,
                                "tags": tags
                        });
        
        returnlist = [];	
        

        return "success"
        
    except Exception:
        return "error"

def getRecords(user, lastID, prev, tags):

    try:
        dir = ">"
        order = "ASC"

        if prev != 'false':
            dir = "<"
            order = "DESC"



        client = document_client.DocumentClient(db_client, {'masterKey': db_client_key})
        db = next((data for data in client.ReadDatabases() if data['id'] == db_name))
        coll = next((coll for coll in client.ReadCollections(db['_self']) if coll['id'] == db_collection))


        queryString = 'SELECT TOP 20 ' + db_collection + '.photo_id, ' + \
                        db_collection +'.file_name, ' + db_collection + '.photo_url, ' + db_collection + \
                        '.tags FROM '+ db_collection + ' WHERE '+ db_collection + '.user_id = "' \
                        + user + '" AND ' + db_collection + '.photo_id ' + dir + ' ' + str(lastID)

        if len(tags) > 0:
            for tag in tags:
                queryString += ' AND ARRAY_CONTAINS(' + db_collection + '.tags ,"' + tag + '")'

        queryString += ' ORDER BY '+ db_collection +'.photo_id '+ order
        

        itterResult =  client.QueryDocuments(coll['_self'], queryString)
        rtn_list = list(itterResult)
        


        if prev != 'false':
            rtn_list_new = sorted(rtn_list, reverse=True)
            return sorted(rtn_list_new)
        
        return rtn_list

    except Exception:
        return "error"

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

        #changes the tags field to the new one provided by the user
        doc['tags'] = tags 
        #replaces the current document with the new document, with updated tags
        replaced_document = client.ReplaceDocument(doc['_self'], doc)
        return "success"
    
    except Exception:
        return "error"

def main():
    pass
    #time = datetime.datetime.utcnow()
    #print(makeMetadata('tom','filename2',['fun','tom'], time, "www.happy.com"))
    #fun = getRecords('fin',1214137258909, 'true', [])
    #print(fun)
    #fun = deleteRecord('www.test.com')
    #print(fun)
    #tags = ['mom', 'dad', 'trees', 'cat']
    #print(updateRecord('https://ad440rjh.blob.core.windows.net/fin/2016-03-08041411903453_Todd', tags))    
    
if __name__ == "__main__":
    main()
