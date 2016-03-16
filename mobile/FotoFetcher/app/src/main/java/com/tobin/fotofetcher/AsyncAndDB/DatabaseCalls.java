package com.tobin.fotofetcher.AsyncAndDB;

/**
 * Created by Joaquin on 3/1/2016.
 */
public class DatabaseCalls {

    private static DatabaseCalls instance;

    public static DatabaseCalls getInstance() {
        if(instance == null) {
             instance = new DatabaseCalls();
        }
        return instance;
    }
}
