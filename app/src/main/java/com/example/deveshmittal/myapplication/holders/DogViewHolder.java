package com.example.deveshmittal.myapplication.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.deveshmittal.myapplication.R;

/**
 * Created by deveshmittal on 02/11/15.
 */

public class DogViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout mParentView;

    public ImageView mImageView;
    public TextView mTextView1;
    public TextView mTextView2;
    public TextView mTextView3;


    public DogViewHolder(View itemView) {
        super(itemView);
         mParentView =(RelativeLayout)itemView.findViewById(R.id.dog_parent);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView_dog1);
        mTextView1 = (TextView)itemView.findViewById(R.id.textView_dog_1);
        mTextView2 = (TextView)itemView.findViewById(R.id.textView_dog_2);
        mTextView3 = (TextView)itemView.findViewById(R.id.textView_dog_3);

    }
}