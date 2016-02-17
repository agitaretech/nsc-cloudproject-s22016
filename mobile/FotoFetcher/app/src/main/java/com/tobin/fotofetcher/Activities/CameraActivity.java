package com.tobin.fotofetcher.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tobin.fotofetcher.R;

public class CameraActivity extends AppCompatActivity {

    Button btnTakePhoto;
    ImageView photo;
    private static final int CAM_REQUEST = 1313;

    public static final int IMAGE_GALLERY_REQUEST = 2;
    ImageView imgFromGallery;//Attempt to grab image from the gallery


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btnTakePhoto = (Button) findViewById(R.id.Button1);
        photo = (ImageView) findViewById(R.id.ImageView1);

        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());




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
                    Bitmap myImage = BitmapFactory.decodeFile(filePath);

                    int height = myImage.getHeight();
                    int width = myImage.getWidth();

                    int ratio=1;
                    Bitmap thumbnail;
                    if(height>width){
                        ratio = height/width;
                        thumbnail = Bitmap.createScaledBitmap(myImage,250, 250, true);

                    }else{
                        ratio = width/height;
                        thumbnail = Bitmap.createScaledBitmap(myImage,250, 250, true);

                    }

                    photo.setImageBitmap(thumbnail);

                   Toast.makeText(this,thumbnail.toString()+"here",Toast.LENGTH_LONG).show();

                    break;
                }
            case CAM_REQUEST:
                /* Camera Piece */
                if(resultCode == RESULT_OK) {
                    String s = data.getExtras().get("data").toString();
                    Bitmap myBitmap = BitmapFactory.decodeFile(s);
                    Bitmap thumbnail = Bitmap.createScaledBitmap(myBitmap, 100, 100, true);
                    photo.setImageBitmap(thumbnail);
                    Toast.makeText(this,thumbnail.toString(),Toast.LENGTH_LONG).show();

                    break;
                }
            default:
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //getMenuInflater().inflate(R.menu.camera, menu);
        return true;
    }

    public void selectFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, IMAGE_GALLERY_REQUEST);
    }

    public void uploadPhoto(View view) {
    }

    public void tagPhoto(View view) {
    }

    class btnTakePhotoClicker implements View.OnClickListener {

        @Override
        public void onClick(View v){
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAM_REQUEST);
        }
    }
}