package com.appestan.sharona.Fragment;

import android.graphics.Typeface;
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
import android.widget.TextView;

import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.Network.SignUpRequest;
import com.appestan.sharona.Network.SignUpResponse;
import com.appestan.sharona.R;
import com.appestan.sharona.Utility.DialogHelper;
import com.appestan.sharona.Utility.ValidationHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.sign_up_username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.sign_up_email_edit_text)
    EditText emailEditText;
    @BindView(R.id.sign_up_national_id_edit_text)
    EditText nationalIdEditText;
    @BindView(R.id.sign_up_password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.sign_up_sign_up_button)
    Button signUpButton;
    @BindView(R.id.sign_up_login_text_view)
    TextView loginTextView;

    @BindView(R.id.sign_up_status_text_view)
    TextView statusTextView;

    @OnClick(R.id.sign_up_sign_up_button)
    void signUp()
    {
//        if (!ValidationHelper.isUserNameValid(usernameEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "Username is not valid!");
//            return;
//        }
//
//        if (!ValidationHelper.isEmailValid(emailEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "Email is not valid!");
//            return;
//        }
//
//        if (!ValidationHelper.isNationalIdValid(nationalIdEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "National Id is not valid!");
//            return;
//        }
//
//        if (!ValidationHelper.isPasswordValid(passwordEditText))
//        {
//            DialogHelper.showDialogWithMessage(getContext(), "Password is not valid!");
//            return;
//        }


//        SignUpRequest signUpRequest = fillSignUpRequestWithUserInput();
        SignUpRequest signUpRequest = fillSignUpRequestWithMockData();

        DialogHelper.showLoading(getContext());

        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);
        Call<SignUpResponse> signUpResponseCall = endpointApi.signUp(signUpRequest);

        signUpResponseCall.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response)
            {
                Log.d("TAG", "onResponse: " + response);
                DialogHelper.hideLoading();
                OnUserHasAttemptedToSignUp(response);

            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t)
            {
                Log.d("TAG", "onFailure: " + t.toString());
                DialogHelper.hideLoading();
//                onUserHasFailedToSignUp(response);
            }
        });


    }

    private SignUpRequest fillSignUpRequestWithUserInput()
    {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername(usernameEditText.getText().toString());
        signUpRequest.setEmail(emailEditText.getText().toString());
//        signUpRequest.setFirst_name("Ali");
//        signUpRequest.setLast_name("Alizade");
        signUpRequest.setNational_id(nationalIdEditText.getText().toString());
        signUpRequest.setPassword(passwordEditText.getText().toString());
        return signUpRequest;
    }

    private SignUpRequest fillSignUpRequestWithMockData()
    {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("ali6@gmail.com");
        signUpRequest.setUsername("Ali6");
        signUpRequest.setFirst_name("Ali");
        signUpRequest.setLast_name("Alizade");
        signUpRequest.setNational_id("5678901234");
        signUpRequest.setPassword("12345678");
        return signUpRequest;
    }

    private void OnUserHasAttemptedToSignUp(Response<SignUpResponse> response)
    {
        if (response == null)
        {
            onUserHasFailedToSignUp(response);
        }
        else
        {
            if (response.code() == 201)
            {
                onUserHasSignedUp();
            }
            else
            {
                onUserHasFailedToSignUp(response);
            }
        }
    }

    private void onUserHasFailedToSignUp(Response<SignUpResponse> response)
    {
        statusTextView.setText("SIGN UP FAILED!");
        if (response == null)
        {
            return;
        }

    }

    private void onUserHasSignedUp()
    {
        statusTextView.setText("SIGN UP OK!");
        ((MainActivity) getActivity()).routeExploreFragment();
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

        statusTextView.getTypeface();


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
