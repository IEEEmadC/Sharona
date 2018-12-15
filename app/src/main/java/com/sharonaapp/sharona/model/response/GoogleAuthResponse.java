package com.sharonaapp.sharona.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleAuthResponse {

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
    private GoogleAuthData data;

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

    public GoogleAuthData getData()
    {
        return data;
    }

    public void setData(GoogleAuthData data)
    {
        this.data = data;
    }

    public class GoogleAuthData {

        @SerializedName("registered")
        @Expose
        private Boolean registered;
        @SerializedName("access_token")
        @Expose
        private String accessToken;

        public Boolean getRegistered()
        {
            return registered;
        }

        public void setRegistered(Boolean registered)
        {
            this.registered = registered;
        }

        public String getAccessToken()
        {
            return accessToken;
        }

        public void setAccessToken(String accessToken)
        {
            this.accessToken = accessToken;
        }

    }



}
