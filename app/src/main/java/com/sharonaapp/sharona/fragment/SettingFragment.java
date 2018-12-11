package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.helper.LocaleHelper;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SettingFragment extends Fragment {
    Unbinder unbinder;

    @BindView(R.id.setting_language_index_zero_radio_button)
    RadioButton englishLanguageRadioButton;
    @BindView(R.id.setting_language_index_one_radio_button)
    RadioButton persianLanguageRadioButton;
    @BindView(R.id.setting_save_changes_button)
    Button saveChangesButton;

    @OnCheckedChanged({R.id.setting_language_index_zero_radio_button, R.id.setting_language_index_one_radio_button})
    void onSelectLanguageRadioButtonsCliked(RadioButton selectedRadioButton, boolean state)
    {
        switch (selectedRadioButton.getId())
        {
            case (R.id.setting_language_index_zero_radio_button):
            {
                if (state)
                {
                    settingStateHolder.setChosenLanguageCode(LocaleHelper.LOCALE_ENGLISH);
                }
            }
            break;
            case (R.id.setting_language_index_one_radio_button):
            {
                if (state)
                {
                    settingStateHolder.setChosenLanguageCode(LocaleHelper.LOCALE_PERSIAN);
                }
            }
            break;

        }
    }

    @OnClick(R.id.setting_save_changes_button)
    void onSaveButtonClicked()
    {
        if (settingStateHolder != null)
        {
            if (settingStateHolder.getCurrentLanguageCode() != settingStateHolder.getChosenLanguageCode())
            {
                LocaleHelper.changeAppLocale(getActivity(), settingStateHolder.chosenLanguageCode);
            }
        }
    }

    SettingStateHolder settingStateHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));


    }

    @Override
    public void onResume()
    {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        settingStateHolder = new SettingStateHolder();
        settingStateHolder.setChosenLanguageCode(LocaleHelper.getLocaleCodeFromSharedPrefs());

    }

    private class SettingStateHolder {
        int currentLanguageCode;
        int chosenLanguageCode;

        public int getCurrentLanguageCode()
        {
            return currentLanguageCode;
        }

        public void setCurrentLanguageCode(int currentLanguageCode)
        {
            this.currentLanguageCode = currentLanguageCode;
        }

        public int getChosenLanguageCode()
        {
            return chosenLanguageCode;
        }

        public void setChosenLanguageCode(int chosenLanguageCode)
        {
            this.chosenLanguageCode = chosenLanguageCode;
        }
    }
}
