package com.sharonaapp.sharona.utility;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class CommonHelper {
    /**
     * get actual path from uri
     *
     * @param context    context
     * @param contentUri uri
     * @return actual path
     */
    public static String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }

    public static boolean hideKeyboard(Context context, View v)
    {
        try
        {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            return imm != null && imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        catch (Exception var3)
        {
            var3.printStackTrace();
            return false;
        }
    }

}
