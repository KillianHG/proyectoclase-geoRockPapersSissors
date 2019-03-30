package com.example.valentin.rps_battle_royale;


import android.annotation.SuppressLint;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {



    public MapFragment() {
        // Required empty public constructor


    }


    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference base = FirebaseDatabase.getInstance().getReference();

        if (auth.getUid() != null) {

            DatabaseReference users = base.child("users");
            DatabaseReference uid = users.child(auth.getUid());
            DatabaseReference jugadores = uid.child("users");

            SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

            mapFragment.getMapAsync(map -> {
                try {
                    // Customize the styling of the base map using a JSON object defined
                    // in a raw resource file.
                    boolean success = map.setMapStyle(
                            MapStyleOptions.loadRawResourceStyle(
                                    getActivity(), R.raw.map_style));

                    if (!success) {
                        Log.e(null, "Style parsing failed.");
                    }
                } catch (Resources.NotFoundException e) {
                    Log.e(null, "Can't find style. Error: ", e);
                }
                // Codi a executar quan el mapa s'acabi de carregar.
                map.setMyLocationEnabled(true);

                MutableLiveData<LatLng> currentLatLng = model.getCurrentLatLng();
                LifecycleOwner owner = getViewLifecycleOwner();
                currentLatLng.observe(owner, latLng -> {
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    map.animateCamera(cameraUpdate);
                    currentLatLng.removeObservers(owner);

                    Map<String, Marker> markers = new HashMap();

                    users.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Jugador jugador = dataSnapshot.getValue(Jugador.class);

                            LatLng aux = new LatLng(
                                    Double.valueOf(jugador.getLatitud()),
                                    Double.valueOf(jugador.getLongitud())
                            );

                            MarkerAdapter markerAdapter = new MarkerAdapter(
                                    getActivity()
                            );

                            float color = 90.0f;
                            Marker marker = map.addMarker(new MarkerOptions().title("JUGAR")
                                    .snippet("Victorias: \n" + "Derrotas: " )
                                    .icon(BitmapDescriptorFactory.defaultMarker(color))
                                    .position(aux));
                            marker.setTag(dataSnapshot.getKey());
                            markers.put(dataSnapshot.getKey(), marker);

                            map.setInfoWindowAdapter(markerAdapter);

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Jugador jugador = dataSnapshot.getValue(Jugador.class);

                            System.out.println(dataSnapshot);

                            LatLng aux = new LatLng(
                                    Double.valueOf(jugador.getLatitud()),
                                    Double.valueOf(jugador.getLongitud())
                            );


                            if (markers.containsKey(dataSnapshot.getKey())){
                                Marker marker = markers.get(dataSnapshot.getKey());

                                try {
                                    marker.setPosition(aux);
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                                @Override
                                public void onInfoWindowClick(Marker marker) {
                                    // TODO
                                    String enemyID = marker.getTag().toString();
                                    String myID = auth.getUid();

                                    System.out.println(marker.getTag());
                                    String[] users = new String[2];
                                    users[0] = myID;
                                    users[1] = enemyID;
                                    GameFragment gameFragment = new GameFragment();

                                    if (!auth.getUid().equals(marker.getTag())){
                                        Toast.makeText(getContext(), "FUCK YOU!", Toast.LENGTH_SHORT).show();
                                        GameFragment newGamefragment = new GameFragment();
                                        Bundle args = new Bundle();
                                        args.putStringArray("uids", users);

                                        newGamefragment.setArguments(args);

                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.selected_fragment, newGamefragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                        if (!auth.getUid().equals(marker.getTag())){
                                            gameFragment.getUsersId(myID, enemyID);
                                        }
                                    }
                                }
                            });
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

                });

            });

        }

        return view;
    }


}
