package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Managers.SharedPreferencesManager;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.R;
import com.appestan.sharona.Utility.DialogHelper;
import com.appestan.sharona.Utility.ValidationHelper;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private Unbinder unbinder;
    @BindView(R.id.login_email_edit_text)
    EditText emailEditText;
    @BindView(R.id.login_password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.login_login_button)
    Button loginButton;
    @BindView(R.id.login_sign_up_instead_text_view)
    TextView signUpInsteadTextView;


    @BindView(R.id.login_login_layout)
    LinearLayout loginLayout;
    @BindView(R.id.login_logout_layout)
    LinearLayout logoutLayout;
    @BindView(R.id.login_logout_welcome_message_text_view)
    TextView welcomeMessageTextView;

    @OnClick(R.id.login_login_button)
    void login()
    {
//        if (!ValidationHelper.isEmailValid(emailEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "Email is not valid!");
//            return;
//        }
//
//        if (!ValidationHelper.isPasswordValid(passwordEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "Password is not valid!");
//            return;
//        }


        final Api.LoginRequest loginRequest = fillLoginRequestWithUserInput();
//        fillLoginRequestWithMockData(loginRequest);

        DialogHelper.showLoading(getContext());

        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);
        Call<Api.LoginResponse> loginResponseCall = endpointApi.login(loginRequest);

        loginResponseCall.enqueue(new Callback<Api.LoginResponse>() {
            @Override
            public void onResponse(Call<Api.LoginResponse> call, Response<Api.LoginResponse> response)
            {
                Log.d(TAG, "onResponse: " + response.isSuccessful() + " " + response.code() + " " + response.message());
                DialogHelper.hideLoading();
                if (response != null)
                {
                    if (response.code() == 401)
                    {
                        onUserHasFailedToLogin(response);
                    }
                    else if (response.code() == 200)
                    {
                        onUserHasLogedIn(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<Api.LoginResponse> call, Throwable t)
            {
                DialogHelper.hideLoading();
                Log.d(TAG, "onFailure: " + t.getMessage());
                onUserHasFailedToLogin(t);
            }
        });
    }

    private Api.LoginRequest fillLoginRequestWithUserInput()
    {
        Api.LoginRequest loginRequest = new Api.LoginRequest();
        loginRequest.setUsername(emailEditText.getText().toString());
        loginRequest.setPassword(passwordEditText.getText().toString());
        loginRequest.setClient_id(1);
        loginRequest.setClient_secret("zcWRDOGvr6jJKhdrc7I1R626mXSg0ORztaNZmpYJ");
        loginRequest.setGrant_type("password");
        return loginRequest;

    }

    private void fillLoginRequestWithMockData(Api.LoginRequest loginRequest)
    {
        loginRequest.setUsername("fmmajd@google.com");
        loginRequest.setPassword("12345678");
        loginRequest.setClient_id(1);
        loginRequest.setClient_secret("zcWRDOGvr6jJKhdrc7I1R626mXSg0ORztaNZmpYJ");
        loginRequest.setGrant_type("password");
    }

    @OnClick(R.id.login_logout_button)
    void logout()
    {
        SharedPreferencesManager.delete("token");
        loginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }

    private void onUserHasFailedToLogin(Response<Api.LoginResponse> response)
    {
        Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
    }

    private void onUserHasLogedIn(Api.LoginResponse loginResponse)
    {
        // save token for calls
        if (loginResponse != null || loginResponse.getAccessToken() != null)
        {
            SharedPreferencesManager.persist("token", loginResponse.getAccessToken());
            Log.d(TAG, "onUserHasLogedIn: SAVED TOKEN" + loginResponse.getAccessToken());
        }

        loginLayout.setVisibility(View.GONE);
        logoutLayout.setVisibility(View.VISIBLE);

        welcomeMessageTextView.setText("You've logged in with email:\n" + emailEditText.getText().toString());

    }

    private void onUserHasFailedToLogin(Throwable t)
    {

        loginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.login_sign_up_instead_text_view)
    void routeToSignUp()
    {
        ((MainActivity) getActivity()).routeToSignUpFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_login, container, false);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
