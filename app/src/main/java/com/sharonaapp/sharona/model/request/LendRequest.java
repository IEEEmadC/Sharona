package com.sharonaapp.sharona.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LendRequest {

    @SerializedName("clothes_id")
    @Expose
    private Integer clothesId;
    @SerializedName("type")
    @Expose
    private String type = "LEND";
    @SerializedName("start_date")
    @Expose
    private String startDate = "2019-11-9";
    @SerializedName("return_date")
    @Expose
    private String returnDate = "2019-11-12";

    public Integer getClothesId()
    {
        return clothesId;
    }

    public void setClothesId(Integer clothesId)
    {
        this.clothesId = clothesId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }


}
