package com.sharonaapp.sharona.custom_ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.R;

public class LoadingDialog extends Dialog {

    ImageView loadingImageView;

    public LoadingDialog(Context context)
    {
        super(context);
        setContentView(R.layout.loading_dialog);
        init(context);
    }


    private void init(Context context)
    {
        loadingImageView = findViewById(R.id.loading_dialog_image_view);
        Glide.with(context).load(R.drawable.loading_gif).into(loadingImageView);
        this.setCancelable(false);

    }
}
