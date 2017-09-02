package com.example.winddragon.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 40774 on 2017/9/2.
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;
    public class More
    {
        @SerializedName("txt")
        public String info;
    }
}