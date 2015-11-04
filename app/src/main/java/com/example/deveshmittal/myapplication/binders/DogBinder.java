package com.example.deveshmittal.myapplication.binders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.deveshmittal.myapplication.OnItemClickedListener;
import com.example.deveshmittal.myapplication.R;
import com.example.deveshmittal.myapplication.common.BaseBinder;
import com.example.deveshmittal.myapplication.common.GenericAdapter;
import com.example.deveshmittal.myapplication.common.RecyclerViewItem;
import com.example.deveshmittal.myapplication.holders.DogViewHolder;
import com.example.deveshmittal.myapplication.model.DogItem;
import com.koushikdutta.ion.Ion;

/**
 * Created by deveshmittal on 02/11/15.
 */

public class DogBinder extends BaseBinder {


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, GenericAdapter adapter) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                getViewTypeLayout(), parent, false);

        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, final GenericAdapter adapter, final OnItemClickedListener onItemClickedListener) {
        DogViewHolder dogViewHolder = (DogViewHolder) holder;
        final DogItem dog = (DogItem) adapter.getItem(position);
        dogViewHolder.mTextView1.setText("Name : "+dog.mName);
        dogViewHolder.mTextView2.setText("Eyecolor : "+dog.mEyeColor);
//        dogViewHolder.mTextView3.setText(dog.mFriends);

        //    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) dogViewHolder.mImageView.getLayoutParams();
        Ion.with(dogViewHolder.mImageView)
                //  .resize(width, height)
                .centerCrop()
             .load(dog.mImageUrl);

        dogViewHolder.mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(adapter.getContext(), "Dog clicked ", Toast.LENGTH_LONG).show();
                onItemClickedListener.onItemClicked(dog);

            }
        });
    }

    public int getViewTypeLayout() {
        return R.layout.item_dog;
    }
}