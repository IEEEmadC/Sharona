package com.sharonaapp.sharona.manager;

import android.content.Context;

import com.orhanobut.hawk.Hawk;

public class SharedPreferencesManager {

    public static final String LOCALE_PERSISTED = "l_p";
    public static final String USERNAME_PERSISTED = "username_persisted";
    public static final String EMAIL_PERSISTED = "email_persisted";
    public static final String CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED = "CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED";
    public static final String USER_HAS_HALF_WAY_UPLOADED_CLOTHES = "USER_HAS_HALF_WAY_UPLOADED_CLOTHES";
    public static final String USER_IS_CURRENTLY_LOGED_IN = "user_is_currently_loged_in";
    public static final String CONFIG = "CONFIG_DAO";
    public static final String CONFIG_DATE = "CONFIG_DAO_DATE";
    public static final String PUSH_TOKEN_HAS_CHANGED = "PUSH_TOKEN_HAS_CHANGED";
    public static final String VIRGINITY_OF_TOUR = "VIRGINITY_OF_TOUR";
    public static final String LIKED_ = "VIRGINITY_OF_TOUR";

    public static final String LIKED_SYSTEM = "LIKED_SYSTEM";

    public SharedPreferencesManager(Context context)
    {
        Hawk.init(context).build();

    }


    public void persist(String key, Object T)
    {
        Hawk.put(key, T);
    }

    public Object read(String key)
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


    public void delete(String token)
    {
        Hawk.delete(token);
    }
}
