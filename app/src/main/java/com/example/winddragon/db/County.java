package com.example.winddragon.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/8/29.
 */

public class County extends DataSupport {
    private int id;
    private String CountyCode;
    private String CountyName;
    private int CityId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        this.CityId = cityId;
    }

    public String getCountyCode() {
        return CountyCode;
    }

    public void setCountyCode(String CountyCode) {
        this.CountyCode = CountyCode;
    }

    public void setCountyName(String CountyName) {
        this.CountyName = CountyName;
    }

    public String getCountyName() {
        return CountyName;
    }
}