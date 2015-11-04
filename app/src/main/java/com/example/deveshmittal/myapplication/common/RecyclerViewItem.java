package com.example.deveshmittal.myapplication.common;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by deveshmittal on 02/11/15.
 */

public abstract class RecyclerViewItem implements Parcelable{
    protected int itemType;


    protected RecyclerViewItem(Parcel in) {
        itemType = in.readInt();
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(itemType);
    }


    public RecyclerViewItem(int itemType) {
        this.itemType = itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getItemType() {
        return this.itemType;
    }

    abstract public String getImageUrl();

    abstract public String getItemName();

}