package com.sharonaapp.sharona.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenRequest {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("provider")
    @Expose
    private String provider = "firebase";
    @SerializedName("device")
    @Expose
    private String device = "todo";

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getProvider()
    {
        return provider;
    }

    public void setProvider(String provider)
    {
        this.provider = provider;
    }

    public String getDevice()
    {
        return device;
    }

    public void setDevice(String device)
    {
        this.device = device;
    }


}
