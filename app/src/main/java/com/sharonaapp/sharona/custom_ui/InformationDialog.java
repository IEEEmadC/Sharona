package com.sharonaapp.sharona.custom_ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.sharonaapp.sharona.R;

public class InformationDialog extends Dialog {

    public InformationDialog(Context context, String message)
    {
        super(context);
        setContentView(R.layout.dialog_information);
        ((TextView) findViewById(R.id.dialog_information_message_text_view)).setText(message);
    }


}
