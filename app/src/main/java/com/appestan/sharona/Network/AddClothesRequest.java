package com.appestan.sharona.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddClothesRequest {

    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("buy_price")
    @Expose
    private Integer buyPrice;
    @SerializedName("rent_price")
    @Expose
    private Integer rentPrice;
    @SerializedName("description")
    @Expose
    private String description;

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

}
