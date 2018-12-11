package com.sharonaapp.sharona.network;

import com.sharonaapp.sharona.model.OutgoingOfferResponse;
import com.sharonaapp.sharona.model.request.OauthRequest;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;
import com.sharonaapp.sharona.model.response.OauthResponse;
import com.sharonaapp.sharona.model.response.ProfileResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("api/clothes")
    Call<ExploreClothesResponse> getClothes();

    @GET("api/clothes")
    Call<ExploreClothesResponse> getSearch(@Query("size") String size, @Query("type") String type);

    @POST("api/clothes")
    Call<AddClothesResponse> addClothes(@Body AddClothesRequest addClothesRequest);

    @Multipart
    @POST("api/clothes/{id}")
    Call<AddClothesResponse> addClothesImagePart(@Part("_method") RequestBody methodType, @Part() MultipartBody.Part image, @Path("id") int clothesId);

//    @Headers({"Content-Type: application/json; charset=utf-8","Accept: application/json"})
//    @POST("oauth/token")
//    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/users/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("api/account/profile")
    Call<ProfileResponse> profile();

    @Headers({"Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @GET("api/clothes")
    Call<ExploreClothesResponse> getMyCloset();

    @Headers({"Content-Type: application/json; charset=utf-8","Accept: application/json"})
    @GET("api/outbox")
    Call<OutgoingOfferResponse>  outbox();

    @POST("oauth/token")
    Call<OauthResponse> oauth(@Body OauthRequest oauthRequest);
}
