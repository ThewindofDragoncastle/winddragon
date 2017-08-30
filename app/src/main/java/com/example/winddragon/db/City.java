package com.example.winddragon.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/8/29.
 */

public class City extends DataSupport {
    private int id;
    private int CityCode;
    private String CityName;
    private int ProvinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceId(int provinceId) {
        this.ProvinceId = provinceId;
    }
public int getProvinceId()
{
   return ProvinceId;
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