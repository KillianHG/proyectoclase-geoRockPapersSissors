package com.example.valentin.rps_battle_royale;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    Button play_btn;
    boolean jugarPartida = false;

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

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugarPartida = true;

                play_btn.setVisibility(View.GONE);
                rock_btn.setVisibility(View.VISIBLE);
                paper_btn.setVisibility(View.VISIBLE);
                scissors_btn.setVisibility(View.VISIBLE);

            }
        });



        return view;
    }

    public void getUsersId (String meID, String enemyID) {

    }

}
