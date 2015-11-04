package com.example.deveshmittal.myapplication.common;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.deveshmittal.myapplication.OnItemClickedListener;
import com.example.deveshmittal.myapplication.binders.DogBinder;

/**
 * Created by deveshmittal on 02/11/15.
 */

public abstract class BaseBinder {

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, GenericAdapter adapter);

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position, GenericAdapter adapter, OnItemClickedListener onItemClickedListener);

}
