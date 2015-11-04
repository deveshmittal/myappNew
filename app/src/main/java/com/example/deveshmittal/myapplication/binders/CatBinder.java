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
import com.example.deveshmittal.myapplication.holders.CatViewHolder;
import com.example.deveshmittal.myapplication.model.CatItem;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

/**
 * Created by deveshmittal on 02/11/15.
 */
public class CatBinder extends BaseBinder {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, GenericAdapter adapter) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                getViewTypeLayout(), parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, final GenericAdapter adapter,final OnItemClickedListener onItemClickedListener) {
        CatViewHolder catViewHolder = (CatViewHolder) holder;
        final CatItem cat = (CatItem) adapter.getItem(position);
        catViewHolder.mTextView.setText("Name : "+cat.mName);
        catViewHolder.mTextView2.setText("Company : "+cat.mCompany);

        Picasso.with(catViewHolder.mImageView.getContext()).load(cat.mImageUrl).placeholder(R.drawable.image_place).fit().into(catViewHolder.mImageView);

        catViewHolder.mParentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedListener.onItemClicked(cat);
              //  Toast.makeText(adapter.getContext(), "Image clicked ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public int getViewTypeLayout() {
        return R.layout.item_cat;
    }
}
