package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Clothes implements Serializable {

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
    private String buyPrice;
    @SerializedName("rent_price")
    @Expose
    private String rentPrice;
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
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("swap_condition")
    @Expose
    private ClothesInnerResponse.SwapCondition swapCondition;

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

    public List<String> getImages()
    {
        return images;
    }

    public void setImages(List<String> images)
    {
        this.images = images;
    }

    public ClothesInnerResponse.SwapCondition getSwapCondition()
    {
        return swapCondition;
    }

    public void setSwapCondition(ClothesInnerResponse.SwapCondition swapCondition)
    {
        this.swapCondition = swapCondition;
    }

}
