package com.sharonaapp.sharona.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.manager.LoginLogoutStateManager;
import com.sharonaapp.sharona.model.request.OauthRequest;
import com.sharonaapp.sharona.model.response.OauthResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.network.SignUpRequest;
import com.sharonaapp.sharona.network.SignUpResponse;
import com.sharonaapp.sharona.utility.CommonHelper;
import com.sharonaapp.sharona.utility.DialogHelper;
import com.sharonaapp.sharona.utility.ValidationHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.EMAIL_PERSISTED;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.USERNAME_PERSISTED;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";

    private Unbinder unbinder;

    @BindView(R.id.sign_up_sign_up_layout)
    LinearLayout signUpLayout;

    //username
    @BindView(R.id.sign_up_username_text_input_layout)
    TextInputLayout usernameTextInputLayout;
    @BindView(R.id.sign_up_username_text_input_edit_text)
    TextInputEditText usernameTextInputEditText;
    //email
    @BindView(R.id.sign_up_email_text_input_layout)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.sign_up_email_edit_text)
    TextInputEditText emailTextInputEditText;
    // password
    @BindView(R.id.sign_up_password_text_input_layout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.sign_up_password_edit_text)
    TextInputEditText passwordTextInputEditText;
    // city
    @BindView(R.id.sign_up_city_text_input_layout)
    TextInputLayout cityTextInputLayout;
    @BindView(R.id.sign_up_city_edit_text)
    TextInputEditText cityTextInputEditText;
    // address
    @BindView(R.id.sign_up_address_text_input_layout)
    TextInputLayout addressTextInputLayout;
    @BindView(R.id.sign_up_address_edit_text)
    TextInputEditText addressTextInputEditText;
    @BindView(R.id.sign_up_phone_number_edit_text)
    TextInputEditText phoneNumberTextInputEditText;


    @BindView(R.id.sign_up_gender_value_text_view)
    TextView genderValueTextView;
    @BindView(R.id.sign_up_sign_up_button)
    Button signUpButton;
    @BindView(R.id.sign_up_login_text_view)
    TextView loginTextView;

    @BindView(R.id.sign_up_logout_layout)
    LinearLayout logoutLayout;


    @OnClick({R.id.sign_up_gender_title_text_view, R.id.sign_up_gender_value_text_view})
    void genderTextViewClicked()
    {
        CommonHelper.hideKeyboard(getActivity(), getView());

        ArrayList<String> genderArrayList = MyApplication.getConfig().getUserGenderArrayList();
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genderArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Gender")
                .setAdapter(genderSpinnerAdapter, (dialog, which) -> {

                    genderValueTextView.setText(genderArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    @OnClick(R.id.sign_up_sign_up_button)
    void signUp()
    {
        if (!ValidationHelper.isUserNameValid(usernameTextInputEditText))
        {
            usernameTextInputLayout.setError("Username must be longer than 4 characters");
            return;
        }

        if (!ValidationHelper.isEmailValid(emailTextInputEditText))
        {
            emailTextInputLayout.setError("Email is not valid!");
            return;
        }

        if (!ValidationHelper.isPasswordValid(passwordTextInputEditText))
        {
            passwordTextInputLayout.setError("Password is too short or empty");
            return;
        }

        if (!ValidationHelper.isCityValid(cityTextInputEditText))
        {
            cityTextInputLayout.setError("City is not valid");
            return;
        }

        if (!ValidationHelper.isAddressValid(addressTextInputEditText))
        {
            addressTextInputLayout.setError("Address is not valid");
            return;
        }

        if (!ValidationHelper.isPhoneNumberValid(phoneNumberTextInputEditText))
        {
            phoneNumberTextInputEditText.setError("Phone number is not valid");
            return;
        }

        if (!ValidationHelper.isUserGenderValid(genderValueTextView))
        {
            DialogHelper.warnDialog(getActivity(), "Invalid input", "Select gender!");
            return;
        }


        doSignUp();

    }


    @OnClick(R.id.sign_up_logout_button)
    void logout()
    {
        MyApplication.getSharedPreferencesManager().delete("token");
        LoginLogoutStateManager.getInstance().setUserLoginLogoutState(Boolean.FALSE);

        signUpLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }

    private void doSignUp()
    {

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(usernameTextInputEditText.getText().toString());
        signUpRequest.setEmail(emailTextInputEditText.getText().toString());
        signUpRequest.setPassword(passwordTextInputEditText.getText().toString());
        signUpRequest.setCity(cityTextInputEditText.getText().toString());
        signUpRequest.setAddress(addressTextInputEditText.getText().toString());
        signUpRequest.setGender(genderValueTextView.getText().toString().toUpperCase());
        signUpRequest.setPhoneNumber(phoneNumberTextInputEditText.getText().toString());

        ((MainActivity) getActivity()).showLoading();

        Call<SignUpResponse> signUpCall = NetworkManager.getInstance().getEndpointApi(Api.class).signUp(signUpRequest);
        signUpCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response)
            {
                ((MainActivity) getActivity()).hideLoading();

                if (response.isSuccessful())
                {
                    onUserHasSignedUp(signUpRequest);
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t)
            {
                ((MainActivity) getActivity()).hideLoading();
                Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();


            }
        });


    }

    private SignUpRequest fillSignUpRequestWithUserInput()
    {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(usernameTextInputEditText.getText().toString());
        signUpRequest.setEmail(emailTextInputEditText.getText().toString());
//        signUpRequest.setFirst_name("Ali");
//        signUpRequest.setLast_name("Alizade");
//        signUpRequest.setNational_id(nationalIdEditText.getText().toString());
        signUpRequest.setPassword(passwordTextInputEditText.getText().toString());
        return signUpRequest;
    }

    private void onUserHasSignedUp(SignUpRequest signUpRequest)
    {
        // try to login the user and then go to explore fragment
        persist(signUpRequest);
        loginRetrofit(signUpRequest.getEmail(), signUpRequest.getPassword());
    }

    private void persist(SignUpRequest signUpRequest)
    {
        MyApplication.getSharedPreferencesManager().persist(USERNAME_PERSISTED, signUpRequest.getUsername());
        MyApplication.getSharedPreferencesManager().persist(EMAIL_PERSISTED, signUpRequest.getEmail());
    }

    private void loginRetrofit(String username, String password)
    {
        {

            OauthRequest oauthRequest = new OauthRequest();
            oauthRequest.setClientId(3);
            oauthRequest.setClientSecret("sNnoZW4JkaxJlT333HyLKZMV0rPkqZxtjXPInzRK");
            oauthRequest.setGrantType("password");
            oauthRequest.setUsername(username);
            oauthRequest.setPassword(password);
            ((MainActivity) getActivity()).showLoading();
            Call<OauthResponse> oauthCall = NetworkManager.getInstance().getEndpointApi(Api.class).oauth(oauthRequest);
            oauthCall.enqueue(new Callback<OauthResponse>() {
                @Override
                public void onResponse(Call<OauthResponse> call, Response<OauthResponse> response)
                {
                    ((MainActivity) getActivity()).hideLoading();

                    if (response.isSuccessful() && response.body() != null && response.body().getAccessToken() != null)
                    {
                        MyApplication.getSharedPreferencesManager().persist("token", response.body().getAccessToken());
                        LoginLogoutStateManager.getInstance().setUserLoginLogoutState(Boolean.TRUE);

                        signUpLayout.setVisibility(View.GONE);
                        logoutLayout.setVisibility(View.VISIBLE);

                        MyApplication.handleTokenForNotification(false);

                        if (getActivity() != null)
                        {
                            ((MainActivity) getActivity()).routeExploreFragment();
                        }
                    }
                    else if (response.code() == 400)
                    {
                        onUserHasFailedToLogin();

                    }
                    else
                    {
                        onUserHasFailedToLogin();
                    }
                }

                @Override
                public void onFailure(Call<OauthResponse> call, Throwable t)
                {
                    ((MainActivity) getActivity()).hideLoading();

                    onUserHasFailedToLogin();
                }
            });


        }
    }


    private void onUserHasFailedToLogin(String message)
    {
        if (message != null && message.length() > 4)
        {
            DialogHelper.showDialogWithMessage(getContext(), message);
        }
        else
        {
            DialogHelper.showDialogWithMessage(getContext(), "Sign up Successful! \nPlease Login to continue");
        }
    }

    private void onUserHasFailedToLogin()
    {
        DialogHelper.showDialogWithMessage(getContext(), "Sign up Successful! \nPlease Login to continue");
        ((MainActivity) getActivity()).routeToLoginFragment();

    }


    @OnClick(R.id.sign_up_login_text_view)
    void routeToLogin()
    {
        ((MainActivity) getActivity()).routeToLoginFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initTextWatchers();
        initGenderSpinner();


    }

    private void initGenderSpinner()
    {

    }

    private void initTextWatchers()
    {
        TextWatcher usernameTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                usernameTextInputLayout.setError(null);
            }
        };


        TextWatcher emailTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                emailTextInputLayout.setError(null);
            }
        };

        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                passwordTextInputLayout.setError(null);
            }
        };

        TextWatcher cityTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                cityTextInputLayout.setError(null);
            }
        };

        TextWatcher addressTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                addressTextInputLayout.setError(null);
            }
        };

        usernameTextInputEditText.addTextChangedListener(usernameTextWatcher);
        emailTextInputEditText.addTextChangedListener(emailTextWatcher);
        passwordTextInputEditText.addTextChangedListener(passwordTextWatcher);
        cityTextInputEditText.addTextChangedListener(cityTextWatcher);
        addressTextInputEditText.addTextChangedListener(addressTextWatcher);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (LoginLogoutStateManager.getInstance().isUserLogedIn())
        {
            signUpLayout.setVisibility(View.GONE);
            logoutLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            signUpLayout.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
