package com.appestan.sharona.Network;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {

    @SerializedName("username")
    String username;
    @SerializedName("first_name")
    String first_name;
    @SerializedName("last_name")
    String last_name;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("national_id")
    String national_id;

//    {
//        "username":"shervin",
//            "first_name":"shervin",
//            "last_name":"shoorei",
//            "email":"shervinox@google.com",
//            "password":"12345678",
//            "national_id":"0012546763"
//    }


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirst_name()
    {
        return first_name;
    }

    public void setFirst_name(String first_name)
    {
        this.first_name = first_name;
    }

    public String getLast_name()
    {
        return last_name;
    }

    public void setLast_name(String last_name)
    {
        this.last_name = last_name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNational_id()
    {
        return national_id;
    }

    public void setNational_id(String national_id)
    {
        this.national_id = national_id;
    }
}
