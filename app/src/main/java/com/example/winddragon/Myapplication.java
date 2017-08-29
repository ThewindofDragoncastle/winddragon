package com.example.winddragon;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * Created by 40774 on 2017/8/29.
 */

public class Myapplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        context=getApplicationContext();
        LitePal.initialize(context);
    }
    public static Context getContext()
    {
        return context;
    }
}
