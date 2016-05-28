package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxwellsport.maxwellsportapp.R;

// TODO: zaimplementowac metody z cyklem zycia fragmentu do treningu

public class TrainingFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_training, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.training_fab_start);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingDayFragment fragment = new TrainingDayFragment();
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit();
            }
        });

        return v;
    }
}
