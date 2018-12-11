package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.model.LoginResponse;
import com.sharonaapp.sharona.network.SignUpRequest;
import com.sharonaapp.sharona.network.Url;
import com.sharonaapp.sharona.utility.DialogHelper;
import com.sharonaapp.sharona.utility.ValidationHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.EMAIL_PERSISTED;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.USERNAME_PERSISTED;
import static com.sharonaapp.sharona.network.NetworkManager.fetchErrorMessage;
import static com.sharonaapp.sharona.network.Url.SIGNUP;

public class SignUpFragment extends Fragment {

    private static final String TAG = "SignUpFragment";

    private Unbinder unbinder;

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
    //    @BindView(R.id.sign_up_national_id_edit_text)
//    TextInputEditText nationalIdEditText;
    //password
    @BindView(R.id.sign_up_password_text_input_layout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.sign_up_password_edit_text)
    TextInputEditText passwordTextInputEditText;
    @BindView(R.id.sign_up_sign_up_button)
    Button signUpButton;
    @BindView(R.id.sign_up_login_text_view)
    TextView loginTextView;

    @OnClick(R.id.sign_up_sign_up_button)
    void signUp()
    {
        if (!ValidationHelper.isUserNameValid(usernameTextInputEditText))
        {
            usernameTextInputLayout.setError("Username must be longer than 7 characters");
            return;
        }

        if (!ValidationHelper.isEmailValid(emailTextInputEditText))
        {
            emailTextInputLayout.setError("Email is not valid!");
            return;
        }

//        if (!ValidationHelper.isNationalIdValid(nationalIdEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "National Id is not valid!");
//            return;
//        }

        if (!ValidationHelper.isPasswordValid(passwordTextInputEditText))
        {
            passwordTextInputLayout.setError("Password is too short");
            return;
        }


        SignUpRequest signUpRequest = fillSignUpRequestWithUserInput();
//        SignUpRequest signUpRequest = fillSignUpRequestWithMockData();

        DialogHelper.showLoading(getContext());

        JSONObject paramsJson = new JSONObject();
        try
        {
            paramsJson.put("username", signUpRequest.getUsername());
//            paramsJson.put("first_name", signUpRequest.getFirst_name());
//            paramsJson.put("last_name", signUpRequest.getLast_name());
            paramsJson.put("email", signUpRequest.getEmail());
            paramsJson.put("password", signUpRequest.getPassword());
//            paramsJson.put("national_id", signUpRequest.getNational_id());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest signUpJsonRequest = new JsonObjectRequest(Request.Method.POST, SIGNUP, paramsJson,
                response -> {

                    DialogHelper.hideLoading();

                    try
                    {
                        if (response != null && response.has("success"))
                        {
                            if (response.getBoolean("success"))
                            {
                                onUserHasSignedUp(signUpRequest);
                            }
                            else
                            {
                                DialogHelper.showDialogWithMessage(getContext(), response.getString("userMessage"));
                            }
                        }
                        else
                        {
                            DialogHelper.showDialogWithMessage(getContext(), "Sign up failed");

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }, error -> {

            DialogHelper.hideLoading();

            if (error != null)
            {
                Log.d(TAG, "onErrorResponse: " + error);
                String json = new String(error.networkResponse.data);
                json = fetchErrorMessage(json, "user_message");
                DialogHelper.showDialogWithMessage(getContext(), json);

            }

        });

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(signUpJsonRequest);


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

    private SignUpRequest fillSignUpRequestWithMockData()
    {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("ali6@gmail.com");
        signUpRequest.setUsername("Ali6");
        signUpRequest.setFirst_name("Ali");
        signUpRequest.setLast_name("Alizade");
//        signUpRequest.setNational_id("5678901234");
        signUpRequest.setPassword("12345678");
        return signUpRequest;
    }

    private void onUserHasSignedUp(SignUpRequest signUpRequest)
    {
        //try to login the user and then go to explore fragment
        persist(signUpRequest);
        login(signUpRequest.getPassword());
    }

    private void persist(SignUpRequest signUpRequest)
    {
        MyApplication.getSharedPreferencesManager().persist(USERNAME_PERSISTED, signUpRequest.getUsername());
        MyApplication.getSharedPreferencesManager().persist(EMAIL_PERSISTED, signUpRequest.getEmail());
    }

    private void login(String password)
    {
        DialogHelper.showLoading(getContext());
        JSONObject paramsJsonObject = new JSONObject();
        try
        {
            paramsJsonObject.put("username", MyApplication.getSharedPreferencesManager().read(EMAIL_PERSISTED));
            paramsJsonObject.put("password", password);
            paramsJsonObject.put("client_id", 1);
            paramsJsonObject.put("client_secret", "zcWRDOGvr6jJKhdrc7I1R626mXSg0ORztaNZmpYJ");
            paramsJsonObject.put("grant_type", "password");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.LOGIN, paramsJsonObject,
                response -> {

                    DialogHelper.hideLoading();

                    if (response != null && response.has("access_token"))
                    {
                        LoginResponse loginResponse = new LoginResponse();
                        try
                        {
                            loginResponse.setAccessToken(response.getString("access_token"));
                            MyApplication.getSharedPreferencesManager().persist("token", response.getString("access_token"));
                            ((MainActivity) getActivity()).routeExploreFragment();


                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            onUserHasFailedToLogin();
                        }

                    }
                    else
                    {
                        onUserHasFailedToLogin();
                    }


                }, error -> {
            DialogHelper.hideLoading();


            if (error != null)
            {
                String json = new String(error.networkResponse.data);
                json = fetchErrorMessage(json, "user_message");
                onUserHasFailedToLogin(json);
            }
            else
            {
                onUserHasFailedToLogin();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headerHashMap = new HashMap<>();
                headerHashMap.put("Content-Type", "application/json; charset=utf-8");
                headerHashMap.put("Accept", "application/json");

                return headerHashMap;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
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
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));

        initTextWatchers();


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

        usernameTextInputEditText.addTextChangedListener(usernameTextWatcher);
        emailTextInputEditText.addTextChangedListener(emailTextWatcher);
        passwordTextInputEditText.addTextChangedListener(passwordTextWatcher);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
