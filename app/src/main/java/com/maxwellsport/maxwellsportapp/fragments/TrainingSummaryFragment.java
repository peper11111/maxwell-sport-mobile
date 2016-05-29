package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;

import java.util.Locale;

public class TrainingSummaryFragment extends Fragment {
    private View mView;
    private int[] mStars = {R.id.training_summary_star_1, R.id.training_summary_star_2, R.id.training_summary_star_3, R.id.training_summary_star_4, R.id.training_summary_star_5};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_training_summary, container, false);

        Bundle args = getArguments();
        long trainingTime = args.getLong("training-time");
        setTimeView(trainingTime);

        for (int i = 0; i < 5; i++) {
            ImageView star = (ImageView) mView.findViewById(mStars[i]);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    int n = parent.indexOfChild(v);
                    colorStars(n + 1);
                }
            });
        }

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.training_summary_fab_finished);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrainingFragment fragment = new TrainingFragment();
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        return mView;
    }

    private void setTimeView(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        TextView timeView = (TextView) mView.findViewById(R.id.training_summary_time_value);
        timeView.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void colorStars(int n) {
        LinearLayout stars = (LinearLayout) mView.findViewById(R.id.training_summary_rate);
        int i;
        /* Pokolorowanie gwiazdek na color accent */
        for (i = 0; i < n; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }

        /* Pokolorowoanie pozostalych gwiazdek na biało */
        for (; i < 5; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(getResources().getColor(R.color.material_white), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
