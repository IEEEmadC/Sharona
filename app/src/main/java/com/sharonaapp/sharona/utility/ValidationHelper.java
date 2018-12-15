package com.sharonaapp.sharona.utility;

import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;

import com.sharonaapp.sharona.MyApplication;

public class ValidationHelper {

    public static boolean isUserNameValid(EditText editText)
    {
        return (editText.getText() != null &&
                editText.getText().toString().length() > 4);
    }

    public static boolean isEmailValid(EditText editText)
    {
        return (editText.getText() != null &&
                Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches());
    }

    public static boolean isNationalIdValid(EditText editText)
    {
        return (editText.getText() != null &&
                editText.getText().toString().length() > 9);
    }

    public static boolean isPasswordValid(EditText editText)
    {
        return (editText.getText() != null &&
                editText.getText().toString().length() > 7);
    }

    public static boolean isCityValid(TextInputEditText cityTextInputEditText)
    {
        return (cityTextInputEditText.getText() != null &&
                cityTextInputEditText.getText().toString().length() > 2);
    }

    public static boolean isAddressValid(TextInputEditText addressTextInputEditText)
    {
        return (addressTextInputEditText.getText() != null &&
                addressTextInputEditText.getText().toString().length() > 9);
    }

    public static boolean isPhoneNumberValid(TextInputEditText phoneNumberTextInputEditText)
    {
        return (phoneNumberTextInputEditText.getText() != null &&
                phoneNumberTextInputEditText.getText().toString().length() > 7);
    }

    public static boolean isUserGenderValid(TextView genderValueTextView)
    {
        return MyApplication.getConfig().getUserGenderArrayList().contains(genderValueTextView.getText().toString());
    }
}
