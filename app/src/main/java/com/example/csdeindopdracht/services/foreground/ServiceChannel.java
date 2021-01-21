package com.example.csdeindopdracht.services.foreground;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.csdeindopdracht.R;

public class ServiceChannel extends Application {
    public static final String CHANNEL_ID_TRAINING = "Training";
    public static final String CHANNEL_ID_OTHER = "Other";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID_TRAINING,
                    getApplicationContext().getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
