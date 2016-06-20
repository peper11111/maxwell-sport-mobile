package com.maxwellsport.maxwellsportapp.fragments;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.adapters.TrainingDayListAdapter;
import com.maxwellsport.maxwellsportapp.helpers.ConnectionHelper;
import com.maxwellsport.maxwellsportapp.helpers.JSONParserHelper;
import com.maxwellsport.maxwellsportapp.helpers.TimerHelper;
import com.maxwellsport.maxwellsportapp.models.ExerciseModel;

import java.util.ArrayList;

public class TrainingDayFragment extends Fragment {
    private MainActivity mContext;
    private FloatingActionButton mPauseButton;
    private FloatingActionButton mStopButton;

    /* time count */
    protected final static String STATUS_KEY = "status-key";
    public String status;

    private TimerHelper mTimerHelper;
    private ListView mListView;
    private TrainingDayListAdapter mAdapter;

    // TODO: Pobrac liste z adaptera i przekazac do statystyk
    /* Lista skonczonych cwiczen */
    private ArrayList<Integer> mpositionList;

    /* Lista cwiczen */
    private ArrayList<ExerciseModel> mExerciseList;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_training_day_title));

        View v = inflater.inflate(R.layout.fragment_training_day, container, false);
        /* Inicjalizacja widoków */
        mListView = (ListView) v.findViewById(R.id.training_list_view);
        mPauseButton = (FloatingActionButton) v.findViewById(R.id.training_fab_pause);
        mStopButton = (FloatingActionButton) v.findViewById(R.id.training_fab_stop);


        /* Pobranie listy cwiczen */
        mExerciseList = new JSONParserHelper(mContext).getExerciseListForCurrentTraining();

        /* Ustawienie adaptera */
        mAdapter = new TrainingDayListAdapter(mContext, mExerciseList);
        mListView.setAdapter(mAdapter);

        /* Ustawienie timera do przycisków */
        mTimerHelper = new TimerHelper((TextView) v.findViewById(R.id.training_timer_view));
        onRestoreInstanceState(savedInstanceState);
        status = "running";
        mTimerHelper.setupTimerService(status);
        setupTimeButtonListeners();
        mTimerHelper.startTimer();

        return v;
    }

    private void setupTimeButtonListeners() {
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "stopped";
                mTimerHelper.stopTimer();
                mPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_pause));
                summary();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.equals("running")) {
                    status = "paused";
                    mTimerHelper.pauseTimer();
                    mPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_start));
                } else if (status.equals("paused")) {
                    status = "running";
                    mTimerHelper.startTimer();
                    mPauseButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_pause));
                }
            }
        });
    }

    private void summary(){
        int counter = 0;
        for (int positon: mAdapter.getPositionList()
             ) {
            if(positon != 0)
                counter++;
        }

        long trainingTime = mTimerHelper.getTimerTime();
        Bundle bundle = new Bundle();
        bundle.putLong("training-time", trainingTime);
        bundle.putInt("exercise-count", counter);
        TrainingSummaryFragment fragment = new TrainingSummaryFragment();
        fragment.setArguments(bundle);
        mContext.addFragment(fragment);
    }

    /*
     * Fragment lifecycle methods
     */

    /* Pomocnicza metoda do wczytania danych z Bundle */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mTimerHelper.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            status = savedInstanceState.getString(STATUS_KEY);
        } else {
            status = "stopped";
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mTimerHelper.onSaveInstanceState(outState);
        /* Zapisanie pozycji listy */
        int index = mListView.getFirstVisiblePosition();
        View v = mListView.getChildAt(0);
        int top = (v == null) ? 0 : v.getTop();

        outState.putIntegerArrayList("positionList", mAdapter.getPositionList());
        outState.putInt("index", index);
        outState.putInt("top", top);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int index = -1;
        int top = 0;
        if (savedInstanceState != null) {
            /* Przywrocenie poprzedniego stanu (pozycii) listy */
            index = savedInstanceState.getInt("index", -1);
            top = savedInstanceState.getInt("top", 0);
            mAdapter.setPositionList(savedInstanceState.getIntegerArrayList("positionList"));
        }
        if (index != -1) {
            mListView.setSelectionFromTop(index, top);
        }
    }
}
