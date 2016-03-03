import pydocumentdb.document_client as document_client
from static.app_keys import db_client, db_client_key, db_name, db_collection

def getRecords(user, lastID, direction, tags):

    dir = ">";
    order = "ASCENDING";

    if direction == "back":
        dir = "<";
        order = "DESCENDING";

    client = document_client.DocumentClient(db_client, {'masterKey': db_client_key});

    queryString = "SELECT TOP 20 file_name, photo_url, photo_id, tags FROM " + db_name  + "t JOIN tags IN t.tags WHERE photo_id = " + lastID + " AND tags = "+ tags + " ORDER BY " + order

    itterResult =  client.CreateDocumentQuery(collection_link, queryString);
    

    sorted(itterResult, key="photo_id");

    jsnOb = flask.jsonify(**itterResult);
    if(direction == "back"):
        return sorted(jsnOb, key="photo_id")

    return jsnOb;

def main():
    print(getRecords('fin','5232066177', 'fun', 'fun'))

# call main
if __name__ == "__main__":
    main()
