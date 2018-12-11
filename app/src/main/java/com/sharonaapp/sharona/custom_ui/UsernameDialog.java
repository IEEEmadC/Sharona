package com.sharonaapp.sharona.custom_ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sharonaapp.sharona.R;

public class UsernameDialog extends Dialog {

    EditText usernameEditText;
    Button registerButton;

    public UsernameDialog(@NonNull Context context)
    {
        super(context);
        setContentView(R.layout.dialog_username);
        usernameEditText = findViewById(R.id.username_dialog_username_edit_text);
        registerButton = findViewById(R.id.username_dialog_register_button);
        registerButton.setOnClickListener(view -> {
            Toast.makeText(context, "" + usernameEditText.getText().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    public UsernameDialog(@NonNull Context context, int themeResId)
    {
        super(context, themeResId);
    }

    protected UsernameDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
    }
}
