package com.sharonaapp.sharona.network;

import com.sharonaapp.sharona.model.IncomingOfferResponse;
import com.sharonaapp.sharona.model.OutgoingOfferResponse;
import com.sharonaapp.sharona.model.general.Report;
import com.sharonaapp.sharona.model.request.GeoClothesRequest;
import com.sharonaapp.sharona.model.request.GoogleAuthRequest;
import com.sharonaapp.sharona.model.request.LendRequest;
import com.sharonaapp.sharona.model.request.OauthRequest;
import com.sharonaapp.sharona.model.request.SellRequest;
import com.sharonaapp.sharona.model.request.TokenRequest;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;
import com.sharonaapp.sharona.model.response.GeoClothesResponse;
import com.sharonaapp.sharona.model.response.GoogleAuthResponse;
import com.sharonaapp.sharona.model.response.LendResponse;
import com.sharonaapp.sharona.model.response.LikeResponse;
import com.sharonaapp.sharona.model.response.MyClosetResponse;
import com.sharonaapp.sharona.model.response.OauthResponse;
import com.sharonaapp.sharona.model.response.ProfileResponse;
import com.sharonaapp.sharona.model.response.ReportResponse;
import com.sharonaapp.sharona.model.response.SearchResponse;
import com.sharonaapp.sharona.model.response.SellResponse;
import com.sharonaapp.sharona.model.response.TokenResponse;

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
    @Headers("Accept: application/json")
    Call<ExploreClothesResponse> getClothes();

    @GET("api/clothes")
    Call<SearchResponse> search(@Query("type") String type, @Query("size") String size, @Query("brand") String brand, @Query("color") String color, @Query("gender") String gender);

    @POST("api/clothes")
    Call<UploadClothesInfoPartResponse> uploadClothes(@Body UploadClothesInfoPartRequest uploadClothesInfoPartRequest);

    @Multipart
    @POST("api/clothes/{id}")
    @Headers("Accept: application/x-www-form-urlencoded")
    Call<UploadClothesInfoPartResponse> imagePartOfUploadClothes(@Part("_method") RequestBody methodType, @Part() MultipartBody.Part image, @Path("id") int clothesId);

//    @Headers({"Content-Type: application/json; charset=utf-8","Accept: application/json"})
//    @POST("oauth/token")
//    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/users/register")
    Call<SignUpResponse> signUp(@Body SignUpRequest signUpRequest);

    @GET("api/account/profile")
    Call<ProfileResponse> profile();

    @Headers("Accept: application/json")
    @GET("api/account/clothes")
    Call<MyClosetResponse> getMyCloset();

    @Headers({"Content-Type: application/json; charset=utf-8", "Accept: application/json"})
    @GET("api/inbox")
    Call<IncomingOfferResponse> inbox();

    @Headers({"Content-Type: application/json; charset=utf-8", "Accept: application/json"})
    @GET("api/outbox")
    Call<OutgoingOfferResponse> outbox();

    @POST("oauth/token")
    Call<OauthResponse> oauth(@Body OauthRequest oauthRequest);

    @POST("api/auth/google")
    Call<GoogleAuthResponse> googleOAuth(@Body GoogleAuthRequest googleAuthRequest);

    @POST("api/push-tokens")
    Call<TokenResponse> pushTokens(@Body TokenRequest tokenRequest);

    @POST("api/exchange-requests")
    Call<SellResponse> sell(@Body SellRequest sellRequest);

    @POST("api/exchange-requests")
    Call<LendResponse> lend(@Body LendRequest lendRequest);

    @POST("api/reports")
    Call<ReportResponse> report(@Body Report report);

    @POST("api/geo/clothes")
    Call<GeoClothesResponse> geoClothes(@Body GeoClothesRequest geoClothesRequest);

    @POST("api/clothes/{id}/likes")
    Call<LikeResponse> triggerLike(@Path("id") int clothesId);
}
