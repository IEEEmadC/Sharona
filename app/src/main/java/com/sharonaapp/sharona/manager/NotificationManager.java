package com.sharonaapp.sharona.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.sharonaapp.sharona.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationManager {

    private Context context;
    private android.app.NotificationManager notificationManager;

    public NotificationManager(Context context)
    {
        this.context = context;
        notificationManager = context.getSystemService(android.app.NotificationManager.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(Context context)
    {

        this.context = context;
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = android.app.NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = context.getSystemService(android.app.NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void showNotif(Context context)
    {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                .setSmallIcon(R.drawable.anim_sharona_logo)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    @NonNull
    public synchronized void build(String notificationTitle, String notificationContent)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            Notification.Builder builder = new Notification.Builder(context, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.anim_sharona_logo)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
                    .setAutoCancel(false);
//            if (sound != null)
//            {
//                builder.setSound(sound);
//            }
//
//            if (largeIcon != null)
//            {
//                builder.setLargeIcon(largeIcon);
//            }
//
//            if (pendingIntent != null)
//            {
//                builder.setContentIntent(pendingIntent);
//            }
//            return builder.build();

            builder.build().notify();

        }
        else
        {
            android.app.NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                    .setSmallIcon(R.drawable.anim_sharona_logo)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationContent)
//                    .setVibrate()
                    .setAutoCancel(false);

//            if (light == 0)
//            {
//                light = Color.BLUE;
//            }
//            builder.setLights(light, 3000, 3000);
//
//            if (sound != null)
//            {
//                builder.setSound(sound);
//            }
//
//            if (largeIcon != null)
//            {
//                builder.setLargeIcon(largeIcon);
//            }
//
//            if (pendingIntent != null)
//            {
//                builder.setContentIntent(pendingIntent);
//            }

//            return builder.build();


//            builder.build().notify();
            notificationManager.notify(0, builder.build());


        }


    }
}
