package com.example.winddragon.util;

import android.text.TextUtils;

import com.example.winddragon.MyLog;
import com.example.winddragon.db.City;
import com.example.winddragon.db.County;
import com.example.winddragon.db.Province;
import com.example.winddragon.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 40774 on 2017/8/30.
 */
//解析数据json方式
public class Utility {
    public static Boolean handleProvinces(String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(object.getString("name"));
                    province.setProvinceCode(object.getInt("id"));
                    province.save();
                    MyLog.e("Utility:","province name:"+province.getProvinceName()+"  province code"+province.getProvinceCode());
                }
                return true;
            }catch (JSONException e)
            {
                MyLog.i("Utility:","解析省级数据出错。");
            }
        }
        return false;
    }
    public static Boolean handleCitys(String response,int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    City city=new City();
                    city.setCityName(object.getString("name"));
                    city.setCityCode(object.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                    MyLog.i("Utility:","city name:"+city.getCityName()+"  City code"+city.getCityCode());
                }
                return true;
            }catch (JSONException e)
            {
                MyLog.e("Utility:","解析市级数据出错。");
            }
        }
        return false;
    }
    public static Boolean handleCounty(String response,int cityid)
    {
        if(!TextUtils.isEmpty(response))
        {
            try {
                JSONArray array=new JSONArray(response);
                for (int i=0;i<array.length();i++) {
                    JSONObject object = array.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(object.getString("name"));
                    county.setCountyCode(object.getString("weather_id"));
                    county.setCityId(cityid);
                    county.save();
                    MyLog.i("Utility:","County name:"+county.getCountyName()+"  County code"+county.getCountyCode());
                }
                return true;
            }catch (JSONException e)
            {
                MyLog.e("Utility:","解析县级数据出错。");
            }
        }
        return false;
    }
    public  static Weather handleweatherResponse(String response)
    {
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray array=jsonObject.getJSONArray("HeWeather");
            String content=array.getJSONObject(0).toString();
            return new Gson().fromJson(content,Weather.class);
        } catch (JSONException e) {
            MyLog.d("Utility:","解析出错");
        }
        return null;
    }
}
