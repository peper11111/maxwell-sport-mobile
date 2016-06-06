package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;

// TODO: zaimplementowac metody z cyklem zycia fragmentu do treningu

public class TrainingFragment extends Fragment {
    private MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_training_title));

        View v = inflater.inflate(R.layout.fragment_training, container, false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.training_fab_start);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingDayFragment fragment = new TrainingDayFragment();
                mContext.addFragment(fragment);
            }
        });

        return v;
    }
}
