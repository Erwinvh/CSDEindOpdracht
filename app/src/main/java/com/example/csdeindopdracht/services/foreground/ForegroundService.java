//package com.example.csdeindopdracht.services.foreground;
//
//import android.app.NotificationManager;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import com.example.csdeindopdracht.services.Notification;
//import com.example.csdeindopdracht.services.gps.GpsLocation;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class ForegroundService extends Service {
//
//    private Timer timer;
//    private GpsLocation gps;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        new ServiceChannel().onCreate();
//
//        this.gps = new GpsLocation(getApplicationContext());
//
//
////        timer.schedule(new TimerTask() {
////            @Override
////            public void run() {
////
////            }
////        }, 0, 1000);
//
//        startForeground(NotificationManager.AUTOMATIC_RULE_STATUS_ENABLED, Notification.createNotification(getApplicationContext(), "training", "").build());
//
//        return START_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
