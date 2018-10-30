package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Model.Profile;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.R;
import com.appestan.sharona.Utility.ValidationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProfileFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.profile_username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.profile_email_edit_text)
    EditText emailEditText;
    @BindView(R.id.profile_city_edit_text)
    EditText cityEditText;
    private Profile persistedProfile;


    @OnClick(R.id.profile_save_changes_button)
    void saveChanges()
    {
        if (!ValidationHelper.isUserNameValid(usernameEditText))
        {
            // TODO: 10/5/18 display username is not valid
            Toast.makeText(getContext(), "Username is not valid", Toast.LENGTH_SHORT).show();
            return;

        }


        if (!ValidationHelper.isEmailValid(emailEditText))
        {
            // TODO: 10/5/18 display email is not valid
            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
            return;

        }


        Profile tempProfile = new Profile();
        if (editTextIsDifferentValueFromPersistedProfileValue(persistedProfile.getEmail(), emailEditText))
        {
            tempProfile.setEmail(emailEditText.getEditableText().toString());
        }

        if (editTextIsDifferentValueFromPersistedProfileValue(persistedProfile.getCity(), cityEditText))
        {
            tempProfile.setCity(cityEditText.getEditableText().toString());
        }

        // TODO: 10/5/18 should save the changes in the tempProfile which are not null, the null parts are intact
        Toast.makeText(getContext(), "Save Changes", Toast.LENGTH_SHORT).show();
    }

    void fetchProfile()
    {
        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);

//        Call<SignUpResponse> profile = endpointApi.profile();

/*        profile.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response)
            {

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t)
            {

            }
        });*/
    }

    private boolean editTextIsDifferentValueFromPersistedProfileValue(String persistedValue, EditText containingEditText)
    {
        return (containingEditText.getText() != null &&
                (containingEditText.getText().toString().equalsIgnoreCase(persistedValue)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));

        // TODO: 10/5/18 this profile has to be fetched from server
        persistedProfile = new Profile();
        persistedProfile.setUsername("Shervin");
        persistedProfile.setEmail("shervino@gmail.com");
        persistedProfile.setCity("Tehran");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
