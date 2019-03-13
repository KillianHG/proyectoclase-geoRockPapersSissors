package com.example.valentin.rps_battle_royale;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class TimeZoneApi {
    private final String BASE_URL = "http://api.timezonedb.com/v2.1/list-time-zone";
    private final String API_KEY = "A8BUJ0RBO58T";
    String em = "Europe/Madrid";

    //http://api.timezonedb.com/v2.1/list-time-zone?key=A8BUJ0RBO58T&format=json&by=zone&zone=Europe/Madrid

    String getTimeZone() {
        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendQueryParameter("key", API_KEY)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("by", "zone")
                .appendQueryParameter("zone", em)
                .build();
        String url = builtUri.toString();

        Log.d("Debug", url != null ? url: null);

        return doCall(url);
    }

    private String doCall (String url){
        try {
            String JsonResponse = HttpUtils.get(url);
            return processJSON(JsonResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String processJSON (String jsonResponse){

        String timeZone = "";
        try {
            JSONObject data = new JSONObject(jsonResponse);
            JSONArray jsonZones = data.getJSONArray("zones");
            for (int i = 0; i < jsonZones.length(); i++) {
                JSONObject jsonZone = jsonZones.getJSONObject(i);

                timeZone = jsonZone.getString("timestamp");
            }

            Log.d("timeZone", timeZone != null ? timeZone: null);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return timeZone;
    }

}
