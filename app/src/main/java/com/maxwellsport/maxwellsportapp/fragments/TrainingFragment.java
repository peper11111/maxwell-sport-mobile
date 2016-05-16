package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.maxwellsport.maxwellsportapp.R;

public class TrainingFragment extends Fragment implements View.OnClickListener{

//    TODO: zmienic ten brzydki i paskudny wyglad na mniej paskudny

    private View view;
    private Button startBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_training, container, false);
        startBtn = (Button) view.findViewById(R.id.start_trainingBtn);
        startBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        TrainingDayFragment fragment = new TrainingDayFragment();
        this.getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment,null).addToBackStack(null).commit();
    }
}
