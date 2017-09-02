package com.example.winddragon.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 40774 on 2017/8/30.
 */

public class Basic {
    @SerializedName("city")
    public String cityname;
    @SerializedName("id")
    public String weatherId;
    public Update update;
    public class Update
    {
        @SerializedName("loc")
                public String updateTime;
    }
}
