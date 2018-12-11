package com.sharonaapp.sharona.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;

import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.manager.SharedPreferencesManager;

import java.util.Locale;

public class LocaleHelper {

    public static final int LOCALE_PERSIAN = 1001;
    public static final int LOCALE_ENGLISH = 1002;

    public static boolean changeAppLocale(Activity activity, int localeCode)
    {
        String language = getLanguageFromLocale(localeCode);
        if (language.isEmpty())
        {
            return false;
        }

        Locale locale = new Locale(language);

        saveLocaleCodeIntoSharedPrefs(localeCode);
        changeActivityConfiguration(activity, locale);
        if (isCurrentLayoutDirectionDifferentFromGivenLocale(activity.getWindow().getDecorView()))
        {
            restartApp(activity);
        }
        changeLayoutDirectionBasedOnLocale(activity.getWindow().getDecorView());

        return true;

    }

    private static void restartApp(Activity activity)
    {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private static boolean isCurrentLayoutDirectionDifferentFromGivenLocale(View decorView)
    {
        boolean layoutDirectionIsDifferent = false;
        int layoutDirection = decorView.getLayoutDirection();

        if (getLocaleCodeFromSharedPrefs() == LOCALE_ENGLISH)
        {
            if (layoutDirection != View.LAYOUT_DIRECTION_LTR)
            {
                layoutDirectionIsDifferent = true;
            }
        }
        else if (getLocaleCodeFromSharedPrefs() == LOCALE_PERSIAN)
        {
            if (layoutDirection != View.LAYOUT_DIRECTION_RTL)
            {
                layoutDirectionIsDifferent = true;
            }
        }

        return layoutDirectionIsDifferent;
    }

    private static void saveLocaleCodeIntoSharedPrefs(int theLocale)
    {
        if (MyApplication.getSharedPreferencesManager() == null)
        {
            return;
        }

        MyApplication.getSharedPreferencesManager().persist(SharedPreferencesManager.LOCALE_PERSISTED, theLocale);
    }

    public static int getLocaleCodeFromSharedPrefs()
    {
        if (MyApplication.getSharedPreferencesManager() == null ||
                MyApplication.getSharedPreferencesManager().read(SharedPreferencesManager.LOCALE_PERSISTED) == null)
        {
            return LOCALE_ENGLISH;
        }

        return ((int) MyApplication.getSharedPreferencesManager().read(SharedPreferencesManager.LOCALE_PERSISTED));
    }

    public static void changeLayoutDirectionBasedOnLocale(View view)
    {
        if (getLocaleCodeFromSharedPrefs() == LOCALE_ENGLISH)
        {
            view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        else if (getLocaleCodeFromSharedPrefs() == LOCALE_PERSIAN){
            view.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    private static void changeActivityConfiguration(Activity activity, Locale locale)
    {
        Resources resources = activity.getApplicationContext().getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    private static String getLanguageFromLocale(int locale)
    {
        switch (locale)
        {
            case LOCALE_PERSIAN:
                return "fa";
            case LOCALE_ENGLISH:
                return "en";
            default:
                return "en";
        }
    }

    public static Locale getCurrentLocaleFromConfig(Context context)
    {
        return context.getResources().getConfiguration().locale;
    }

    public static void changeAppLocaleFromSharedPrefIfNeeded(Activity activity, boolean shouldRestart)
    {
        String language = getLanguageFromLocale(getLocaleCodeFromSharedPrefs());
        Locale locale = new Locale(language);

        Locale locale1 = activity.getApplicationContext().getResources().getConfiguration().locale;

        if (!locale.equals(locale1))
        {
            changeActivityConfiguration(activity, locale);
            if (shouldRestart)
            {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        }
    }
}
