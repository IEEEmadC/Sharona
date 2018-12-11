package com.sharonaapp.sharona.custom_ui;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.sharonaapp.sharona.R;

import butterknife.ButterKnife;

public class ShebaAccountDialog extends Dialog {
//    @Bind
    public ShebaAccountDialog(@NonNull Context context)
    {
        super(context);
        setContentView(R.layout.dialog_sheba_account);
        ButterKnife.bind(context, this);
    }
}
