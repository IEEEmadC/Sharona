package com.appestan.sharona.Model;

import com.appestan.sharona.Network.ClothesResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clothes implements Serializable {

    private int id;
    private String userName;
    private String type;
    private String brand;
    private String color;
    private String size;
    private String originalPrice;
    private String rentalPrice;
    private List<String> imageUrl = Arrays.asList("https://encrypted-tbn2.gstatic.com/shopping?q=tbn:ANd9GcSaws98guWmQyUO_9uA_6mmDEB0PqH68FkXU3Q6sgd4cBqefEMPxEKIVhzi5TxfNMs7nqbu4YQM&usqp=CAE",
            "https://mms-images.out.customink.com/mms/images/catalog/colors/04604/views/alt/front_medium_extended.png?design=djn0-00an-000p&placeMax=1&placeUseProduct=1&placeMaxPct=0.8",
            "https://mms-images-prod.imgix.net/mms/images/catalog/271f962a217a8743ce242a18da753534/styles/4600/catalog_detail_image_large.jpg?ixlib=rails-2.1.4&w=700&h=700&fit=fill&bg=ffffff&dpr=1&q=60&fm=pjpg&auto=compress&trim=auto&trimmd=0",
            "https://mms-images-prod.imgix.net/mms/images/catalog/0ab75b421fc037b55afdf107081882ac/styles/4600/supporting/1.jpg?ixlib=rails-2.1.4&w=425&h=495&fit=fill&bg=ffffff&dpr=1&q=39&fm=pjpg&auto=compress&trim=auto&trimmd=0",
            "https://mms-images-prod.imgix.net/mms/images/catalog/1990105835219a32f557bfa82b781d4a/styles/4600/supporting/2.jpg?ixlib=rails-2.1.4&w=425&h=495&fit=fill&bg=ffffff&dpr=1&q=39&fm=pjpg&auto=compress&trim=auto&trimmd=0");
    private String title;
    private String description;

    public Clothes(ClothesResponse.Datum clothesDataResponse)
    {
//        userName = clothesDataResponse.getUserName;
        id = clothesDataResponse.getId();
        type = clothesDataResponse.getType();
        brand = clothesDataResponse.getBrand();
        color = clothesDataResponse.getColor();
        size = clothesDataResponse.getSize();
        originalPrice = clothesDataResponse.getBuyPrice();
        rentalPrice = clothesDataResponse.getRentPrice();
//        imageUrl = clothesDataResponse.getImageUrl();
        if (clothesDataResponse.getTitle() != null)
        {
            title = clothesDataResponse.getTitle().toString();
        }
        description = clothesDataResponse.getDescription();

    }

    public Clothes()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

    public String getOriginalPrice()
    {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice)
    {
        this.originalPrice = originalPrice;
    }

    public String getRentalPrice()
    {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice)
    {
        this.rentalPrice = rentalPrice;
    }

    public List<String> getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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
