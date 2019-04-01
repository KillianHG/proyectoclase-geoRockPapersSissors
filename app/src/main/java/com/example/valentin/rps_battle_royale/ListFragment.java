package com.example.valentin.rps_battle_royale;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    private ArrayList<PartidaDB> items;
    private MatchAdapter adapter;
    private SharedViewModel model;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_list, container, false);

        ListView partidasList = (ListView) view.findViewById(R.id.partidasList);

        items = new ArrayList<>();
        adapter = new MatchAdapter(
                getContext(),
                R.layout.partida_row,
                items
        );

        partidasList.setAdapter(adapter);

        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        model.getPartidas().observe(this, new Observer<List<PartidaDB>>() {
            @Override
            public void onChanged(@Nullable List<PartidaDB> partidaDBS) {
                adapter.clear();
                adapter.addAll(partidaDBS);
            }
        });

        return view;

    }
}
