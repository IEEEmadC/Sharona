package com.appestan.sharona.Utility;

import android.util.Patterns;
import android.widget.EditText;

public class ValidationHelper {

    public static boolean isUserNameValid(EditText editText)
    {
        return (editText.getText() != null &&
                editText.getText().toString().length() > 7);
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
}
