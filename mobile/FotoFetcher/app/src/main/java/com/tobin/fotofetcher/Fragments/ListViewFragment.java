package com.tobin.fotofetcher.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.tobin.fotofetcher.SingletonAndDB.ObjectList;
import com.tobin.fotofetcher.SingletonAndDB.Translator;
import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.LoginCred.TwitterCustomVolleyRequest;
import com.tobin.fotofetcher.R;
import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;
import com.tobin.fotofetcher.RecyclerViewStuff.DividerItemDecoration;
import com.tobin.fotofetcher.RecyclerViewStuff.EndlessRecyclerOnScrollListener;
import com.tobin.fotofetcher.RecyclerViewStuff.MyRecyclerViewAdapter;
import com.tobin.fotofetcher.RecyclerViewStuff.SwipeTouchHelper;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    private Interface listener;
    private ObjectList list;
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;

    NetworkImageView profileImage;
    TextView textViewUsername;
    boolean isBypassed = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list, container, false);

        profileImage = (NetworkImageView) view.findViewById(R.id.home_twitter_profile_image);
        textViewUsername = (TextView) view.findViewById(R.id.home_twitter_username_text_view);

        if (textViewUsername != null) {
            retrieveTwitterLogin();

        }

        list = ObjectList.getInstance(getActivity());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        if (isBypassed)
//            mAdapter = new MyRecyclerViewAdapter(list.fillDummyData());
//        else
        mAdapter = new MyRecyclerViewAdapter(list.getList());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                Translator translator = Translator.getInstance(getActivity());
                ArrayList<DataObject> next = translator.getList(null,null);

                for (DataObject obj : next) {
                    if(next!=null) {
                        list.getList().add(obj);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        (mAdapter).setOnItemClickListener(new
                                                  MyRecyclerViewAdapter.MyClickListener() {
                                                      @Override
                                                      public void onItemClick(int position, View v) {

                                                          listener.itemClicked(position);

                                                      }
                                                  });

        ItemTouchHelper.Callback callback = new SwipeTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    public void retrieveTwitterLogin() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("twitter_creds", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String profileImageUrl = sharedPreferences.getString("userImage", null);

        //Loading image
        ImageLoader imageLoader = TwitterCustomVolleyRequest.getInstance(getActivity()).getImageLoader();
        imageLoader.get(profileImageUrl, ImageLoader.getImageListener(profileImage, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        profileImage.setImageUrl(profileImageUrl, imageLoader);

        //Setting the username in textview
        textViewUsername.setText("Welcome, " + username);
    }

    public void updateRecyclerView(int position) {
        mAdapter.notifyItemChanged(position);
        mRecyclerView.scrollToPosition(position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interface) {
            listener = (Interface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    public void searchUpdate(){
        mAdapter.notifyDataSetChanged();
    }
}