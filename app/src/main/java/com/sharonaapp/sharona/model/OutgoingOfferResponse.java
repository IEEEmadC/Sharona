package com.sharonaapp.sharona.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sharonaapp.sharona.model.general.Clothes;

import java.util.List;

public class OutgoingOfferResponse {


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
