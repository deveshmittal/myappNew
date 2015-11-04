package com.example.deveshmittal.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.deveshmittal.myapplication.common.RecyclerViewItem;

/**
 * Created by deveshmittal on 02/11/15.
 */
public class CatItem extends RecyclerViewItem {

    public String mImageUrl;
    public String mName;
    public String mCompany;

    public CatItem(int itemType, String name, String imageData, String company) {
        super(itemType);
        mName = name;
        mImageUrl = imageData;
        mCompany = company;
    }
    protected CatItem(Parcel in) {
        super(in);
        mImageUrl = in.readString();
        mName = in.readString();
        mCompany = in.readString();
    }
    public static final Creator<CatItem> CREATOR = new Creator<CatItem>() {
        @Override
        public CatItem createFromParcel(Parcel in) {
            return new CatItem(in);
        }

        @Override
        public CatItem[] newArray(int size) {
            return new CatItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(mImageUrl);
        parcel.writeString(mName);
        parcel.writeString(mCompany);
    }

    @Override
    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public String getItemName() {
        return mName;
    }
}
