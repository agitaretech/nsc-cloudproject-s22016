#author jesse furlan
from azure.storage.blob import BlobService
import datetime

#temp username variable until we figure out how we're getting it from the client side
username = 'testuser'

"""
utilize the blob_service module to connect to my account for now, this will be changed later
BlobService takes parameters of storage account name and the primary account key
next, set the container to be viewable for public access, this way the user will be able to
actually see their image from the url we give back.
"""
blob_service = BlobService(account_name='jesse15', account_key='iQfMxRlQ1nYsp+pKWNuE+3XzvCsO3VqqXHoUB/TItJ68UN7Iz9zGLi/dgXgBXkIdP25P7PoBG3TlUQr/bDej+A==')

blob_service.create_container(username, x_ms_blob_public_access='container')


#datetime gives the system's current datetime, I convert to string in order to .replace
#characters that wouldn't work in a URL like ':','.', and ' '
time = str(datetime.datetime.now())

time = time.replace(':','').replace('.','').replace(' ','')

##for now, until we figure out how to get a file from the client, this is just a local file upload
blob_service.put_block_blob_from_path(
    username,
    time,
    'C:\\Users\\jesse\\Pictures\\peter.png',
    x_ms_blob_content_type='image/png'
    )
