package com.sharonaapp.sharona.network;

public class Url {

    public static final String BASE_URL = "http://sharonaapp.com/";

    public static final String CLOTHES = "api/clothes";
    public static final String LOGIN = BASE_URL + "oauth/token";
    public static final String SIGNUP = BASE_URL + "api/users/register";
    public static final String BUY_OR_RENT = BASE_URL + "api/exchange-requests";
    public static final String SEARCH = BASE_URL + CLOTHES;
    public static final String ADD_CLOTHES = BASE_URL + CLOTHES;
}
