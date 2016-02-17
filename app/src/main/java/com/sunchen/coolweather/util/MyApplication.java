package com.sunchen.coolweather.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by sunchen on 2016/2/17.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
