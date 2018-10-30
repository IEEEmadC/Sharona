package com.appestan.sharona.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddClothesImagePartRequest {

    @SerializedName("_method")
    @Expose
    private String _method = "put";
    @SerializedName("type")
    @Expose
    private String type;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }


}
