package com.tobin.fotofetcher.RecyclerViewStuff;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tobin.fotofetcher.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.DataObjectHolder> {
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView imageName;
        TextView tags;
        TextView url;
        ImageView thumb;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imageName = (TextView) itemView.findViewById(R.id.imageNameTextView);
            tags = (TextView) itemView.findViewById(R.id.tagNameTextView);
            url = (TextView) itemView.findViewById(R.id.url_text_view);
            thumb = (ImageView) itemView.findViewById(R.id.thumbnailImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);

        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.imageName.setText(mDataset.get(position).getImageName());
        holder.tags.setText(mDataset.get(position).getTags());
        holder.url.setText(mDataset.get(position).getUrl());
        Picasso.with(holder.itemView.getContext()).load(holder.url.getText().toString()).resize(100, 100).into(holder.thumb);
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
         void onItemClick(int position, View v);
    }


}