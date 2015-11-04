package com.example.deveshmittal.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.deveshmittal.myapplication.common.BaseBinder;
import com.example.deveshmittal.myapplication.common.GenericAdapter;
import com.example.deveshmittal.myapplication.common.RecyclerViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deveshmittal on 02/11/15.
 */
public class MyAdapter extends GenericAdapter {

    List<RecyclerViewItem> mData = new ArrayList<>();
    Context mContext;
    RecyclerView.RecycledViewPool mRecycledViewPool;
    OnItemClickedListener onItemClickedListener;

    public MyAdapter(Context context, RecyclerView.RecycledViewPool recycledViewPool, OnItemClickedListener listener) {
        this.mContext = context;
        this.mRecycledViewPool = recycledViewPool;
        this.onItemClickedListener = listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerViewItem getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getItemType();
    }

    @Override
    public BaseBinder getBaseType(int viewType) {
        return ViewType.getBinder(viewType);
    }

    @Override
    public OnItemClickedListener getClickListener() {
        return onItemClickedListener;
    }

    public void setData(List<RecyclerViewItem> data) {
        mData = data;
    }

    public Context getContext() {
        return mContext;
    }

    public RecyclerView.RecycledViewPool getRecycledViewPool() {
        return mRecycledViewPool;
    }
}
