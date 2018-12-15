package com.sharonaapp.sharona.model.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

    @SerializedName("clothes_id")
    @Expose
    private Integer clothesId;
    @SerializedName("reason")
    @Expose
    private String reason;

    public Integer getClothesId()
    {
        return clothesId;
    }

    public void setClothesId(Integer clothesId)
    {
        this.clothesId = clothesId;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

}
