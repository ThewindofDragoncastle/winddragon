package com.example.winddragon;

import android.util.Log;

/**
 * Created by 40774 on 2017/8/29.
 */

public class MyLog {
    private static final int currentversion=5;
    private static final int Vversion=1;
    private static final int Dversion=2;
    private static final int Iversion=3;
    private static final  int Wversion=4;
    private static final int Eversion=5;
    public static void v(String tag,String content)
    {
        if(currentversion>=Vversion)
        {
            Log.v(tag,content);
        }
    }
    public static void d(String tag,String content)
    {
        if(currentversion>=Dversion)
        {
            Log.d(tag,content);
        }
    }
    public static void i(String tag,String content)
    {
        if(currentversion>=Iversion)
        {
            Log.i(tag,content);
        }
    }
    public static void w(String tag,String content)
    {
        if(currentversion>=Wversion)
        {
            Log.w(tag,content);
        }
    }
    public static void e(String tag,String content)
    {
        if(currentversion>=Eversion)
        {
            Log.e(tag,content);
        }
    }
}
