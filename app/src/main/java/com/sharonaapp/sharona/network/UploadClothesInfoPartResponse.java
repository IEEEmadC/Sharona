package com.sharonaapp.sharona.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadClothesInfoPartResponse {

    @SerializedName("data")
    @Expose
    private UploadClothesInfoPart uploadClothesInfoPart;
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

    public UploadClothesInfoPart getData()
    {
        return uploadClothesInfoPart;
    }

    public void setData(UploadClothesInfoPart data)
    {
        this.uploadClothesInfoPart = data;
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

    public class UploadClothesInfoPart {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("buy_price")
        @Expose
        private Integer buyPrice;
        @SerializedName("rent_price")
        @Expose
        private Integer rentPrice;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("sellable")
        @Expose
        private Boolean sellable;
        @SerializedName("lendable")
        @Expose
        private Boolean lendable;
        @SerializedName("available")
        @Expose
        private Boolean available;
        @SerializedName("new_or_used_status")
        @Expose
        private String newOrUsedStatus;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("swap_condition")
        @Expose
        private SwapCondition swapCondition;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getBrand()
        {
            return brand;
        }

        public void setBrand(String brand)
        {
            this.brand = brand;
        }

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getSize()
        {
            return size;
        }

        public void setSize(String size)
        {
            this.size = size;
        }

        public Integer getBuyPrice()
        {
            return buyPrice;
        }

        public void setBuyPrice(Integer buyPrice)
        {
            this.buyPrice = buyPrice;
        }

        public Integer getRentPrice()
        {
            return rentPrice;
        }

        public void setRentPrice(Integer rentPrice)
        {
            this.rentPrice = rentPrice;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public Boolean getSellable()
        {
            return sellable;
        }

        public void setSellable(Boolean sellable)
        {
            this.sellable = sellable;
        }

        public Boolean getLendable()
        {
            return lendable;
        }

        public void setLendable(Boolean lendable)
        {
            this.lendable = lendable;
        }

        public Boolean getAvailable()
        {
            return available;
        }

        public void setAvailable(Boolean available)
        {
            this.available = available;
        }

        public String getNewOrUsedStatus()
        {
            return newOrUsedStatus;
        }

        public void setNewOrUsedStatus(String newOrUsedStatus)
        {
            this.newOrUsedStatus = newOrUsedStatus;
        }

        public String getGender()
        {
            return gender;
        }

        public void setGender(String gender)
        {
            this.gender = gender;
        }

        public List<String> getImages()
        {
            return images;
        }

        public void setImages(List<String> images)
        {
            this.images = images;
        }

        public SwapCondition getSwapCondition()
        {
            return swapCondition;
        }

        public void setSwapCondition(SwapCondition swapCondition)
        {
            this.swapCondition = swapCondition;
        }

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

