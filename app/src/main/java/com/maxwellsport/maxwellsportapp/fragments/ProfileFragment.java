package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maxwellsport.maxwellsportapp.R;

// TODO zaczać wgl ten profil

public class ProfileFragment extends Fragment {

    private Button gymButton;
    private Button cardioButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        gymButton = (Button) v.findViewById(R.id.gymButton);
        cardioButton = (Button) v.findViewById(R.id.cardioButton);

        return v;
    }
}
