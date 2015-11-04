package com.example.deveshmittal.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deveshmittal.myapplication.common.RecyclerViewItem;
import com.squareup.picasso.Picasso;

/**
 * Created by deveshmittal on 04/11/15.
 */
public class DetailFragment extends Fragment {
    RecyclerViewItem item ;
    ImageView mImageView;
    TextView mTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        item = getArguments().getParcelable("item");
        return inflater.inflate(R.layout.fragment_detail, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mImageView = (ImageView) view.findViewById(R.id.imageView);
        mTextView =  (TextView) view.findViewById(R.id.textView_detail);
        Picasso.with(mImageView.getContext()).load(item.getImageUrl()).into(mImageView);
        mTextView.setText(item.getItemName());
    }
}
