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

// TODO: zachowac stan pól tekstowych ze statystykami po zmianie konfiguracji

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
        gymButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        gymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardio.setVisibility(View.INVISIBLE);
                gym.setVisibility(View.VISIBLE);
                gymButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardioButton.setBackgroundColor(getResources().getColor(R.color.primary_dark_material_light));
            }
        });

        cardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardio.setVisibility(View.VISIBLE);
                gym.setVisibility(View.INVISIBLE);
                cardioButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                gymButton.setBackgroundColor(getResources().getColor(R.color.primary_dark_material_light));
            }
        });

        return v;
    }
}
