package com.example.deveshmittal.myapplication.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.deveshmittal.myapplication.R;


/**
 * Created by deveshmittal on 02/11/15.
 */
public class CatViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout mParentView ;
    public ImageView mImageView;
    public TextView mTextView;
    public TextView mTextView2;

    public CatViewHolder(View itemView) {
        super(itemView);
        mParentView = (LinearLayout) itemView.findViewById(R.id.cat_parent);
        mImageView = (ImageView)itemView.findViewById(R.id.imageView1);
        mTextView = (TextView)itemView.findViewById(R.id.textView_1);
        mTextView2 = (TextView)itemView.findViewById(R.id.textView_2);

    }
}