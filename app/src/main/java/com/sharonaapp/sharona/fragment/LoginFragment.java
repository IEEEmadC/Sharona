package com.sharonaapp.sharona.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.manager.LoginLogoutStateManager;
import com.sharonaapp.sharona.model.request.GoogleAuthRequest;
import com.sharonaapp.sharona.model.request.OauthRequest;
import com.sharonaapp.sharona.model.response.GoogleAuthResponse;
import com.sharonaapp.sharona.model.response.OauthResponse;
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

public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";

    private Unbinder unbinder;
    @BindView(R.id.login_parent_layout)
    LinearLayout parentLayout;
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

    FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions googleSignInOptions;
    private int RC_SIGN_IN = 12301;
    private GoogleSignInClient mGoogleSignInClient;

    @OnClick(R.id.login_login_with_google_button)
    void loginWithGoogle()
    {
        doLoginWithGoogle();
    }

    @OnClick(R.id.login_login_button)
    void loginWithSharona()
    {

        OauthRequest oauthRequest = new OauthRequest();
        oauthRequest.setClientId(3);
        oauthRequest.setClientSecret("sNnoZW4JkaxJlT333HyLKZMV0rPkqZxtjXPInzRK");
        oauthRequest.setGrantType("password");
        oauthRequest.setUsername(emailEditText.getText().toString());
        oauthRequest.setPassword(passwordEditText.getText().toString());
        ((MainActivity) getActivity()).showLoading();

        Call<OauthResponse> oauthCall = NetworkManager.getInstance().getEndpointApi(Api.class).oauth(oauthRequest);
        oauthCall.enqueue(new Callback<OauthResponse>() {
            @Override
            public void onResponse(Call<OauthResponse> call, Response<OauthResponse> response)
            {
                ((MainActivity) getActivity()).hideLoading();

                if (response.isSuccessful() && response.body() != null && response.body().getAccessToken() != null)
                {
                    onUserHasLogedIn(response.body().getAccessToken());
                }
                else if (response.code() == 400)
                {
                    loginFailed("Check inputs");
                }
                else
                {
                    loginFailed("Login failed");
                }
            }

            @Override
            public void onFailure(Call<OauthResponse> call, Throwable t)
            {
                loginFailed(t);
                ((MainActivity) getActivity()).hideLoading();

            }
        });


    }

    private void loginFailed(String message)
    {
        DialogHelper.showDialogWithMessage(getContext(), message);
    }

    private void loginFailed(@Nullable Throwable throwable)
    {
        if (throwable != null && throwable.getMessage() != null && throwable.getMessage().length() > 2)
        {
            loginFailed(throwable.getMessage());
        }
        else
        {
            loginFailed("Server Unreachable");
        }
    }

    @OnClick(R.id.login_logout_button)
    void logout()
    {
        MyApplication.getSharedPreferencesManager().delete("token");
        LoginLogoutStateManager.getInstance().setUserLoginLogoutState(Boolean.FALSE);

        loginLayout.setVisibility(View.VISIBLE);
        logoutLayout.setVisibility(View.GONE);
    }

    private void onUserHasFailedToLogin(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void onUserHasLogedIn(String accessTokenString)
    {
        Log.d(TAG, "onUserHasLogedIn: SAVED TOKEN" + accessTokenString);
        MyApplication.getSharedPreferencesManager().persist("token", accessTokenString);
        LoginLogoutStateManager.getInstance().setUserLoginLogoutState(Boolean.TRUE);

        loginLayout.setVisibility(View.GONE);
        logoutLayout.setVisibility(View.VISIBLE);

        setWelcomeMessage();

        MyApplication.handleTokenForNotification(false);


    }

    private void setWelcomeMessage()
    {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

//        doLoginWithGoogle();


    }

    private void doLoginWithGoogle()
    {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id_))
                .requestEmail()
                .build();

//        googleApiClient = new GoogleApiClient.Builder(getActivity())
//                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
//                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), googleSignInOptions);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (LoginLogoutStateManager.getInstance().isUserLogedIn())
        {
            loginLayout.setVisibility(View.GONE);
            logoutLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            loginLayout.setVisibility(View.VISIBLE);
            logoutLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess())
            {
                GoogleSignInAccount account = result.getSignInAccount();
                authWithGoogle(account);
            }
        }
    }

    private void authWithGoogle(GoogleSignInAccount account)
    {
        ((MainActivity) getActivity()).showLoading();

        GoogleAuthRequest googleAuthRequest = new GoogleAuthRequest();
        googleAuthRequest.setGoogleToken(account.getIdToken());
        googleAuthRequest.setUserEmail(account.getEmail());

        if (emailEditText != null){
            emailEditText.setText(account.getEmail());
        }

        NetworkManager.getInstance().getEndpointApi(Api.class).googleOAuth(googleAuthRequest).enqueue(new Callback<GoogleAuthResponse>() {
            @Override
            public void onResponse(Call<GoogleAuthResponse> call, Response<GoogleAuthResponse> response)
            {
                ((MainActivity) getActivity()).hideLoading();
                if (response.isSuccessful())
                {
                    if (response.body() != null && response.body().getData() != null)
                    {
                        onUserHasLogedIn(response.body().getData().getAccessToken());
                    }
                }
            }

            @Override
            public void onFailure(Call<GoogleAuthResponse> call, Throwable t)
            {

                ((MainActivity) getActivity()).hideLoading();


            }
        });

//
//        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
//            if (task.isSuccessful())
//            {
//                startActivity(new Intent(getContext(), MainActivity.class));
//                getActivity().finish();
//            }
//            else
//            {
//                Toast.makeText(getContext(), "Auth Error", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
