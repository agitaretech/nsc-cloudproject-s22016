import pydocumentdb.document_client as document_client
import verify_oauth
import datetime
from datetime import timedelta
from static.app_keys import db_client, db_client_key, db_name, db_collection
	

#Need a function specifically oriented toward making the call.
#This is *not* async, which will need to change.
#Ideally, after this is complete, we'd send the confirmation to the UI that the upload completed.
def makeMetadata(user, originalFilename, tags, time, url):
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

    
def main():
    time = datetime.datetime.utcnow()
    print(makeMetadata('tom','filename2',['fun','tom'], time, "www.happy.com"))
          
# call main
if __name__ == "__main__":
    main()
