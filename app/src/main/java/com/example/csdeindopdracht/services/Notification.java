package com.example.csdeindopdracht.services;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.csdeindopdracht.R;

import static com.example.csdeindopdracht.services.foreground.ServiceChannel.CHANNEL_ID_OTHER;

public class Notification {

    /**
     * Create a clean notification without an intent.
     * @param context Necessary to access assign the notification to the certain channel.
     * @param title The title of the notification with the prefix "Forest Run ".
     * @param content The content of the notification in equally big font as the title.
     * @return An unbuild NotificationCompat.
     */
    public static NotificationCompat.Builder createNotification(Context context, String title, String content) {
        return new NotificationCompat.Builder(context, CHANNEL_ID_OTHER)
                .setSmallIcon(R.drawable.forestrun)
                .setContentTitle("Forest Run " + title)
                .setContentText(content)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setOnlyAlertOnce(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    /**
     * Create a clean notification with an intent.
     * @param context Necessary to access assign the notification to the certain channel.
     * @param title The title of the notification with the prefix "Forest Run ".
     * @param content The content of the notification in equally big font as the title.
     * @param intent The intent to which the user will be directed when the notification in tapped.
     * @return An unbuild NotificationCompat.
     */
    public static NotificationCompat.Builder createNotification(Context context, String title, String content, Intent intent) {
        return createNotification(context, title, content).setContentIntent(PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
