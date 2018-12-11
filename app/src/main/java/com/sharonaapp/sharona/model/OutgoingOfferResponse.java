package com.sharonaapp.sharona.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OutgoingOfferResponse {


    public class Clothes {

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
        @SerializedName("images")
        @Expose
        private List<Image> images = null;

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

        public List<Image> getImages()
        {
            return images;
        }

        public void setImages(List<Image> images)
        {
            this.images = images;
        }

    }


    public class OutgoingOfferResponseData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("clothes_id")
        @Expose
        private String clothesId;
        @SerializedName("requested_user_id")
        @Expose
        private String requestedUserId;
        @SerializedName("requester_user_id")
        @Expose
        private String requesterUserId;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("start_date")
        @Expose
        private String startDate;
        @SerializedName("return_date")
        @Expose
        private String returnDate;
        @SerializedName("clothes")
        @Expose
        private Clothes clothes;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String getClothesId()
        {
            return clothesId;
        }

        public void setClothesId(String clothesId)
        {
            this.clothesId = clothesId;
        }

        public String getRequestedUserId()
        {
            return requestedUserId;
        }

        public void setRequestedUserId(String requestedUserId)
        {
            this.requestedUserId = requestedUserId;
        }

        public String getRequesterUserId()
        {
            return requesterUserId;
        }

        public void setRequesterUserId(String requesterUserId)
        {
            this.requesterUserId = requesterUserId;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getStartDate()
        {
            return startDate;
        }

        public void setStartDate(String startDate)
        {
            this.startDate = startDate;
        }

        public String getReturnDate()
        {
            return returnDate;
        }

        public void setReturnDate(String returnDate)
        {
            this.returnDate = returnDate;
        }

        public Clothes getClothes()
        {
            return clothes;
        }

        public void setClothes(Clothes clothes)
        {
            this.clothes = clothes;
        }

    }

    @SerializedName("data")
    @Expose
    private List<OutgoingOfferResponseData> data = null;
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

    public List<OutgoingOfferResponseData> getData()
    {
        return data;
    }

    public void setData(List<OutgoingOfferResponseData> data)
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


    public class Image {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("path")
        @Expose
        private String path;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getPath()
        {
            return path;
        }

        public void setPath(String path)
        {
            this.path = path;
        }

    }
}
