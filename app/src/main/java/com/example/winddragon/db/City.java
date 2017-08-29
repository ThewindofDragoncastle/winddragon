package com.example.winddragon.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/8/29.
 */

public class City extends DataSupport {
    private int CurrentId;
    private int CityCode;
    private String CityName;

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        this.CurrentId = currentId;
    }

    public int getCityCode() {
        return CityCode;
    }

    public void setCityCode(int CityCode) {
        this.CityCode = CityCode;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getCityName() {
        return CityName;
    }
}