package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sharonaapp.sharona.model.general.Clothes;

import java.util.List;

public class ClothesInnerResponse {

    @SerializedName("data")
    @Expose
    private Clothes clothesInner;
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

    public Clothes getClothesInner()
    {
        return clothesInner;
    }

    public void setClothesInner(Clothes clothesInner)
    {
        this.clothesInner = clothesInner;
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
        private Object color;
        @SerializedName("brand")
        @Expose
        private Object brand;
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

        public Object getColor()
        {
            return color;
        }

        public void setColor(Object color)
        {
            this.color = color;
        }

        public Object getBrand()
        {
            return brand;
        }

        public void setBrand(Object brand)
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
