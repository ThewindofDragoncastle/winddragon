package com.example.winddragon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.winddragon.db.City;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
    if(preferences.getString("weather",null)!=null)
    {
        Intent intent=new Intent(this,weather_Activity.class);
        startActivity(intent);
        finish();
    }
    }
}
