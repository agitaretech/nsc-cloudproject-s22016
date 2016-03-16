package com.tobin.fotofetcher.SingletonAndDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Joaquin on 3/1/2016.
 */
public class Translator {

    private static DatabaseCalls databaseCalls;
    private static Translator translator;
//    private static JSONArray jsonArray;
    static String twitterUserName;
    static String token;
    static String secret;
    static Context transContext;

//    public static void setJsonArray(JSONArray jArray){
//        jsonArray = jArray;
//    }

    public static Translator getInstance(Context context) {
        if (translator == null) {
            translator = new Translator(context);
        }

        transContext = context;
        SharedPreferences sharedPref = context.getSharedPreferences("twitter_creds", Context.MODE_PRIVATE);
        twitterUserName = sharedPref.getString("username", null);
        token = sharedPref.getString("token", null);
        secret = sharedPref.getString("secret", null);
        databaseCalls = DatabaseCalls.getInstance(context);
        return translator;
    }

    private Translator(Context context) {

    }

    public boolean delete(DataObject object) {
        HashMap<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("url", object.getUrl());
        hashMap.put("token", token);
        hashMap.put("secret", secret);
        String deleteResponse = databaseCalls.delete(hashMap);

        if(deleteResponse != null) {
            //check to see if it succeeded or failed
            try {
                if(deleteResponse.equals("success")) {
                    return true;
                }

            } catch (Exception e){}
        }

        return false;
    }

    //update
    public String updateMetaData(DataObject object) {
        // Update tags from the DB
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("blobURL", object.getUrl());
        hashMap.put("tags", object.getTags());
        hashMap.put("token", token);
        hashMap.put("secret", secret);
        return null;
    }

    //add
    public String uploadImage(Bitmap bitmap, String tags, String filename) {
        return null;
    }

    //getAllTags
    public ArrayList<DataObject> getAllTags() {
        return null;
    }
    //getList
    public ArrayList<DataObject> getList(String timestamp, String filter) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        if(timestamp != null && !timestamp.equals("")) {
            hashMap.put("timestamp", timestamp);
        }

        if(filter != null && !filter.equals("")) {
            hashMap.put("tags", filter);
        }

        hashMap.put("username", twitterUserName);
        hashMap.put("token", token);
        hashMap.put("secret", secret);

        /*Needs to be created in the DatabaseCalls class*/
//       JSONArray[] obj = databaseCalls.getObject(hashMap, transContext);
//        JSONArray jsonArray=obj[0];

        JSONArray[] js = databaseCalls.get(hashMap, transContext);
        JSONArray jsonArray = js[0];
//        databaseCalls.get(hashMap, transContext);
        // this json array is set by the setJsonArray method in this Translator class, called from getObject method in the DatabaseCalls class
        if(jsonArray != null) {
            //check to see if it succeeded or failed
            try {

                // Parse JSON Array
                ArrayList<DataObject> objectList = new ArrayList<DataObject>();

                for (int i = 0; i < jsonArray.length(); i++) {
//                    String tags = "";
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String fileName = jsonObject.getString("file_name");
                    String tags = jsonObject.getString("tags");

//                    for (int i = 0; i < jsonObject.getString("tags").length(); i++) {
//                        String tags += jsonObject.getString("tags")[i];
//                    }

                    String url = jsonObject.getString("photo_url");
                    String id = jsonObject.getString("photo_id");
                    DataObject dataObject=new DataObject(fileName, "tags", url, id);
                    objectList.add(dataObject);
                    Log.d("trans", objectList.size() + "");
                    return objectList;
                }


                // if timestamp != null, then make sure to add to the list
                // instead of creating a new list.
            } catch (Exception e){}
        }else{
            ArrayList<DataObject> dummyData = ObjectList.fillDummyData();
            return dummyData;
        }
        return null;
    }
}
