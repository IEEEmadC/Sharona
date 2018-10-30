package com.appestan.sharona.Fragment;

import android.support.v4.app.Fragment;

import com.appestan.sharona.BackPressable;
import com.appestan.sharona.Interface.BackPressListener;

public class BaseFragment extends Fragment implements BackPressListener {


    @Override
    public boolean onBackPressed()
    {
        return new BackPressable(this).onBackPressed();
    }
}
