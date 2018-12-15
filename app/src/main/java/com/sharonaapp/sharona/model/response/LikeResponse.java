package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LikeResponse {

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
    private LikeData data;

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

    public LikeData getData()
    {
        return data;
    }

    public void setData(LikeData data)
    {
        this.data = data;
    }


    public class LikeData {

        @SerializedName("likes")
        @Expose
        private Integer likes;

        public Integer getLikes()
        {
            return likes;
        }

        public void setLikes(Integer likes)
        {
            this.likes = likes;
        }

    }

}
