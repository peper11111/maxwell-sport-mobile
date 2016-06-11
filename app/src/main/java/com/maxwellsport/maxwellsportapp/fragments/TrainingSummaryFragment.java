package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.services.DataConversionService;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

public class TrainingSummaryFragment extends Fragment {
    private MainActivity mContext;
    private View mView;
    private int[] mStars = {R.id.training_summary_star_1, R.id.training_summary_star_2, R.id.training_summary_star_3, R.id.training_summary_star_4, R.id.training_summary_star_5};
    private int mColor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_training_summary_title));

        mView = inflater.inflate(R.layout.fragment_training_summary, container, false);

        Bundle args = getArguments();
        long trainingTime = args.getLong("training-time");
        TextView timeView = (TextView) mView.findViewById(R.id.training_summary_time_value);
        timeView.setText(DataConversionService.convertTime(trainingTime));

        int style = SharedPreferencesService.getInt(mContext, SharedPreferencesService.settings_theme_key, R.style.CyanAccentColorTheme);
        int[] attr = {R.attr.colorAccent};
        TypedArray array = mContext.obtainStyledAttributes(style, attr);
        mColor = array.getColor(0, Color.WHITE);
        array.recycle();

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
                mContext.replaceFragment(fragment);
            }
        });

        return mView;
    }

    private void colorStars(int n) {
        LinearLayout stars = (LinearLayout) mView.findViewById(R.id.training_summary_rate);
        int i;
        /* Pokolorowanie gwiazdek na color accent */
        for (i = 0; i < n; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(mColor, PorterDuff.Mode.SRC_ATOP);
        }

        /* Pokolorowoanie pozostalych gwiazdek na biało */
        for (; i < 5; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }
}
