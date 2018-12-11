package com.sharonaapp.sharona.network;

import android.util.Log;

import com.sharonaapp.sharona.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sharonaapp.sharona.network.Url.BASE_URL;

public class NetworkManager {

    private static final String TAG = "NetworkManager";

    private static NetworkManager netWorkManager;
    private Retrofit retrofit;

    public static NetworkManager getInstance()
    {
        if (netWorkManager == null)
        {
            netWorkManager = new NetworkManager();
        }
        return netWorkManager;
    }

    private NetworkManager()
    {
        createAdapter();
    }

    private void createAdapter()
    {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException
                    {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Bearer " + getToken())
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Cache-Control", "no-cache")
                                .addHeader("User-Agent", "Sharona")
                                .build();

                        Log.d(TAG, "intercept: added token to header as: " + getToken());

                        RequestBody requestBody = newRequest.body();
                        if (requestBody != null)
                        {
                            Log.d(TAG, "intercept: " + chain.request().method() + " " + chain.request().url());

                            Buffer buffer = new Buffer();
                            requestBody.writeTo(buffer);
                            Charset charset = Charset.forName("UTF-8");
                            MediaType contentType = requestBody.contentType();
                            if (contentType != null)
                            {

                                Log.d(TAG, "intercept: " + buffer.readString(charset));

//                            + chain.connection().protocol() +
                            }
                        }

                        return chain.proceed(newRequest);
                    }
                });

//                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public <T> T getEndpointApi(Class<T> service)
    {
        return retrofit.create(service);
    }

    public static String getToken()
    {
        if (MyApplication.getSharedPreferencesManager().read("token") instanceof String)
        {
            return MyApplication.getSharedPreferencesManager().read("token").toString();
        }
        else
        {
            return "";
        }
    }

    public static String fetchErrorMessage(String json, String key)
    {
        String trimmedString = null;

        try
        {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
