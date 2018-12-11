package com.sharonaapp.sharona.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.fragment.AddClothesFragment;
import com.sharonaapp.sharona.fragment.ExploreFragment;
import com.sharonaapp.sharona.fragment.HostFragment;
import com.sharonaapp.sharona.fragment.MyClosetFragment;

public class MainPagerAdapter {

    private final FragmentManager fragmentManager;
    private final FrameLayout frameLayout;
    public Fragment getActiveFragment;
    private HostFragment[] hostFragments = new HostFragment[4];

    AddClothesFragment addClothesFragment;
    ExploreFragment exploreFragment = new ExploreFragment();
    MyClosetFragment myClosetFragment;

    public MainPagerAdapter(FragmentManager supportFragmentManager, FrameLayout frameLayout)
    {
        this.fragmentManager = supportFragmentManager;
        this.frameLayout = frameLayout;
        hostFragments[0] = new HostFragment();
        hostFragments[1] = new HostFragment();
        hostFragments[2] = new HostFragment();
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

            case MainActivity.TAB_MY_CLOSET:
                fragment = new MyClosetFragment();
                break;

        }

        return fragment;
    }

    public void onTabItemClicked(int tabIndex)
    {
    }

    public void addFragmentOnTop(Fragment fragment)
    {
        fragmentManager
                .beginTransaction()
                .replace(frameLayout.getId(), fragment)
                .commitAllowingStateLoss();
    }

    public void switchTab(int tabIndex)
    {
        switch (tabIndex)
        {
            case 0:
                if (addClothesFragment == null)
                {
                    addClothesFragment = new AddClothesFragment();
                }
                addFragmentOnTop(addClothesFragment);

                break;
            case 1:
                if (exploreFragment == null)
                {
//                    exploreFragment = new ExploreFragment();
                }
                addFragmentOnTop(exploreFragment);
                break;
            case 2:
                if (myClosetFragment == null)
                {
                    myClosetFragment = new MyClosetFragment();
                }
                addFragmentOnTop(myClosetFragment);
                break;

        }
    }

    public void openFragment(Fragment fragment) throws IllegalStateException
    {
        FragmentManager childFragmentManager = getActiveFragment.getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.add(frameLayout.getId(), fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

//    @Override
//    public HostFragment getItem(int i)
//    {
//        return hostFragments[i];
//    }
//
//    @Override
//    public int getCount()
//    {
//        return hostFragments.length;
//    }
}
