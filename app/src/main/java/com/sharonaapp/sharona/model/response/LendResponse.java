package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sharonaapp.sharona.model.general.Clothes;

import java.util.List;

public class LendResponse {

    @SerializedName("data")
    @Expose
    private LendData data;
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

    public LendData getData()
    {
        return data;
    }

    public void setData(LendData data)
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


    public class LendData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("clothes_id")
        @Expose
        private Integer clothesId;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
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
        @SerializedName("user_phone")
        @Expose
        private String userPhone;
        @SerializedName("user_address")
        @Expose
        private String userAddress;

        public Integer getId()
        {
            return id;
        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public Integer getClothesId()
        {
            return clothesId;
        }

        public void setClothesId(Integer clothesId)
        {
            this.clothesId = clothesId;
        }

        public Integer getUserId()
        {
            return userId;
        }

        public void setUserId(Integer userId)
        {
            this.userId = userId;
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

        public String getUserPhone()
        {
            return userPhone;
        }

        public void setUserPhone(String userPhone)
        {
            this.userPhone = userPhone;
        }

        public String getUserAddress()
        {
            return userAddress;
        }

        public void setUserAddress(String userAddress)
        {
            this.userAddress = userAddress;
        }

    }


}
