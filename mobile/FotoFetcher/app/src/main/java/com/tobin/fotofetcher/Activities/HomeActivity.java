package com.tobin.fotofetcher.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tobin.fotofetcher.AsyncObjectList;
import com.tobin.fotofetcher.Fragments.FullSizeImageFragment;
import com.tobin.fotofetcher.Fragments.ListViewFragment;

import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.LoginCred.TwitterCustomVolleyRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.tobin.fotofetcher.R;



public class HomeActivity extends AppCompatActivity implements Interface {
    FragmentManager fragManager = getSupportFragmentManager();
    FullSizeImageFragment fullFrag;
    ListViewFragment listFrag;
    private int fragState = 1;
    AsyncObjectList objectList;
    int position = 0;

    //TextView object
    private TextView textViewUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        objectList = AsyncObjectList.getInstance();

        if (savedInstanceState != null) {
            this.fragState = savedInstanceState.getInt("fragState");
            listFrag=(ListViewFragment) fragManager.findFragmentByTag("listFrag");
            fullFrag=(FullSizeImageFragment) fragManager.findFragmentByTag("photoFrag");

        }else{
            listFrag = new ListViewFragment();
            fullFrag = new FullSizeImageFragment();
            fragManager.beginTransaction().replace(R.id.list_fragment_container, listFrag, "listFrag").commit();
            fragManager.beginTransaction().replace(R.id.photo_fragment_container, fullFrag, "photoFrag").commit();

        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && fragState == 1) {
            fragManager.beginTransaction().show(listFrag).commit();
            fragManager.beginTransaction().hide(fullFrag).commit();
            Log.d("show", "in home");

        } else {
            fragManager.beginTransaction().hide(listFrag).commit();
            fragManager.beginTransaction().show(fullFrag).commit();
            Log.d("show", "in home");

        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragManager.beginTransaction().show(listFrag).commit();
            fragManager.beginTransaction().show(fullFrag).commit();
            Log.d("show", "in home");


        }

        if (textViewUsername != null)
            retrieveTwitterLogin();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragState", fragState);
        outState.putInt("position",fragState);
    }

    public void retrieveTwitterLogin() {

        //Initializing views
        NetworkImageView profileImage = (NetworkImageView) findViewById(R.id.home_twitter_profile_image);
        textViewUsername = (TextView) findViewById(R.id.home_twitter_username_text_view);

        //Getting the intent
        Intent intent = getIntent();

        //Getting values from intent
        String username = intent.getStringExtra(LoginActivity.KEY_USERNAME);
        String profileImageUrl = intent.getStringExtra(LoginActivity.KEY_PROFILE_IMAGE_URL);

        //Loading image
        ImageLoader imageLoader = TwitterCustomVolleyRequest.getInstance(this).getImageLoader();
        imageLoader.get(profileImageUrl, ImageLoader.getImageListener(profileImage, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        profileImage.setImageUrl(profileImageUrl, imageLoader);

        //Setting the username in textview
        textViewUsername.setText("Welcome, " + username);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.plusButton) {
            Intent uploadIntent = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(uploadIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (FullSizeImageFragment.popupWindow != null){
            Log.d("home", "popup image not null ");
            FullSizeImageFragment.popupWindow.dismiss();
        }

        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragState = 1;
            fragManager.beginTransaction().show(listFrag).commit();
            fragManager.beginTransaction().hide(fullFrag).commit();
        }

    }

    @Override
    public void itemClicked(int position) {



        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragManager.beginTransaction().hide(listFrag).commit();
            fragManager.beginTransaction().show(fullFrag).commit();
            fragState = 2;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragManager.beginTransaction().show(listFrag).commit();
            fragManager.beginTransaction().show(fullFrag).commit();
        }

        this.position=position;
        fullFrag.setObject(position);
        Log.d("click", "in home");


    }

    @Override
    public void updateTag(int position) {

        listFrag.updateRecyclerView(position);

    }

}
