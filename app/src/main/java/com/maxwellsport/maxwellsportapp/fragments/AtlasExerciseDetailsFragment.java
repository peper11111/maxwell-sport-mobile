package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.Exercise;

public class AtlasExerciseDetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_atlas_exercise_details, container, false);
        Bundle bundle = getArguments();
        Exercise exercise = (Exercise) bundle.getSerializable("exercise-class");

        ImageView icon = (ImageView) v.findViewById(R.id.atlas_exercise_details_icon);
        icon.setImageDrawable(exercise.getIcon());
        TextView name = (TextView) v.findViewById(R.id.atlas_exercise_details_name);
        name.setText(exercise.getName());
        TextView description = (TextView) v.findViewById(R.id.atlas_exercise_details_description);
        description.setText(exercise.getDescription());

        return v;
    }
}
