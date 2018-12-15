package com.sharonaapp.sharona.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleAuthRequest {

    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("google_token")
    @Expose
    private String googleToken;

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }

    public String getGoogleToken()
    {
        return googleToken;
    }

    public void setGoogleToken(String googleToken)
    {
        this.googleToken = googleToken;
    }
}
