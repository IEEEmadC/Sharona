package com.appestan.sharona.Managers;

import android.content.Context;

import com.appestan.sharona.MainActivity;
import com.orhanobut.hawk.Hawk;

public class SharedPreferencesManager {

    public static final String LOCALE_PERSISTED = "l_p";

    public SharedPreferencesManager(Context context)
    {
        Hawk.init(context).build();

    }


    public static void persist(String key, Object T)
    {
        Hawk.put(key, T);
    }

    public static Object read(String key)
    {
        if (Hawk.contains(key))
        {
            return Hawk.get(key);
        }
        else
        {
            return null;
        }
    }


    public static void delete(String token)
    {
        Hawk.delete(token);
    }
}
