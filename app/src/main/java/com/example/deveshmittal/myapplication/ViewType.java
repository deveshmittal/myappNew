package com.example.deveshmittal.myapplication;

import com.example.deveshmittal.myapplication.binders.CatBinder;
import com.example.deveshmittal.myapplication.binders.DogBinder;
import com.example.deveshmittal.myapplication.common.BaseBinder;
import com.example.deveshmittal.myapplication.common.RecyclerViewItem;
import com.example.deveshmittal.myapplication.model.CatItem;
import com.example.deveshmittal.myapplication.model.DogItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by deveshmittal on 02/11/15.
 */
public enum ViewType {
    CAT(0, new CatBinder()) {
        @Override
        public RecyclerViewItem getItem(JSONObject json) {
           CatItem catItem = new CatItem(this.getItemType()
                   , json.optString("name"), json.optString("picture"),json.optString("company"));
            return catItem;
        }
    }, DOG(1, new DogBinder()) {
        @Override
        public RecyclerViewItem getItem(JSONObject json) {
            DogItem dogItem = new DogItem(this.getItemType()
                    , json.optString("name"), json.optString("picture"),json.optString("eyeColor"),json.optJSONArray("friends").length());
            return dogItem;
        }
    };

private static class Holder {
    static Map<Integer, ViewType> MAP = new HashMap<>();
}

private final int itemType;
private final BaseBinder binder;

    ViewType(int itemType, BaseBinder binder) {
        this.itemType = itemType;
        this.binder = binder;
        Holder.MAP.put(itemType, this);
    }

    public int getItemType() {
        return itemType ;
    }

    public BaseBinder getBinder() {
        return binder;
    }

    public abstract RecyclerViewItem getItem(JSONObject json);

    public static BaseBinder getBinder(int itemType) {
        return Holder.MAP.get(itemType).getBinder();
    }

    public static ViewType getViewType(int itemType) {
        return Holder.MAP.get(itemType);
    }

//    public static ImageData getImageData(JSONObject json) {
//        return new ImageData(json.optString("imageUrl"), json.optString("width"), json.optString("height"));
//    }
}