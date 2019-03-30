package com.example.valentin.rps_battle_royale;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private final Activity activity;
    //private final String user;

    public MarkerAdapter(Activity activity) {
        this.activity = activity;
        //this.user = user;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = activity.getLayoutInflater()
                .inflate(R.layout.markerview, null);

        //Jugador jugador = (Jugador) marker.getTag();

        ImageView ivProfile = view.findViewById(R.id.iv_profile);
        TextView tvUser = view.findViewById(R.id.user_tv);
        TextView tvVictorias = view.findViewById(R.id.victorias_tv);
        TextView tvDerrotas = view.findViewById(R.id.derrotas_tv);
        TextView tvEmpates = view.findViewById(R.id.empates_tv);

        tvUser.setText(marker.getTag().toString());
        System.out.println("IIIIIII" + marker.getTag().toString());
        tvVictorias.setText("Victorias: ");
        tvDerrotas.setText("Derrotas: ");
        tvEmpates.setText("Empates: ");
        return view;
    }
}