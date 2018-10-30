package com.appestan.sharona;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.appestan.sharona.Interface.BackPressListener;

public class BackPressable implements BackPressListener {

    private Fragment parentFragment;

    public BackPressable(Fragment fragment)
    {
        this.parentFragment = fragment;
    }


    @Override
    public boolean onBackPressed()
    {
        int backStackEntryCount;

        if (parentFragment == null)
        {
            return false;
        }

        if (parentFragment.getParentFragment() != null)
        {
            backStackEntryCount = parentFragment.getChildFragmentManager().getBackStackEntryCount();

        }
        else
        {
            backStackEntryCount = parentFragment.getChildFragmentManager().getBackStackEntryCount();
        }
        if (backStackEntryCount == 0)
        {
            return false;
        }
        else
        {
            FragmentManager childFragmentManager = parentFragment.getChildFragmentManager();
            BackPressListener fragmentAtIndex0 = (BackPressListener) childFragmentManager.getFragments().get(0);

            if (!fragmentAtIndex0.onBackPressed())
            {
                childFragmentManager.popBackStackImmediate();
            }

            return true;
        }

    }
}
