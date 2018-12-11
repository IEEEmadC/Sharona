package com.sharonaapp.sharona.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddClothesRequest {

    @SerializedName("title")
    @Expose
    private String title;
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
    private Integer buyPrice = 0;
    @SerializedName("rent_price")
    @Expose
    private Integer rentPrice = 0;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("sellable")
    @Expose
    private boolean sellable;
    @SerializedName("lendable")
    @Expose
    private boolean lendable;

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

    public boolean isSellable()
    {
        return sellable;
    }

    public void setSellable(boolean sellable)
    {
        this.sellable = sellable;
    }

    public boolean isLendable()
    {
        return lendable;
    }

    public void setLendable(boolean lendable)
    {
        this.lendable = lendable;
    }
}
