package com.sharonaapp.sharona.fragment;

import android.support.v4.app.Fragment;

import com.sharonaapp.sharona.BackPressable;
import com.sharonaapp.sharona.interfaces.BackPressListener;

public class BaseFragment extends Fragment implements BackPressListener {


    @Override
    public boolean onBackPressed()
    {
        return new BackPressable(this).onBackPressed();
    }
}
