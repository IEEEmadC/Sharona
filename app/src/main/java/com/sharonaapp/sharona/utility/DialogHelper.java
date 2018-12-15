package com.sharonaapp.sharona.utility;

import android.content.Context;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.custom_ui.InformationDialog;
import com.sharonaapp.sharona.custom_ui.LoadingDialog;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

public class DialogHelper {

    private static LoadingDialog loadingDialog;
//    private static MaterialDialog materialDialog;

    public static void warnDialog(Context context, String title, String message)
    {
        new LovelyInfoDialog(context)
                .setTopColorRes(R.color.colorSecondaryLight)
                .setIcon(R.drawable.ic_sharona_logo)
                .setNotShowAgainOptionChecked(false)
                .setTitle(title)
                .setMessage(message)
                .show();
    }

    public static void showLoading(Context context)
    {
        if (context == null)
        {
            return;
        }
        if (loadingDialog == null)
        {
            loadingDialog = new LoadingDialog(context);
        }

        loadingDialog.show();
    }

    public static void hideLoading()
    {
        if (loadingDialog != null)
        {
            loadingDialog.dismiss();
        }
    }

    public static void showDialogWithMessage(Context context, String message)
    {
        new InformationDialog(context, message).show();
    }
}
