package com.example.deveshmittal.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.deveshmittal.myapplication.volley.VolleyLib;

/**
 * Created by deveshmittal on 04/11/15.
 */
public class MyApplication extends Application{
    private static MyApplication    mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyLib.init(this);

    }
    public MyApplication() {
        mApplication = this;
    }
    public static Context getContext(){
        return mApplication.getApplicationContext();
    }

}
