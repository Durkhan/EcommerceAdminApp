package com.tasks.ecommerceadmin;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FCMSender {
    public static void sendNotification(String restApiKey, String appId, String message) {
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();

        try {
            JSONObject contents = new JSONObject();
            contents.put("en", message);
            json.put("contents", contents);
            json.put("app_id", appId);

            JSONArray includedSegments = new JSONArray();
            includedSegments.put("All");
            json.put("included_segments", includedSegments);

            System.out.println(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body=RequestBody.create(mediaType, json.toString());


        Request request = new Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .post(body)
                .addHeader("Authorization", "Basic " + restApiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                Log.d("OneSignalExample", "Notification sent successfully");
            } else {
                Log.e("OneSignalExample", "Notification send failed: " + response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}