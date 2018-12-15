package com.sharonaapp.sharona.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadClothesInfoPartRequest {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("new_or_used_status")
    @Expose
    private String newOrUsedStatus;
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
    @SerializedName("swap_condition")
    @Expose
    private SwapCondition swapCondition;

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

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getNewOrUsedStatus()
    {
        return newOrUsedStatus;
    }

    public void setNewOrUsedStatus(String newOrUsedStatus)
    {
        this.newOrUsedStatus = newOrUsedStatus;
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

    public SwapCondition getSwapCondition()
    {
        return swapCondition;
    }

    public void setSwapCondition(SwapCondition swapCondition)
    {
        this.swapCondition = swapCondition;
    }


    public class SwapCondition {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("color")
        @Expose
        private String color;

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

    }

    @Override
    public String toString()
    {
        return "UploadClothesInfoPartRequest{" +
                "brand='" + brand + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", gender='" + gender + '\'' +
                ", newOrUsedStatus='" + newOrUsedStatus + '\'' +
                ", buyPrice=" + buyPrice +
                ", rentPrice=" + rentPrice +
                ", description='" + description + '\'' +
                ", sellable=" + sellable +
                ", lendable=" + lendable +
                ", swapCondition=" + swapCondition +
                '}';
    }
}
