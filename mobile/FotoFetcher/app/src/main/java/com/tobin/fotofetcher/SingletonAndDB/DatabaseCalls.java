package com.tobin.fotofetcher.SingletonAndDB;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DatabaseCalls {

    private static DatabaseCalls instance;
    private String url = "http://ad440api.cloudapp.net/";
//    final RequestQueue mRequestQueue;


    public static DatabaseCalls getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseCalls(context);
        }
        return instance;
    }

    private DatabaseCalls(Context context){
//        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//        // Instantiate the RequestQueue with the cache and network.
//        mRequestQueue = new RequestQueue(cache, network);
    }

//    public void getObject( final HashMap header, Context context) {
//        url += "getImages";
//        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
//        Network network = new BasicNetwork(new HurlStack());
//        final RequestQueue mRequestQueue = new RequestQueue(cache, network);
//        mRequestQueue.start();
//
//        JsonObjectRequest req = new JsonObjectRequest(url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
//
////                            for (int i = 0; i < response.length(); i++) {
////
////                                JSONObject row = (JSONObject) response.get(i);
////
////                                Log.d("db", "row in response: " + row);
////                            }
//
//                            JSONArray Jarray = response.getJSONArray("imgs");
//                            Log.d("db", "in response Jarray: " + Jarray);
////                            Translator.setJsonArray(Jarray);
//                        } catch (JSONException e) {
//                            Log.d("db", "jsonarray exception: " + e.toString());
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("db", "error: " + error.toString());
//
//                mRequestQueue.stop();
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put ("username", "fin");
//                params.put("token", "4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR");
//                params.put("secret", "BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF");
//                Log.d("db", "In header");
////                params.put("Accept", "application/json");
//                return params;
//            }
//        };
//
//        mRequestQueue.add(req);
////        Log.d("db", "response object: " + responseObject[0] + "");
//    }

    public JSONArray[] get(final HashMap header, Context context) {
        url += "getImages";
//        final JSONArray responseArray = new JSONArray();
        final JSONArray[] responseObject = new JSONArray[1];
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        final RequestQueue mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject row = (JSONObject) response.get(i);

                        Log.d("db", "row in response: " + row);
                    }
                }catch (JSONException e) {
                        e.printStackTrace();
                        mRequestQueue.stop();
                    }


//                responseObject[0] = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("db", "error: " + error.toString());

                mRequestQueue.stop();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put ("username", "fin");
                params.put("token", "4800385332-ZbrU1XfignI2lA3MjQu7U8KbIkTdYAdj1ArMVFR");
                params.put("secret", "BPSs4gwICptsGVZQc9F2EpWcw6ar1gsv4Nlnqvq5PFIdF");
                Log.d("db", "In header");
//                params.put("Accept", "application/json");
                return params;
            }
        };

        mRequestQueue.add(req);
//        Log.d("db", "response object: " + responseObject[0] + "");
        return responseObject;
    }

    public String delete(HashMap header){
//        url += "deleteImage";
//        final String[] result = new String[1];
//        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d("database", "delete response: " + response);
//                result[0] = response;
//            }
//        },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("database", "delete error: " + error);
//                        result[0] = error + "";
//
//                    }
//                }
//        );
//        mRequestQueue.add(deleteRequest);
//
//        return result[0];
        return null;
    }

    public String put(HashMap hashMap) {
        return null;
    }

}

