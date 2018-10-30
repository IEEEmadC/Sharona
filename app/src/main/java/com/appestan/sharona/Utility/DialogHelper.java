package com.appestan.sharona.Utility;

import android.content.Context;

import com.appestan.sharona.CustomUI.InformationDialog;
import com.appestan.sharona.CustomUI.LoadingDialog;

public class DialogHelper {

    private static LoadingDialog loadingDialog;

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
