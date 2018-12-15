package com.sharonaapp.sharona;

import android.app.Application;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.sharonaapp.sharona.manager.SharedPreferencesManager;
import com.sharonaapp.sharona.model.ConfigDAO;
import com.sharonaapp.sharona.model.request.TokenRequest;
import com.sharonaapp.sharona.model.response.TokenResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.PUSH_TOKEN_HAS_CHANGED;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    private static SharedPreferencesManager sharedPreferencesManager;

    @Nullable
    public static ConfigDAO getConfig()
    {
        if (sharedPreferencesManager.read(SharedPreferencesManager.CONFIG) instanceof ConfigDAO)
        {
            return ((ConfigDAO) sharedPreferencesManager.read(SharedPreferencesManager.CONFIG));
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/myriad_pro_regular_1.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                Log.d(TAG, "onCreate: PTOKEN" + task.getResult().getToken());
                handleTokenForNotification(true);
            }
        });

    }


    public static SharedPreferencesManager getSharedPreferencesManager()
    {
        return sharedPreferencesManager;
    }

    public static void handleTokenForNotification(boolean checkIfSendingIsNeeded)
    {
//        if (checkIfSendingIsNeeded)
//        {
//            if (!(MyApplication.getSharedPreferencesManager().read(PUSH_TOKEN_HAS_CHANGED) instanceof Boolean) ||
//                    (MyApplication.getSharedPreferencesManager().read(PUSH_TOKEN_HAS_CHANGED) instanceof Boolean &&
//                            (Boolean) MyApplication.getSharedPreferencesManager().read(PUSH_TOKEN_HAS_CHANGED)))
//            {
//                sendPushTokenToServer();
//            }
//
//        } else
        {
            sendPushTokenToServer();
        }


    }

    private static void sendPushTokenToServer()
    {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                Log.d(TAG, "sendPushTokenToServer onComplete Task PTOKEN" + task.getResult().getToken());
                TokenRequest tokenRequest = new TokenRequest();
                tokenRequest.setToken(task.getResult().getToken());
                Call<TokenResponse> pushTokenCall = NetworkManager.getInstance().getEndpointApi(Api.class).pushTokens(tokenRequest);
                pushTokenCall.enqueue(new Callback<TokenResponse>() {
                    @Override
                    public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response)
                    {
                        MyApplication.getSharedPreferencesManager().persist(PUSH_TOKEN_HAS_CHANGED, Boolean.FALSE);
                        Log.d(TAG, "sendPushTokenToServer onResponse: PTOKEN" + response.isSuccessful());
                    }

                    @Override
                    public void onFailure(Call<TokenResponse> call, Throwable t)
                    {
                        Log.d(TAG, "sendPushTokenToServer onFailure: PTOKEN");

                    }
                });
            }
            else
            {
                Log.d(TAG, "onComplete: refresh failed");
            }

        });
    }
}
