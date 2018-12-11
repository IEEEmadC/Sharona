package com.sharonaapp.sharona.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddClothesResponse {

    public class Data {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("title")
        @Expose
        private Object title;
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

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public Object getTitle()
        {
            return title;
        }

        public void setTitle(Object title)
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

    }


    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    public Data getData()
    {
        return data;
    }

    public void setData(Data data)
    {
        this.data = data;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    public Boolean getError()
    {
        return error;
    }

    public void setError(Boolean error)
    {
        this.error = error;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

}

