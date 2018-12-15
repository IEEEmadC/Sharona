package com.sharonaapp.sharona.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeoClothesRequest {


    @SerializedName("lat")
    @Expose
    private String latValue;
    @SerializedName("long")
    @Expose
    private String longValue;

    public String getLatValue()
    {
        return latValue;
    }

    public void setLatValue(String latValue)
    {
        this.latValue = latValue;
    }

    public String getLongValue()
    {
        return longValue;
    }

    public void setLongValue(String longValue)
    {
        this.longValue = longValue;
    }
}
