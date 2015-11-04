package com.example.deveshmittal.myapplication.model;

import android.os.Parcel;

import com.example.deveshmittal.myapplication.common.RecyclerViewItem;

/**
 * Created by deveshmittal on 02/11/15.
 */
public class DogItem extends RecyclerViewItem {

    public String mImageUrl;
    public String mName;
    public String mEyeColor;
    public Integer mFriends;

    public DogItem(int itemType, String name, String imageData, String eyeColor, Integer friends) {
        super(itemType);
        mName = name;
        mImageUrl = imageData;
        mEyeColor = eyeColor;
        mFriends = friends;
    }

    protected DogItem(Parcel in) {
        super(in);
        mImageUrl = in.readString();
        mName = in.readString();
        mEyeColor = in.readString();
    }

    public static final Creator<DogItem> CREATOR = new Creator<DogItem>() {
        @Override
        public DogItem createFromParcel(Parcel in) {
            return new DogItem(in);
        }

        @Override
        public DogItem[] newArray(int size) {
            return new DogItem[size];
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
        parcel.writeString(mEyeColor);
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

