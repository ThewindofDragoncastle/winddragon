package com.example.winddragon.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 40774 on 2017/8/29.
 */
//利用回调函数处理 返回两项onFailture onResponse
public class HttpUtil {
    public static void sendRequest(String address,okhttp3.Callback callback)
    {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
