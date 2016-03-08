
# json error messages for oauth, uploading, and deleting images
oauth_error_json = {'status': 'error', 'msg': "could not verify oauth credentials"}
upload_image_blob_error_json = {'status': 'error', 'msg': "failure to upload image to azure storage"}
upload_image_db_error_json = {'status': 'error', 'msg': "failure to upload metadata to documentdb"}
delete_image_blob_error_json = {'status': 'error', 'msg': "failure to delete image from azure storage"}
delete_image_db_error_json = {'status': 'error', 'msg': "failure to delete image from documentdb"}
get_image_error_json = {'status': 'error', 'msg': "failure to get image links from documentdb"}
# json success messages each api method
upload_image_success_json = {'status': 'success', 'msg': "image uploaded"}
delete_image_success_json = {'status': 'success', 'msg': "image deleted"}
update_tags_success_json = {'status': 'success', 'msg': "tags updated"}
