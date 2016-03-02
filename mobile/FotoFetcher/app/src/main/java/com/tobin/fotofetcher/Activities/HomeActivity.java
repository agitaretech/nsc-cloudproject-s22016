package com.tobin.fotofetcher.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.tobin.fotofetcher.AsyncAndDB.AsyncObjectList;
import com.tobin.fotofetcher.Fragments.FullSizeImageFragment;
import com.tobin.fotofetcher.Fragments.ListViewFragment;

import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.LoginCred.TwitterCustomVolleyRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.tobin.fotofetcher.R;

public class HomeActivity extends AppCompatActivity implements Interface, SearchView.OnQueryTextListener {
    FragmentManager fragManager = getSupportFragmentManager();
    FullSizeImageFragment fullFrag;
    ListViewFragment listFrag;
    private int fragState = 1;
    AsyncObjectList objectList;
    int position = 0;
    SearchView searchView;

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

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("fragState", fragState);
        outState.putInt("position",fragState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if(searchView == null) {
            Log.d("test", "failed");
        }
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        if(id == R.id.action_search){
            return true;
        }else if (id == R.id.plusButton) {
            Intent uploadIntent = new Intent(getApplicationContext(), CameraActivity.class);
            startActivity(uploadIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (FullSizeImageFragment.popupWindow != null) {
            Log.d("home", FullSizeImageFragment.popupWindow.toString());
            FullSizeImageFragment.popupWindow.dismiss();
            FullSizeImageFragment.popupWindow = null;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.d("here", "here");
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
        fullFrag.sv.scrollTo(0,0);
        Log.d("click", "in home");


    }

    @Override
    public void updateTag(int position) {

        listFrag.updateRecyclerView(position);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        Log.d("search", query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // If we want to do autofinish for search, implement here.
        // get all tags for user, put them in the array, search the array
        // from here.
        return false;
    }
}
