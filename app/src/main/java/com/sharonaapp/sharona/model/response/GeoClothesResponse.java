package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoClothesResponse {

    @SerializedName("user_message")
    @Expose
    private String userMessage;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<GeoClothesResponse> data = null;

    public String getUserMessage()
    {
        return userMessage;
    }

    public void setUserMessage(String userMessage)
    {
        this.userMessage = userMessage;
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

    public List<GeoClothesResponse> getData()
    {
        return data;
    }

    public void setData(List<GeoClothesResponse> data)
    {
        this.data = data;
    }

    public class Clothe {

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
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("buy_price")
        @Expose
        private String buyPrice;
        @SerializedName("rent_price")
        @Expose
        private String rentPrice;
        @SerializedName("owner_id")
        @Expose
        private Integer ownerId;
        @SerializedName("lendable")
        @Expose
        private Boolean lendable;
        @SerializedName("sellable")
        @Expose
        private Boolean sellable;
        @SerializedName("available")
        @Expose
        private Boolean available;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("is_premium")
        @Expose
        private Integer isPremium;
        @SerializedName("new_or_used_status")
        @Expose
        private String newOrUsedStatus;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

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

        public String getSize()
        {
            return size;
        }

        public void setSize(String size)
        {
            this.size = size;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getBuyPrice()
        {
            return buyPrice;
        }

        public void setBuyPrice(String buyPrice)
        {
            this.buyPrice = buyPrice;
        }

        public String getRentPrice()
        {
            return rentPrice;
        }

        public void setRentPrice(String rentPrice)
        {
            this.rentPrice = rentPrice;
        }

        public Integer getOwnerId()
        {
            return ownerId;
        }

        public void setOwnerId(Integer ownerId)
        {
            this.ownerId = ownerId;
        }

        public Boolean getLendable()
        {
            return lendable;
        }

        public void setLendable(Boolean lendable)
        {
            this.lendable = lendable;
        }

        public Boolean getSellable()
        {
            return sellable;
        }

        public void setSellable(Boolean sellable)
        {
            this.sellable = sellable;
        }

        public Boolean getAvailable()
        {
            return available;
        }

        public void setAvailable(Boolean available)
        {
            this.available = available;
        }

        public String getDescription()
        {
            return description;
        }

        public void setDescription(String description)
        {
            this.description = description;
        }

        public Integer getIsPremium()
        {
            return isPremium;
        }

        public void setIsPremium(Integer isPremium)
        {
            this.isPremium = isPremium;
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

        public String getCreatedAt()
        {
            return createdAt;
        }

        public void setCreatedAt(String createdAt)
        {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt()
        {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt)
        {
            this.updatedAt = updatedAt;
        }

    }

    public class GeoClothesData {

        @SerializedName("lat")
        @Expose
        private String lat;
        @SerializedName("long")
        @Expose
        private String _long;
        @SerializedName("clothes")
        @Expose
        private List<Clothe> clothes = null;

        public String getLat()
        {
            return lat;
        }

        public void setLat(String lat)
        {
            this.lat = lat;
        }

        public String getLong()
        {
            return _long;
        }

        public void setLong(String _long)
        {
            this._long = _long;
        }

        public List<Clothe> getClothes()
        {
            return clothes;
        }

        public void setClothes(List<Clothe> clothes)
        {
            this.clothes = clothes;
        }

    }


}
