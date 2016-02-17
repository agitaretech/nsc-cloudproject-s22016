package com.tobin.fotofetcher;


import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

/**
 * Created by Joaquin on 2/7/2016.
 */
public interface Interface {
//     void itemClicked(int objectPosition, String name, String tags, String url);
    void itemClicked(int position);
    void updateTag(DataObject object, int position);
}
