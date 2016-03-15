package com.tobin.fotofetcher.RecyclerViewStuff;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class SwipeTouchHelper extends ItemTouchHelper.SimpleCallback {
    private  MyRecyclerViewAdapter mAdapter;

    public SwipeTouchHelper(MyRecyclerViewAdapter adapter){
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT);
        this.mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //TODO: Not implemented here
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        mAdapter.deleteItem(viewHolder.getAdapterPosition());
    }
}