package com.appestan.sharona.Network;

import com.appestan.sharona.Helper.SearchParameterHelper;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("api/clothes")
    Call<ClothesResponse> getClothes();

    @GET("api/clothes")
    Call<ClothesResponse> getSearch(@Query("size") String size, @Query("type") String type);

    @POST("api/clothes")
    Call<AddClothesResponse> addClothes(@Body AddClothesRequest addClothesRequest);

    @Multipart
    @POST("api/clothes/{id}")
    Call<AddClothesResponse> addClothesImagePart(@Part("_method") RequestBody methodType, @Part() MultipartBody.Part image, @Path("id") int clothesId);


    @POST("oauth/token")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/users/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("api/users/{id}")
    Call<SignUpResponse> profile(@Path("id") int groupId);


    public class LoginResponse {

        @SerializedName("token_type")
        @Expose
        private String tokenType;
        @SerializedName("expires_in")
        @Expose
        private Integer expiresIn;
        @SerializedName("access_token")
        @Expose
        private String accessToken;
        @SerializedName("refresh_token")
        @Expose
        private String refreshToken;

        public String getTokenType()
        {
            return tokenType;
        }

        public void setTokenType(String tokenType)
        {
            this.tokenType = tokenType;
        }

        public Integer getExpiresIn()
        {
            return expiresIn;
        }

        public void setExpiresIn(Integer expiresIn)
        {
            this.expiresIn = expiresIn;
        }

        public String getAccessToken()
        {
            return accessToken;
        }

        public void setAccessToken(String accessToken)
        {
            this.accessToken = accessToken;
        }

        public String getRefreshToken()
        {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken)
        {
            this.refreshToken = refreshToken;
        }

        @Override
        public String toString()
        {
            return "LoginResponse{" +
                    "tokenType='" + tokenType + '\'' +
                    ", expiresIn=" + expiresIn +
                    ", accessToken='" + accessToken + '\'' +
                    ", refreshToken='" + refreshToken + '\'' +
                    '}';
        }
    }


    class LoginRequest {
//        {
//            "username": "fmmajd@google.com",
//                "password": "12345678",
//                "grant_type": "password",
//                "client_id": 1,
//                "client_secret": "zcWRDOGvr6jJKhdrc7I1R626mXSg0ORztaNZmpYJ"
//        }

        @SerializedName("username")
        String username;
        @SerializedName("password")
        String password;
        @SerializedName("grant_type")
        String grant_type;
        @SerializedName("client_id")
        int client_id;
        @SerializedName("client_secret")
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
}
