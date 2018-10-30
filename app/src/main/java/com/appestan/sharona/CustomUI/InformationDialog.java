package com.appestan.sharona.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestan.sharona.R;
import com.bumptech.glide.Glide;

public class InformationDialog extends Dialog {

    public InformationDialog(Context context, String message)
    {
        super(context);
        setContentView(R.layout.dialog_information);
        ((TextView) findViewById(R.id.dialog_information_message_text_view)).setText(message);
    }


}
