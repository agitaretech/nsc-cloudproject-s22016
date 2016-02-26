package com.tobin.fotofetcher.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tobin.fotofetcher.AsyncObjectList;
import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.R;
import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;
import com.tobin.fotofetcher.RecyclerViewStuff.DividerItemDecoration;
import com.tobin.fotofetcher.RecyclerViewStuff.MyRecyclerViewAdapter;
import com.tobin.fotofetcher.RecyclerViewStuff.SwipeTouchHelper;

import java.util.ArrayList;

public class ListViewFragment extends Fragment {

    private Interface listener;
    private AsyncObjectList list;
    RecyclerView mRecyclerView;
    MyRecyclerViewAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("name", "here listFrag");
        View view = inflater.inflate(R.layout.fragment_image_list, container, false);
        list= AsyncObjectList.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(list.getList());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener(new
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

    public void updateRecyclerView(int position){
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
}