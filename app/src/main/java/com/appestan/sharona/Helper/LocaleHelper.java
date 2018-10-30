package com.appestan.sharona.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;

import com.appestan.sharona.Managers.SharedPreferencesManager;
import com.appestan.sharona.MyApplication;

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

        return true;

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
        else if (getLocaleCodeFromSharedPrefs() == LOCALE_PERSIAN)
        {
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
}
