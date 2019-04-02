package com.example.valentin.rps_battle_royale;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static android.support.constraint.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    Button play_btn;
    boolean reto;
    String[] usersIds = new String[2];
    String[] partidaArr = new String[4];
    String time;
    PartidaDB partidaDB = new PartidaDB();
    String gameResult;

    Partida partida;

    Button rock_btn;
    Button paper_btn;
    Button scissors_btn;

    private SharedViewModel model;


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        play_btn = view.findViewById(R.id.play_btn);
        rock_btn = view.findViewById(R.id.rock_btn);
        paper_btn = view.findViewById(R.id.paper_btn);
        scissors_btn = view.findViewById(R.id.scissors_btn);

        model = ViewModelProviders.of(this).get(SharedViewModel.class);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.play_btn:
                        play_btn.setVisibility(View.GONE);
                        rock_btn.setVisibility(View.VISIBLE);
                        paper_btn.setVisibility(View.VISIBLE);
                        scissors_btn.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rock_btn:
                        if (reto) {
                            partida = new Partida(usersIds[0], usersIds[1], "r", null, false);
                            matchPush(partida);
                        } else {
                            partida = new Partida(partidaArr[0], partidaArr[1], partidaArr[2], "r", false);
                            matchUpdate(partida);
                            TimeStampDataTask task = new TimeStampDataTask();
                            task.execute();
                        }
                        getFragmentManager().popBackStackImmediate();
                        break;
                    case R.id.paper_btn:
                        if (reto) {
                            partida = new Partida(usersIds[0], usersIds[1], "p", null, false);
                            matchPush(partida);
                        } else {
                            partida = new Partida(partidaArr[0], partidaArr[1], partidaArr[2], "p", false);
                            matchUpdate(partida);
                            TimeStampDataTask task = new TimeStampDataTask();
                            task.execute();
                        }
                        getFragmentManager().popBackStackImmediate();
                        break;
                    case R.id.scissors_btn:
                        if (reto) {
                            partida = new Partida(usersIds[0], usersIds[1], "s", null, false);
                            matchPush(partida);
                        } else {
                            partida = new Partida(partidaArr[0], partidaArr[1], partidaArr[2], "s", false);
                            matchUpdate(partida);
                            TimeStampDataTask task = new TimeStampDataTask();
                            task.execute();
                        }
                        getFragmentManager().popBackStackImmediate();
                        break;
                }
            }
        };
        play_btn.setOnClickListener(listener);
        rock_btn.setOnClickListener(listener);
        paper_btn.setOnClickListener(listener);
        scissors_btn.setOnClickListener(listener);
        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().getStringArray("partida") != null) {
            reto = false;
            partidaArr = getArguments().getStringArray("partida");
            System.out.println(partidaArr.toString());
        } else if (getArguments().getStringArray("uids") != null) {
            reto = true;
            usersIds = getArguments().getStringArray("uids");
            System.out.println(partidaArr.toString());
        }
    }

    public String[] getUsersId(String id1, String id2) {
        usersIds[0] = id1;
        usersIds[1] = id2;

        return usersIds;
    }

    public void matchPush(Partida partida) {
        DatabaseReference base = FirebaseDatabase.getInstance(
        ).getReference();

        DatabaseReference users = base.child("partidas");

        DatabaseReference reference = users.push();
        reference.setValue(partida);
    }

    public void matchUpdate(Partida partida) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        Map<String, Object> postValues = partida.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        if (postValues.isEmpty()) {
            Log.e(TAG, "Aqui no hay ningun dato de M");
        } else {
            Log.i(TAG, "Aqui hay algo!!! Sera un RABO gigante? ___" + postValues);
        }
        int res = partida.checkWin();
        if (res == 1) {
            gameResult = "DERROTA";
        } else if (res == 2) {
            gameResult = "VICTORIA";
        } else {
            gameResult = "EMPATE";
        }

        childUpdates.put("/partidas/" + partidaArr[3], postValues);
        mDatabase.updateChildren(childUpdates);

        Toast.makeText(getContext(), gameResult, Toast.LENGTH_LONG).show();
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

            partidaDB.setUser(partida.getUserid2());
            partidaDB.setOpponent(partida.getUserid1());
            partidaDB.setUserAction(partida.getActionid2());
            partidaDB.setOpponentAction(partida.getActionid1());
            partidaDB.setHora(time);
            partidaDB.setResult(gameResult);

            System.out.println("-----------INTRODUSIENDO----------");
            model.addPartida(partidaDB);
        }
    }
}
