package com.example.winddragon.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/8/29.
 */

public class County extends DataSupport {
    private int CurrentId;
    private int CountyCode;
    private String CountyName;

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        this.CurrentId = currentId;
    }

    public int getCountyCode() {
        return CountyCode;
    }

    public void setCountyCode(int CountyCode) {
        this.CountyCode = CountyCode;
    }

    public void setCountyName(String CountyName) {
        this.CountyName = CountyName;
    }

    public String getCountyName() {
        return CountyName;
    }
}