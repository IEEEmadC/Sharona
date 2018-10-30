package com.appestan.sharona.Managers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class CallManager {

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1001;


    public static void callPhoneNumber(Activity activity, String phoneNumber)
    {
        checkPermissions(activity, phoneNumber);
    }

    private static void checkPermissions(Activity activity, String phoneNumber)
    {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CALL_PHONE))
            {
                activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
            }
            else
            {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
        else
        {
            startCallIntent(activity, phoneNumber);
        }
    }

    @SuppressLint("MissingPermission")
    private static void startCallIntent(Activity activity, String phoneNumber)
    {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        activity.startActivity(intent);
    }
}
