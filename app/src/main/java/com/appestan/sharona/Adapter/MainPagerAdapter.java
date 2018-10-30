package com.appestan.sharona.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.appestan.sharona.Fragment.AddClothesFragment;
import com.appestan.sharona.Fragment.ClothesInnerFragment;
import com.appestan.sharona.Fragment.ExploreFragment;
import com.appestan.sharona.Fragment.HostFragment;
import com.appestan.sharona.Fragment.LoginFragment;
import com.appestan.sharona.Fragment.MapFragment;
import com.appestan.sharona.MainActivity;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private HostFragment[] hostFragments = new HostFragment[4];

    public MainPagerAdapter(FragmentManager fm)
    {
        super(fm);
        hostFragments[0] = new HostFragment();
        hostFragments[1] = new HostFragment();
        hostFragments[2] = new HostFragment();
        hostFragments[3] = new HostFragment();
    }

    public Fragment getDefaultFragmentForTab(int tabIndex)
    {
        Fragment fragment = null;

        switch (tabIndex)
        {
            case MainActivity.TAB_ADD_CLOTHES_INDEX:
                fragment = new AddClothesFragment();
                break;

            case MainActivity.TAB_EXPLORE_INDEX:
                fragment = new ExploreFragment();
                break;

            case MainActivity.TAB_CLOTHES_INNER_INDEX:
//                fragment = new ClothesInnerFragment();
                fragment = new MapFragment();
                break;

                case MainActivity.TAB_LOGIN:
//                fragment = new ClothesInnerFragment();
                fragment = new LoginFragment();
                break;

        }

        return fragment;
    }

    @Override
    public HostFragment getItem(int i)
    {
        return hostFragments[i];
    }

    @Override
    public int getCount()
    {
        return hostFragments.length;
    }
}
