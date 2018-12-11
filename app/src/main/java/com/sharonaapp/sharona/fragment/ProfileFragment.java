package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.model.Profile;
import com.sharonaapp.sharona.model.response.ProfileResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.utility.DialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.profile_username_text_input_edit_text)
    TextInputEditText usernameTextInputEditText;
    @BindView(R.id.profile_first_name_text_input_edit_text)
    TextInputEditText firstNameTextInputEditText;
    @BindView(R.id.profile_last_name_text_input_edit_text)
    TextInputEditText lastNameTextInputEditText;
    @BindView(R.id.profile_email_text_input_edit_text)
    TextInputEditText emailTextInputEditText;
    @BindView(R.id.profile_city_text_input_edit_text)
    TextInputEditText cityTextInputEditText;

    private Profile persistedProfile;


    @OnClick(R.id.profile_save_changes_button)
    void saveChanges()
    {
//        if (!ValidationHelper.isUserNameValid(usernameEditText))
        {
            // TODO: 10/5/18 display username is not valid
            Toast.makeText(getContext(), "Username is not valid", Toast.LENGTH_SHORT).show();
//            return;

        }


//        if (!ValidationHelper.isEmailValid(emailEditText))
        {
            // TODO: 10/5/18 display email is not valid
            Toast.makeText(getContext(), "Email is not valid", Toast.LENGTH_SHORT).show();
//            return;

        }


        Profile tempProfile = new Profile();
//        if (editTextIsDifferentValueFromPersistedProfileValue(persistedProfile.getEmail(), emailEditText))
        {
//            tempProfile.setEmail(emailEditText.getEditableText().toString());
        }

//        if (editTextIsDifferentValueFromPersistedProfileValue(persistedProfile.getCity(), cityEditText))
        {
//            tempProfile.setCity(cityEditText.getEditableText().toString());
        }

        // TODO: 10/5/18 should save the changes in the tempProfile which are not null, the null parts are intact
        Toast.makeText(getContext(), "Save Changes", Toast.LENGTH_SHORT).show();
    }

    void fetchProfile()
    {

        Call<ProfileResponse> profile = NetworkManager.getInstance().getEndpointApi(Api.class).profile();
        profile.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response)
            {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null)
                {
                    setProfileResponseDataToView(response.body().getData());
                }
                else
                {
                    failedToFetchProfile();
                }

            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t)
            {
                failedToFetchProfile();
            }
        });
    }

    private void setProfileResponseDataToView(ProfileResponse.Profile profile)
    {

        if (profile.getUsername() != null && profile.getUsername().trim().length() > 1)
        {
            usernameTextInputEditText.setText(profile.getUsername());
        }
        if (profile.getFirstName() != null && profile.getFirstName().trim().length() > 1)
        {
            firstNameTextInputEditText.setText(profile.getFirstName().trim());
        }
        if (profile.getLastName() != null && profile.getLastName().trim().length() > 1)
        {
            lastNameTextInputEditText.setText(profile.getLastName().trim());
        }
        if (profile.getEmail() != null && profile.getEmail().trim().length() > 1)
        {
            emailTextInputEditText.setText(profile.getEmail().trim());
        }
        if (profile.getCity() != null && profile.getCity().trim().length() > 1)
        {
            cityTextInputEditText.setText(profile.getCity().trim());
        }

    }

    private void failedToFetchProfile()
    {
        DialogHelper.showDialogWithMessage(getContext(), "Server not reachable!");
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
        fetchProfile();
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
