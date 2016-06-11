package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.services.DataConversionService;
import com.maxwellsport.maxwellsportapp.services.SharedPreferencesService;

import java.util.Locale;

// TODO: zachowac stan pól tekstowych ze statystykami po zmianie konfiguracji

public class ProfileFragment extends Fragment {
    MainActivity mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_profile_title));

        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        ImageView trainingForm = (ImageView) v.findViewById(R.id.profile_training_form_value);
        trainingForm.setColorFilter(getResources().getColor(R.color.material_blue_500), PorterDuff.Mode.SRC_ATOP);

        /* Cardio Part */
        TextView lastRunDate = (TextView) v.findViewById(R.id.profile_last_run_date_value);
        lastRunDate.setText(SharedPreferencesService.getString(mContext, SharedPreferencesService.profile_stats_last_run_date_key, "-"));

        TextView lastRunDuration = (TextView) v.findViewById(R.id.profile_last_run_duration_value);
        lastRunDuration.setText(DataConversionService.convertTime(SharedPreferencesService.getLong(mContext, SharedPreferencesService.profile_stats_last_run_duration_key, 0)));

        TextView averageRunDuration = (TextView) v.findViewById(R.id.profile_average_run_duration_value);
        averageRunDuration.setText(DataConversionService.convertTime(SharedPreferencesService.getLong(mContext, SharedPreferencesService.profile_stats_average_run_duration_key, 0)));

        TextView longestRunDuration = (TextView) v.findViewById(R.id.profile_longest_run_duration_value);
        longestRunDuration.setText(DataConversionService.convertTime(SharedPreferencesService.getLong(mContext, SharedPreferencesService.profile_stats_longest_run_duration_key, 0)));

        TextView lastRunDistance = (TextView) v.findViewById(R.id.profile_last_run_distance_value);
        lastRunDistance.setText(DataConversionService.convertDistance(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_last_run_distance_key, 0)));

        TextView averageRunDistance = (TextView) v.findViewById(R.id.profile_average_run_distance_value);
        averageRunDistance.setText(DataConversionService.convertDistance(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_average_run_distance_key, 0)));

        TextView longestRunDistance = (TextView) v.findViewById(R.id.profile_longest_run_distance_value);
        longestRunDistance.setText(DataConversionService.convertDistance(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_longest_run_distance_key, 0)));

        TextView lastRunPace = (TextView) v.findViewById(R.id.profile_last_run_pace_value);
        lastRunPace.setText(DataConversionService.convertPace(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_last_run_pace_key, 0)));

        TextView averageRunPace = (TextView) v.findViewById(R.id.profile_average_run_pace_value);
        averageRunPace.setText(DataConversionService.convertPace(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_average_run_pace_key, 0)));

        TextView biggestRunPace = (TextView) v.findViewById(R.id.profile_biggest_run_pace_value);
        biggestRunPace.setText(DataConversionService.convertPace(SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_biggest_run_pace_key, 0)));

        /* Cardio Form*/
        ImageView runningForm = (ImageView) v.findViewById(R.id.profile_running_form_value);
        float lastRunPaceValue = SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_last_run_pace_key, 0);
        float averageRunPaceValue = SharedPreferencesService.getFloat(mContext, SharedPreferencesService.profile_stats_average_run_pace_key, 0);
        float diff = averageRunPaceValue - lastRunPaceValue;
        if (diff > 0.30) {
            runningForm.setImageDrawable(getResources().getDrawable(R.drawable.ic_progress_up));
            runningForm.setColorFilter(getResources().getColor(R.color.material_green_500), PorterDuff.Mode.SRC_ATOP);
        } else if (diff < -0.30) {
            runningForm.setImageDrawable(getResources().getDrawable(R.drawable.ic_progress_down));
            runningForm.setColorFilter(getResources().getColor(R.color.material_red_500), PorterDuff.Mode.SRC_ATOP);
        } else {
            runningForm.setImageDrawable(getResources().getDrawable(R.drawable.ic_progress_flat));
            runningForm.setColorFilter(getResources().getColor(R.color.material_blue_500), PorterDuff.Mode.SRC_ATOP);
        }


        return v;
    }
}
