package com.example.csdeindopdracht.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class Notify {

    /**
     * Create a clean notification
     * @param context Necessary to access the system service.
     */
    public static void createNotification(Context context, String title, String content) {
        context.getSystemService(NotificationManager.class).notify(
                (int) System.currentTimeMillis(),
                Notification.createNotification(context, title, content).build()
        );
    }

    /**
     * Create a clean notification with intend.
     * @param context Necessary to access the system service.
     * @param intent Redirect the user to this intent when the notification is clicked.
     */
    public static void createNotification(Context context, String title, String content, Intent intent) {
        context.getSystemService(NotificationManager.class).notify(
                (int) System.currentTimeMillis(),
                Notification.createNotification(context, title, content, intent).build()
        );
    }

    /**
     * Vibrate the phone.
     * @param context Necessary to access the vibrator service.
     */
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    /**
     * Play the Android notification sound.
     * @param context Necessary to create a ringtone.
     */
    public static void playNotificationSound(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }
}
