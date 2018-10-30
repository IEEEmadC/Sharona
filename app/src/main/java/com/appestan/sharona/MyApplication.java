package com.appestan.sharona;

import android.app.Application;

import com.appestan.sharona.Helper.LocaleHelper;
import com.appestan.sharona.Managers.SharedPreferencesManager;
import com.mapbox.mapboxsdk.Mapbox;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

    private static SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate()
    {
        super.onCreate();

        sharedPreferencesManager = new SharedPreferencesManager(getApplicationContext());

        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        if (LocaleHelper.getCurrentLocaleFromConfig(getApplicationContext()).toString().contains("fa"))
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/kufi_regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }
        else
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            );
        }


    }

    public static SharedPreferencesManager getSharedPreferencesManager()
    {
        return sharedPreferencesManager;
    }
}
