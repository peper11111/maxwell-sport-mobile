package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.ListMenuItemView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.maxwellsport.maxwellsportapp.R;

public class TrainingFragment extends Fragment implements View.OnClickListener{


    private View view;
    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training, container, false);

        layout = (LinearLayout) view.findViewById(R.id.trainig_day1);
        layout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        TrainingDayFragment fragment = new TrainingDayFragment();
        this.getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,null).addToBackStack(null).commit();
    }
}
