package com.invertisment.webgrep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created on 5/14/16.
 *
 * @author invertisment
 */
public class GrepService extends NotificationService implements RequestTask.InfoReceiver {

    private static final CharSequence ERROR = "Error";

    public GrepService() {
        super("Grep service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            Bundle extra = intent.getExtras();

            if (extra == null) {
                error("nulls appeared");
                return;
            }

            String url = extra.getString("url");
            String[] grep = extra.getStringArray("grep");

            if (url == null || grep == null) {
                error("nulls appeared");
                return;
            }

            RequestTask requestTask = new RequestTask(grep, this, this);
            requestTask.onPostExecute(
                    requestTask.doInBackground(url)
            );

        } catch (Exception e) {
            error(e.getMessage());
        } finally {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            cancelNotification();
            stopSelf();
        }
    }

    @Override
    public void info(CharSequence tag, CharSequence info) {

        Log.d(getClass().getSimpleName(), info.toString());

        updateNotification(tag, info);
    }

    private void error(CharSequence info) {
        info(ERROR, info);
    }
}
