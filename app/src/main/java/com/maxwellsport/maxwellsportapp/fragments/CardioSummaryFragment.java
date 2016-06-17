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

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.DataConversionHelper;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CardioSummaryFragment extends Fragment {
    private MainActivity mContext;
    private View mView;
    private int[] mStars = {R.id.cardio_summary_star_1, R.id.cardio_summary_star_2, R.id.cardio_summary_star_3, R.id.cardio_summary_star_4, R.id.cardio_summary_star_5};
    private int mColor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_cardio_summary_title));

        mView = inflater.inflate(R.layout.fragment_cardio_summary, container, false);

        Bundle args = getArguments();

        long runningTime = args.getLong("running-time");
        TextView timeView = (TextView) mView.findViewById(R.id.cardio_summary_time_value);
        timeView.setText(DataConversionHelper.convertTime(runningTime));

        float runningDistance = args.getFloat("running-distance");
        TextView distanceView = (TextView) mView.findViewById(R.id.cardio_summary_distance_value);
        distanceView.setText(DataConversionHelper.convertDistance(runningDistance));

        float runningPace = args.getFloat("running-pace");
        TextView paceView = (TextView) mView.findViewById(R.id.cardio_summary_pace_value);
        paceView.setText(DataConversionHelper.convertPace(runningPace));

        saveStats(runningTime, runningDistance, runningPace);

        int style = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_theme_key, R.style.CyanAccentColorTheme);
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

        FloatingActionButton fab = (FloatingActionButton) mView.findViewById(R.id.cardio_summary_fab_finished);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardioFragment fragment = new CardioFragment();
                mContext.replaceFragment(fragment);
            }
        });

        return mView;
    }

    private void colorStars(int n) {
        LinearLayout stars = (LinearLayout) mView.findViewById(R.id.cardio_summary_rate);
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

    private void saveStats(long time, float distance, float pace) {
        /* Dane ostatniego biegu */
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_last_run_date_key, dateFormat.format(new Date()));
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_last_run_duration_key, time);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_last_run_distance_key, distance);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_last_run_pace_key, pace);

        /* Najlepsze dane */
        if (SharedPreferencesHelper.getLong(mContext, SharedPreferencesHelper.profile_stats_longest_run_duration_key, 0) < time)
            SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_longest_run_duration_key, time);
        if (SharedPreferencesHelper.getFloat(mContext, SharedPreferencesHelper.profile_stats_longest_run_distance_key, 0) < distance)
            SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_longest_run_distance_key, distance);
        if (SharedPreferencesHelper.getFloat(mContext, SharedPreferencesHelper.profile_stats_biggest_run_pace_key, Float.MAX_VALUE) > pace)
            SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_biggest_run_pace_key, pace);

        /* Srednie dane */
        int run_numbers = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.profile_stats_run_number_key, 0);

        long last_average_time = SharedPreferencesHelper.getLong(mContext, SharedPreferencesHelper.profile_stats_average_run_duration_key, 0);
        long average_time = ((last_average_time * run_numbers) + time) / (run_numbers + 1);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_average_run_duration_key, average_time);

        float last_average_distance = SharedPreferencesHelper.getFloat(mContext, SharedPreferencesHelper.profile_stats_average_run_distance_key, 0);
        float average_distance = ((last_average_distance * run_numbers) + distance) / (run_numbers + 1);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_average_run_distance_key, average_distance);

        float last_average_pace = SharedPreferencesHelper.getFloat(mContext, SharedPreferencesHelper.profile_stats_average_run_pace_key, 0);
        float average_pace = ((last_average_pace * run_numbers) + pace) / (run_numbers + 1);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_average_run_pace_key, average_pace);

        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.profile_stats_run_number_key, run_numbers + 1);
    }
}
