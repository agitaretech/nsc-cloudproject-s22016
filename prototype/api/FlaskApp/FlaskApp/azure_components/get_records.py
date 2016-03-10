import pydocumentdb.document_client as document_client
from static.app_keys import db_client, db_client_key, db_name, db_collection

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

def main():
    fun = getRecords('fin',1214137258909, 'true', [])
    print(fun)
    

# call main
if __name__ == "__main__":
    main()
