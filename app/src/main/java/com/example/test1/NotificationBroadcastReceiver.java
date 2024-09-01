package com.example.test1;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Build and display the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "plant_tasks")
                .setSmallIcon(R.drawable.baseline_notification_important_24)
                .setContentTitle("Plant Task Reminder")
                .setContentText("Don't forget to water your plants!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        }else {
            // Permission is granted, send the notification
            notificationManager.notify(0, builder.build());
        }
    }
}

