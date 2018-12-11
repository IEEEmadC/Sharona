package com.sharonaapp.sharona;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.sharonaapp.sharona.interfaces.BackButtonClickListener;

public class BackButtonClickListenerImpl implements BackButtonClickListener {

    private final FragmentActivity fragmentActivity;

    public BackButtonClickListenerImpl(FragmentActivity fragmentActivity)
    {
        this.fragmentActivity = fragmentActivity;
    }

    @Override
    public void onBackButtonClicked(Fragment fragment)
    {
        if (fragment.getChildFragmentManager().getBackStackEntryCount() > 1) {
            fragment.getChildFragmentManager().popBackStack();
        }
    }
}
