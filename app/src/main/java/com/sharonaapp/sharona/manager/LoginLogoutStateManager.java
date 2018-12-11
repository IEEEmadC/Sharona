package com.sharonaapp.sharona.manager;

import com.sharonaapp.sharona.MyApplication;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.USER_IS_CURRENTLY_LOGED_IN;

public class LoginLogoutStateManager {

    static LoginLogoutStateManager instance;
    public static final Boolean LOG_IN = Boolean.TRUE;
    public static final Boolean LOG_FALSE = Boolean.FALSE;

    private LoginLogoutStateManager()
    {
    }

    public static LoginLogoutStateManager getInstance()
    {
        if (instance == null)
        {
            instance = new LoginLogoutStateManager();
        }
        return instance;
    }

    public boolean isUserLogedIn()
    {
        return MyApplication.getSharedPreferencesManager().read(USER_IS_CURRENTLY_LOGED_IN) instanceof Boolean &&
                ((Boolean) MyApplication.getSharedPreferencesManager().read(USER_IS_CURRENTLY_LOGED_IN));
    }

    public void setUserLoginLogoutState(Boolean state)
    {
        MyApplication.getSharedPreferencesManager().persist(USER_IS_CURRENTLY_LOGED_IN, state);
    }
}
