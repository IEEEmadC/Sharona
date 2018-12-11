package com.sharonaapp.sharona.custom_ui;

import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.view.View;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.manager.LoginLogoutStateManager;

public class MenuDialog extends Dialog {
    public MenuDialog(@NonNull Activity activity)
    {
        super(activity);
        setContentView(R.layout.dialog_menu);

        if (LoginLogoutStateManager.getInstance().isUserLogedIn())
        {
            setLoginVisibilityState(View.GONE);
            setSignUpVisibilityState(View.GONE);
            setLogoutVisibilityState(View.VISIBLE);
        }
        else
        {
            setLoginVisibilityState(View.VISIBLE);
            setSignUpVisibilityState(View.VISIBLE);
            setLogoutVisibilityState(View.GONE);
        }

        findViewById(R.id.menu_login_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToLoginFragment();
            ((MainActivity) activity).hideToolbar();
//            ((MainActivity) activity).hideActiveTab();
            this.dismiss();
        });

        findViewById(R.id.menu_setting_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToSettingFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });

        findViewById(R.id.menu_sign_up_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToSignUpFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });

        findViewById(R.id.menu_inbox_outbox_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToInboxOutBoxFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });

        findViewById(R.id.menu_search_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToSearchFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });

        findViewById(R.id.menu_profile_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToProfileFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });

        findViewById(R.id.menu_logout_layout).setOnClickListener(view -> {
            ((MainActivity) activity).routeToLoginFragment();
            ((MainActivity) activity).hideToolbar();
            this.dismiss();
        });
    }

    private void setLoginVisibilityState(int visibilityState)
    {
        findViewById(R.id.menu_login_layout).setVisibility(visibilityState);

    }

    private void setSignUpVisibilityState(int visibilityState)
    {
        findViewById(R.id.menu_sign_up_layout).setVisibility(visibilityState);

    }

    private void setLogoutVisibilityState(int visibilityState)
    {
        findViewById(R.id.menu_logout_layout).setVisibility(visibilityState);
    }
}
