package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.general.Image;

import java.util.List;

public class SearchResponse {

    @SerializedName("data")
    @Expose
    private List<Clothes> data = null;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("userMessage")
    @Expose
    private String userMessage;
    @SerializedName("meta")
    @Expose
    private List<Object> meta = null;

    public List<Clothes> getClothes()
    {
        return data;
    }

    public void setData(List<Clothes> data)
    {
        this.data = data;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getUserMessage()
    {
        return userMessage;
    }

    public void setUserMessage(String userMessage)
    {
        this.userMessage = userMessage;
    }

    public List<Object> getMeta()
    {
        return meta;
    }

    public void setMeta(List<Object> meta)
    {
        this.meta = meta;
    }





    public class SwapCondition {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("size")
        @Expose
        private String size;

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }

        public String getBrand()
        {
            return brand;
        }

        public void setBrand(String brand)
        {
            this.brand = brand;
        }

        public String getSize()
        {
            return size;
        }

        public void setSize(String size)
        {
            this.size = size;
        }

    }

}
