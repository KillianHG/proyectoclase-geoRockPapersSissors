package com.example.valentin.rps_battle_royale;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private Button logout;
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

            if (auth.getUid() != null) {

            }
        });


        model.getCurrentAddress().observe(this, address -> {
            mLocationTextView.setText(getString(R.string.address_text,
                    address, System.currentTimeMillis()));
        });

        model.getButtonText().observe(this, s -> button.setText(s));
        model.getProgressBar().observe(this, visible -> {
            if (visible)
                mLoading.setVisibility(ProgressBar.VISIBLE);
            else
                mLoading.setVisibility(ProgressBar.INVISIBLE);
        });

        button.setOnClickListener((View clickedView) -> model.switchTrackingLocation());



        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference base = FirebaseDatabase.getInstance().getReference();

        if (auth.getUid() != null) {

            DatabaseReference partidas = base.child("partidas");
            System.out.println(".......: " + partidas.toString());

            //TODO: ===========================================!!!!!!!!!!!!!!!!
            SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
            try {
                partidas.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Partida partida = dataSnapshot.getValue(Partida.class);

                        System.out.println("-----------------"+partida.toString());
                        String[] users = new String[2];
                        users[0] = partida.getUserid1();
                        users[1] = auth.getUid();

                        String[] partidaArr = new String[3];
                        partidaArr[0] = partida.getUserid1();
                        partidaArr[1] = partida.getUserid2();
                        partidaArr[2] = partida.getActionid1();

                        if (partida.getUserid2().equals(auth.getUid()) && partida.getActionid2() == null){
                            System.out.println("-----PENE DE 299 ES PEQUEÑO QUE FLIPAS-----");

                            GameFragment newGamefragment = new GameFragment();
                            Bundle args = new Bundle();
                            args.putStringArray("partida", partidaArr);

                            newGamefragment.setArguments(args);

                            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.selected_fragment, newGamefragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Partida partida = dataSnapshot.getValue(Partida.class);

                        System.out.println("-----------------"+partida.toString());
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return view;
    }

}
