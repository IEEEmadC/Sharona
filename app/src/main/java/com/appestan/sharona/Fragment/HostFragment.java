package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appestan.sharona.Interface.BackPressListener;
import com.appestan.sharona.R;

public class HostFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_base_fragment, container, false);
        return view;
    }

    public boolean hasContent() {
        if (getFragmentManager() == null) {
            return false;
        } else {
            return getChildFragmentManager().getBackStackEntryCount() > 0;
        }
    }

    public boolean back() {

        if (getChildFragmentManager().getBackStackEntryCount() > 1) {

            getChildFragmentManager().popBackStackImmediate();
            Fragment fragment = getChildFragmentManager().getFragments().get(getChildFragmentManager().getBackStackEntryCount() - 1);
//            fr.updateToolbar();
            return true;
        } else {
            return false;
        }
    }
}
