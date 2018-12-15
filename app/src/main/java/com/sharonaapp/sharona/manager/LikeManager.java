package com.sharonaapp.sharona.manager;

import com.sharonaapp.sharona.MyApplication;

import java.util.HashSet;
import java.util.Set;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.LIKED_SYSTEM;

public class LikeManager {

    private static final LikeManager ourInstance = new LikeManager();
    private static Set<String> likedSet;

    public static LikeManager getInstance()
    {
        return ourInstance;
    }

    private LikeManager()
    {
        if (MyApplication.getSharedPreferencesManager().read(LIKED_SYSTEM) != null &&
                MyApplication.getSharedPreferencesManager().read(LIKED_SYSTEM) instanceof Set)
        {
            likedSet = (Set<String>) MyApplication.getSharedPreferencesManager().read(LIKED_SYSTEM);
        }
        else
        {
            likedSet = new HashSet<>();
        }
    }


    public boolean isAlreadyLiked(int id)
    {
        return likedSet.contains(String.valueOf(id));
    }

    public boolean isAlreadyLiked(String id)
    {
        return likedSet.contains(id);
    }

    public boolean toggleLikeClothes(int id)
    {
        if (isAlreadyLiked(id))
        {
            likedSet.remove(String.valueOf(id));
            MyApplication.getSharedPreferencesManager().persist(LIKED_SYSTEM, likedSet);
            return false;
        }
        else
        {
            likedSet.add(String.valueOf(id));
            MyApplication.getSharedPreferencesManager().persist(LIKED_SYSTEM, likedSet);

            return true;
        }
    }
}
