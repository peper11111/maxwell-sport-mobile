package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

// TODO: zaimplementowac metody z cyklem zycia fragmentu do treningu

public class TrainingFragment extends Fragment {
    private MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_training_title));

        View v = inflater.inflate(R.layout.fragment_training, container, false);

        TextView trainingWeight = (TextView) v.findViewById(R.id.training_weight_value);
        trainingWeight.setText(Integer.toString(SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.training_fragment_weight_key, 0)));

        TextView trainingSets = (TextView) v.findViewById(R.id.training_sets_value);
        trainingSets.setText(Integer.toString(SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.training_fragment_sets_amount_key, 0)));

        TextView trainingReps = (TextView) v.findViewById(R.id.training_reps_value);
        trainingReps.setText(Integer.toString(SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.training_fragment_reps_amount_key, 0)));

        TextView trainingAmount = (TextView) v.findViewById(R.id.training_exercise_amount_value);
        trainingAmount.setText(Integer.toString(SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.training_fragment_exercise_amount_key, 0)));

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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_grow_fade_in_from_bottom);
        } else {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_fade_out);
        }
    }
}
