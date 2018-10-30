package com.appestan.sharona.CustomUI;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.appestan.sharona.R;
import com.bumptech.glide.Glide;

public class LoadingDialog extends Dialog {

    ImageView loadingImageView;

    public LoadingDialog(Context context)
    {
        super(context);
        setContentView(R.layout.loading_dialog);
        init(context);
    }

    public LoadingDialog(Context context, int themeResId)
    {
        super(context, themeResId);
        init(context);

    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener)
    {
        super(context, cancelable, cancelListener);
        init(context);

    }


    private void init(Context context)
    {
        loadingImageView = findViewById(R.id.loading_dialog_image_view);
        Glide.with(context).load(R.drawable.loading_gif).into(loadingImageView);
        this.setCancelable(false);

    }
}
