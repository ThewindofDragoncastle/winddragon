package com.example.winddragon.db;

import org.litepal.crud.DataSupport;

/**
 * Created by 40774 on 2017/8/29.
 */

public class Province extends DataSupport {
    private int CurrentId;
    private int ProvinceCode;
    private String ProvinceName;

    public int getCurrentId() {
        return CurrentId;
    }

    public void setCurrentId(int currentId) {
        this.CurrentId = currentId;
    }

    public int getProvinceCode() {
        return ProvinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.ProvinceCode = provinceCode;
    }

    public void setProvinceName(String provinceName) {
        this.ProvinceName = provinceName;
    }

    public String getProvinceName() {
        return ProvinceName;
    }
}
