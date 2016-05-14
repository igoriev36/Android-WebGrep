package com.invertisment.webgrep;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

/**
 * Created on 5/14/16.
 *
 * @author invertisment
 */
public abstract class NotificationService extends IntentService {

    public NotificationService(String name) {
        super(name);
    }

    Notification.Builder notificationBuilder;

    NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_popup_sync);
    }

    public void updateNotification(CharSequence title, CharSequence text) {
        notificationManager.notify(R.id.sync_notification_id,
                notificationBuilder
                        .setContentTitle(title)
                        .setContentText(text)
                        .build());
    }

    public void cancelNotification() {
        notificationManager.cancel(R.id.sync_notification_id);
    }

}
