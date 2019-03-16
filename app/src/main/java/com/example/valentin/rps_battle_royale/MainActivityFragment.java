package com.example.valentin.rps_battle_royale;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    String time;
    View view;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        return view;

    }

    private class TimeStampDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result;
            TimeZoneApi api = new TimeZoneApi();
            result = api.getTimeZone();
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Date date = new Date(Long.parseLong(result) * 1000L);
            SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
            dateformat.setTimeZone(TimeZone.getTimeZone("GMT"));
            System.out.println(date);
            time = (dateformat.format(date));
        }
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        TimeStampDataTask task = new TimeStampDataTask();
        task.execute();
    }
*/


}
