package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.maxwellsport.maxwellsportapp.R;

// TODO: zaimplementowac metody z cyklem zycia fragmentu do treningu

public class TrainingFragment extends Fragment implements View.OnClickListener{

    private View view;
    private LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training, container, false);

        /*layout = (LinearLayout) view.findViewById(R.id.trainig_day1);
        layout.setOnClickListener(this);*/

        return view;
    }

    @Override
    public void onClick(View v) {
        TrainingDayFragment fragment = new TrainingDayFragment();
        this.getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }
}
