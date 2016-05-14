package com.invertisment.webgrep;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 5/14/16.
 *
 * @author invertisment
 */

class RequestTask {

    private final String[] grep;
    private final InfoReceiver infoReceiver;
    private final Context context;

    public RequestTask(String[] grep, InfoReceiver infoReceiver, Context context) {
        this.grep = grep;
        this.infoReceiver = infoReceiver;
        this.context = context;
    }

    public Either doInBackground(String... urls) {
        try {

            return new Either(matchLine(httpGet(urls[0]), grep));
        } catch (Exception e) {
            infoReceiver.info("Exception", e.toString());
            return new Either("Exception: " + e.toString());
        }
    }

    public void onPostExecute(Either result) {

        Intent resultIntent = new Intent("com.invertisment.webgrep.GrepService.RESULT_ACTION", null);
        Bundle out = new Bundle();

        if (result.isEmpty()) {
            out.putString("error", result.getErrorMessage());
        } else {
            infoReceiver.info("Success", "Sending a Broadcast");
            out.putStringArray("matches", result.getMatches());
        }

        resultIntent.putExtras(out);

        context.sendBroadcast(resultIntent);
    }

    private String httpGet(String address) throws IOException {
        InputStream is = null;

        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            is = conn.getInputStream()));

            StringBuilder buff = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buff.append(line);
            }

            return buff.toString();

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String[] matchLine(String result, String[] grep) {

        String[] matches = new String[grep.length];

        if (result == null) {
            infoReceiver.info("Error", "Result is null");
            return matches;
        }

        for (int i = 0; i < grep.length; i++) {
            matches[i] = find(result, grep[i]);
        }

        return matches;
    }

    private String find(String doc, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(doc);

        if (m.find() && m.groupCount() > 0) {

            return m.group(1);
        }
        return "";
    }

    public interface InfoReceiver {
        void info(CharSequence tag, CharSequence info);
    }
}

