package com.tobin.fotofetcher.AsyncAndDB;

import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

import java.util.ArrayList;

/**
 * Created by Joaquin on 3/1/2016.
 */
public class Translator {

    private DatabaseCalls databaseCalls;

    public Translator() {
        this.databaseCalls = DatabaseCalls.getInstance();
    }

    public String searchFor(String[] credentials) {
        return null;
    }

    public String delete(DataObject object, String[] credentials) {
        return null;
    }

    //update
    public String update(DataObject object, String[] credentials) {
        return null;
    }
    //add
    public String addObject(DataObject object, String[] credentials) {
        return null;
    }
    //getList
    public ArrayList<DataObject> getList(String[] credentials) {
        return null;
    }
    //getAllTags
    public ArrayList<DataObject> getAllTags(String[] credentials) {
        return null;
    }

}
