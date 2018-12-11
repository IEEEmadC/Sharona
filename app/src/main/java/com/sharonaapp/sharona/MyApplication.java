package com.sharonaapp.sharona;

import android.app.Application;

import com.sharonaapp.sharona.manager.SharedPreferencesManager;
import com.sharonaapp.sharona.model.ConfigDAO;

import io.reactivex.annotations.Nullable;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyApplication extends Application {

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
                .setDefaultFontPath("fonts/f1.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


    }


    public static SharedPreferencesManager getSharedPreferencesManager()
    {
        return sharedPreferencesManager;
    }
}
