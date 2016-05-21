package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import com.maxwellsport.maxwellsportapp.R;


public class ProfileFragment extends Fragment {

    private Button gymButton;
    private Button cardioButton;
    private TableLayout cardio;
    private TableLayout gym;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        gymButton = (Button) v.findViewById(R.id.gymButton);
        cardioButton = (Button) v.findViewById(R.id.cardioButton);
        cardio = (TableLayout) v.findViewById(R.id.profil_cardio_table);
        gym = (TableLayout) v.findViewById(R.id.profil_gym_table);
        gymButton.setBackgroundColor(Color.parseColor("#bebebe"));

        gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardio.setVisibility(View.INVISIBLE);
                gym.setVisibility(View.VISIBLE);
                gymButton.setBackgroundColor(Color.parseColor("#bebebe"));
                cardioButton.setBackgroundColor(getResources().getColor(R.color.light_grey));
            }
        });

        cardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardio.setVisibility(View.VISIBLE);
                gym.setVisibility(View.INVISIBLE);
                cardioButton.setBackgroundColor(Color.parseColor("#bebebe"));
                gymButton.setBackgroundColor(getResources().getColor(R.color.light_grey));
            }
        });

        return v;
    }
}
