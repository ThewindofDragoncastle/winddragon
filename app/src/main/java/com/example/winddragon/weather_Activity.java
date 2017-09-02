package com.example.winddragon;

import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.winddragon.gson.Forecast;
import com.example.winddragon.gson.Weather;
import com.example.winddragon.util.HttpUtil;
import com.example.winddragon.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class weather_Activity extends AppCompatActivity {

private ScrollView weatherlayout;

    private TextView titlecity;

    private TextView titledateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carwashText;

    private TextView sportText;

   private LinearLayout forecastLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_);
        //初始化各项目
        weatherlayout=(ScrollView)findViewById(R.id.weather_layout);
        titlecity=(TextView)findViewById(R.id.city_title);
        titledateTime=(TextView)findViewById(R.id.updatetime_title);
        degreeText=(TextView)findViewById(R.id.dragee_text);
        weatherInfoText=(TextView)findViewById(R.id.weather_text);
        forecastLayout=(LinearLayout)findViewById(R.id.forecast_layout);
        aqiText =(TextView)findViewById(R.id.aqi_aqi);
        pm25Text=(TextView)findViewById(R.id.pm25);
        comfortText=(TextView)findViewById(R.id.comfort_text);
        carwashText=(TextView)findViewById(R.id.car_wash_text);
        sportText=(TextView)findViewById(R.id.sport_text);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        String localdata=sharedPreferences.getString("weather",null);
        if(localdata==null)
        {//没有缓存
            String weatherId=getIntent().getStringExtra("weather_id");
            MyLog.i("weather_activity:",weatherId);
            weatherlayout.setVisibility(View.INVISIBLE);
            requestweather(weatherId);
        }
        else
        {
            Weather weather=Utility.handleweatherResponse(localdata);
            showWeather(weather);
        }

    }
    private void requestweather(String weatherId)
    {
        String weatherUrl="http://guolin.tech/api/weather?cityid="+weatherId+"&key=aff3c588e85e4ce5aae8d331617756ab";
        HttpUtil.sendRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(weather_Activity.this,"天气获取失败！",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
final String responsetext=response.body().string();
                final Weather weather= Utility.handleweatherResponse(responsetext);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(weather!=null&&weather.status.equals("ok"))
                        {
                            SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(weather_Activity.this).edit();
                            editor.putString("weather",responsetext);
                            editor.apply();
                            showWeather(weather);
                        }
                        else
                        {
                            Toast.makeText(weather_Activity.this,"天气获取失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    private void showWeather(Weather weather)
    {
        String cityname=weather.basic.cityname;
        String updatetime=weather.basic.update.updateTime.split(" ")[1];
        String degree=weather.now.temperature+"°C";
        String weatherInfo=weather.now.more.info;
        titlecity.setText(cityname);
        titledateTime.setText(updatetime);
        weatherInfoText.setText(weatherInfo);
        degreeText.setText(degree);
        forecastLayout.removeAllViews();
        for (Forecast forecast:weather.forecasts)
        {
            View view= LayoutInflater.from(this).inflate(R.layout.forcast_item,forecastLayout,false);
           TextView dateText=(TextView)view.findViewById(R.id.date_item);
            TextView infoText=(TextView)view.findViewById(R.id.info_item);
            TextView maxText=(TextView)view.findViewById(R.id.max_item);
            TextView minText=(TextView)view.findViewById(R.id.min_item);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
        }
        if(weather.aqi!=null)
        {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort="舒适度："+weather.suggestion.comfort.info;
        String carwash="洗车建议："+weather.suggestion.carwash.info;
        String sport="运动建议："+weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carwashText.setText(carwash);
        sportText.setText(sport);
        weatherlayout.setVisibility(View.VISIBLE);
    }
}
