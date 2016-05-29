package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        LinearLayout difficulty = (LinearLayout) v.findViewById(R.id.atlas_exercise_details_difficulty);
        for (int i = 0; i < exercise.getDifficulty(); i++) {
            ImageView star = (ImageView) difficulty.getChildAt(i);
            star.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }

        String[] diff_name = getResources().getStringArray(R.array.atlas_exercise_difficulty_name);

        TextView difficulty_name = (TextView) v.findViewById(R.id.atlas_exercise_details_difficulty_name);
        difficulty_name.setText(diff_name[exercise.getDifficulty() - 1]);

        return v;
    }
}
