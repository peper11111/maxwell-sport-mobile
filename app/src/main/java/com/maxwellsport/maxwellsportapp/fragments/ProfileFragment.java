package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maxwellsport.maxwellsportapp.R;

// TODO: zachowac stan pól tekstowych ze statystykami po zmianie konfiguracji

public class ProfileFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView arrow = (ImageView) v.findViewById(R.id.profile_form_image);
        arrow.setColorFilter(getResources().getColor(R.color.material_green_500), PorterDuff.Mode.SRC_ATOP);

        return v;
    }
}
