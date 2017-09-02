package com.example.winddragon.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 40774 on 2017/9/2.
 */

public class Weather {
    public String status;//success return ok,if not return the cause of failed;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public  Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecasts;
}
