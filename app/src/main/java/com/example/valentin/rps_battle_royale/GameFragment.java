package com.example.valentin.rps_battle_royale;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    Button play_btn;
    boolean jugarPartida = false;
    String[] usersIds = new String[2];


    Partida partida;

    Button rock_btn;
    Button paper_btn;
    Button scissors_btn;


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
                        System.out.println("Rokilla");
                        partida = new Partida();
                        partida.setUserid1(usersIds[0]);
                        partida.setUserid2(usersIds[1]);
                        partida.setActionid1("r");
                        partida.setChecked(false);

                        DatabaseReference base = FirebaseDatabase.getInstance(
                        ).getReference();

                        DatabaseReference users = base.child("partidas");

                        DatabaseReference reference = users.push();
                        reference.setValue(partida);

                        System.out.println(partida.toString());
                        break;
                    case R.id.paper_btn:
                        System.out.println("Papiro");
                        partida = new Partida();
                        partida.setUserid1(usersIds[0]);
                        partida.setUserid2(usersIds[1]);
                        partida.setActionid1("p");
                        partida.setChecked(false);

                        System.out.println(partida.toString());
                        break;
                    case R.id.scissors_btn:
                        System.out.println("Ciencia");
                        partida = new Partida();
                        partida.setUserid1(usersIds[0]);
                        partida.setUserid2(usersIds[1]);
                        partida.setActionid1("s");
                        partida.setChecked(false);

                        System.out.println(partida.toString());
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
    public void onStart() {
        super.onStart();

        usersIds = getArguments().getStringArray("uids");
        System.out.println(usersIds.toString());
    }

    public String[] getUsersId(String id1, String id2) {
        usersIds[0] = id1;
        usersIds[1] = id2;

        return usersIds;
    }
}
