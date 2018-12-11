package com.sharonaapp.sharona.model;

public class LoginRequest {
//            "username": "fmmajd@google.com",
//                "password": "12345678",
//                "grant_type": "password",
//                "client_id": 1,
//                "client_secret": "zcWRDOGvr6jJKhdrc7I1R626mXSg0ORztaNZmpYJ"
//        }

    String username;
    String password;
    String grant_type;
    int client_id;
    String client_secret;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getGrant_type()
    {
        return grant_type;
    }

    public void setGrant_type(String grant_type)
    {
        this.grant_type = grant_type;
    }

    public int getClient_id()
    {
        return client_id;
    }

    public void setClient_id(int client_id)
    {
        this.client_id = client_id;
    }

    public String getClient_secret()
    {
        return client_secret;
    }

    public void setClient_secret(String client_secret)
    {
        this.client_secret = client_secret;
    }

    @Override
    public String toString()
    {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grant_type='" + grant_type + '\'' +
                ", client_id=" + client_id +
                ", client_secret='" + client_secret + '\'' +
                '}';
    }
}
