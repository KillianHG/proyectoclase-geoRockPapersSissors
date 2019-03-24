package com.example.valentin.rps_battle_royale;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PositionFragment extends Fragment {
    TextView mLocationTextView;
    ProgressBar mLoading;
    private Button button;
    private SharedViewModel model;
    String adress;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public PositionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notificar, container, false);

        button = view.findViewById(R.id.button_location);
        mLocationTextView = view.findViewById(R.id.localitzacio);
        mLoading = view.findViewById(R.id.loading);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);




        model.getCurrentLatLng().observe(this, latlng -> {
            model.getCurrentAddress().observe(this, address -> {
                adress = getString(R.string.address_text,
                        address, System.currentTimeMillis());
            });

            Jugador jugador = new Jugador();
            jugador.setLatitud(String.valueOf(latlng.latitude));
            jugador.setLongitud(String.valueOf(latlng.longitude));
            jugador.setDireccion(adress);


            FirebaseAuth auth = FirebaseAuth.getInstance();
            DatabaseReference base = FirebaseDatabase.getInstance().getReference();

            if (auth.getUid() != null) {

                Map<String, Object> postValues = jugador.toMap();

                Map<String, Object> childUpdates = new HashMap<>();

                if (postValues.isEmpty()) {
                    Log.e(TAG, "Aqui no hay ningun dato de M");
                } else {
                    Log.i(TAG, "Aqui hay algo!!! Sera un calamar gigante? ===" + postValues);
                }

                Toast.makeText(getContext(), "Avís donat", Toast.LENGTH_SHORT).show();

                childUpdates.put("/users/" + auth.getUid(), postValues);
                mDatabase.updateChildren(childUpdates);
            }
        });



        model.getCurrentAddress().observe(this, address -> {
            mLocationTextView.setText(getString(R.string.address_text,
                    address, System.currentTimeMillis()));
        });

        model.getButtonText().observe(this, s -> button.setText(s));
        model.getProgressBar().observe(this, visible -> {
            if(visible)
                mLoading.setVisibility(ProgressBar.VISIBLE);
            else
                mLoading.setVisibility(ProgressBar.INVISIBLE);
        });

        button.setOnClickListener((View clickedView) -> model.switchTrackingLocation());

        return view;
    }

}
