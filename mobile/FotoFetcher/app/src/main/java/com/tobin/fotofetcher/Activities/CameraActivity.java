package com.tobin.fotofetcher.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tobin.fotofetcher.SingletonAndDB.Translator;
import com.tobin.fotofetcher.R;

public class CameraActivity extends AppCompatActivity {

    Button btnTakePhoto;
    ImageView photo;
    Bitmap bitmap;
    String tags, filename;

    private static final int CAM_REQUEST = 1313;

    public static final int IMAGE_GALLERY_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        final Translator translator = Translator.getInstance(this);

        btnTakePhoto = (Button) findViewById(R.id.Button1);
        photo = (ImageView) findViewById(R.id.ImageView1);

        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());

        Button uploadImageButton = (Button) findViewById(R.id.upload_image_button);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tags = ((EditText) findViewById(R.id.new_tags)).getText().toString();
                filename = ((EditText) findViewById(R.id.file_name)).getText().toString();

                if (tags.equals("") || filename.equals("")) {
                    Toast.makeText(v.getContext(), "You must enter a file name and at least one tag.", Toast.LENGTH_LONG).show();
                } else {

                    translator.uploadImage(bitmap, tags, filename);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case IMAGE_GALLERY_REQUEST:
                if(resultCode == RESULT_OK){

                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    bitmap = BitmapFactory.decodeFile(filePath);

                    int height = bitmap.getHeight();
                    int width = bitmap.getWidth();

                    Bitmap thumbnail;
                    if(height>width){
                        thumbnail = Bitmap.createScaledBitmap(bitmap,250, 250, true);

                    }else{
                        thumbnail = Bitmap.createScaledBitmap(bitmap,250, 250, true);

                    }

                    photo.setImageBitmap(thumbnail);
                    break;
                }
            case CAM_REQUEST:
                /* Camera Piece */
                if(resultCode == RESULT_OK) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                    photo.setImageBitmap(bitmap);
                    break;
                }
            default:
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    public void selectFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGE_GALLERY_REQUEST);
    }


    class btnTakePhotoClicker implements View.OnClickListener {

        @Override
        public void onClick(View v){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }
}