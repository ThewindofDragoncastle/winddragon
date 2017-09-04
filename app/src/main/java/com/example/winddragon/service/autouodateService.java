package com.example.winddragon.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.example.winddragon.gson.Weather;
import com.example.winddragon.util.HttpUtil;
import com.example.winddragon.util.Utility;
import com.example.winddragon.weather_Activity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class autouodateService extends Service {
    public autouodateService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         updateBingpic();
        updateweather();
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int time=1000*3600;
        long triggerAttime= SystemClock.elapsedRealtime()+time;
        Intent i=new Intent(this,autouodateService.class);
        PendingIntent pi=PendingIntent.getService(this,0,i,0);
        alarmManager.cancel(pi);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,time,pi);
        return super.onStartCommand(intent, flags, startId);
    }
    private void updateBingpic()
    {
        String requesturl="http://guolin.tech/api/bing_pic";
        HttpUtil.sendRequest(requesturl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                SharedPreferences.Editor sharedPreferences= PreferenceManager.getDefaultSharedPreferences(autouodateService.this).edit();
                sharedPreferences.putString("bingpic",response.body().string());
                sharedPreferences.apply();
            }
        });
    }
    private void updateweather() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(autouodateService.this);
        String weather = sharedPreferences.getString("weather", null);
        if (weather != null) {
            final Weather weather1=Utility.handleweatherResponse(weather);
            String weatherId=weather1.basic.weatherId;
            String requesturl="http://guolin.tech/api/weather?cityid="+weatherId+"&key=aff3c588e85e4ce5aae8d331617756ab";
            HttpUtil.sendRequest(requesturl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responsetext=response.body().string();
                    if(weather1!=null&&weather1.status.equals("ok"))
                    {
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(autouodateService.this).edit();
                        editor.putString("weather",responsetext);
                        editor.apply();

                    }
                }
            });
        }
    }
}
