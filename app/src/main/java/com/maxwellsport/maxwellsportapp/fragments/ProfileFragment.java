package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;

// TODO: zachowac stan pól tekstowych ze statystykami po zmianie konfiguracji

public class ProfileFragment extends Fragment {
    MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_profile_title));

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView trainingForm = (ImageView) v.findViewById(R.id.profile_training_form_value);
        trainingForm.setColorFilter(getResources().getColor(R.color.material_blue_500), PorterDuff.Mode.SRC_ATOP);

        ImageView runningForm = (ImageView) v.findViewById(R.id.profile_running_form_value);
        runningForm.setColorFilter(getResources().getColor(R.color.material_blue_500), PorterDuff.Mode.SRC_ATOP);

        return v;
    }
}
