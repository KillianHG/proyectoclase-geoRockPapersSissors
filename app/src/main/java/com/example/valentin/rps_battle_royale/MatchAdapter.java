package com.example.valentin.rps_battle_royale;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class MatchAdapter extends ArrayAdapter<PartidaDB> {

    public MatchAdapter(Context context, int resource, List<PartidaDB> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PartidaDB partidaDB = getItem(position);
        Log.w("XXXX", partidaDB.toString());

        // Mirem a veure si la View s'està reutilitzant, si no es així "inflem" la View
        // https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView#row-view-recycling
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.partida_row, parent, false);
        }

        // Unim el codi en les Views del Layout
        TextView horatv = convertView.findViewById(R.id.horatv);
        TextView resultadotv = convertView.findViewById(R.id.resultadotv);
        TextView tutv = convertView.findViewById(R.id.tutv);
        TextView oponentetv = convertView.findViewById(R.id.oponentetv);
        RelativeLayout layout_r = convertView.findViewById(R.id.layout_r);

        // Fiquem les dades dels objectes (provinents del JSON) en el layout
        horatv.setText(partidaDB.getHora());
        resultadotv.setText(partidaDB.getResult());
        tutv.setText(partidaDB.getUser());
        oponentetv.setText(partidaDB.getOpponent());
        try {
            if (partidaDB.getResult().equals("VICTORIA")) {
                convertView.setBackgroundColor(Color.parseColor("#98C9A3"));
            } else if (partidaDB.getResult().equals("DERROTA")) {
                convertView.setBackgroundColor(Color.parseColor("#E8A0A0"));
            } else if (partidaDB.getResult().equals("EMPATE")) {
                convertView.setBackgroundColor(Color.parseColor("#D6D8D6"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retornem la View replena per a mostrar-la
        return convertView;
    }

}
