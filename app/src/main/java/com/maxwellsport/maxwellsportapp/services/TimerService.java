package com.maxwellsport.maxwellsportapp.services;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import com.maxwellsport.maxwellsportapp.R;

import java.util.Locale;

public class TimerService {
    protected final static String START_TIME_KEY = "start-time-key";
    protected final static String DIFF_TIME_KEY = "diff-time-key";

    private long mStartTime;
    private long mDiffTime;

    private TextView mTimerView;
    private Handler mTimerHandler;
    private Context mContext;

    public TimerService(Context context, TextView timerView) {
        mContext = context;
        mTimerHandler = new Handler();
        mTimerView = timerView;
    }

    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long time = (SystemClock.uptimeMillis() - mStartTime) + mDiffTime;
            setupStatsView(time);
            mTimerHandler.post(mTimerRunnable);
        }
    };

    public void startTimer() {
        mStartTime = SystemClock.uptimeMillis();
        mTimerHandler.post(mTimerRunnable);
    }

    public void stopTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mDiffTime = 0;
        mTimerView.setText(mContext.getResources().getString(R.string.default_timer_value));
    }

    public void pauseTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mDiffTime += SystemClock.uptimeMillis() - mStartTime;
    }

    private void setupStatsView(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        mTimerView.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mStartTime = savedInstanceState.getLong(START_TIME_KEY);
            mDiffTime = savedInstanceState.getLong(DIFF_TIME_KEY);
        } else {
            mStartTime = 0;
            mDiffTime = 0;
        }
    }

    public void setupTimerService(String status) {
        if (status.equals("running"))
            mTimerHandler.post(mTimerRunnable);
        else if (status.equals("paused"))
            setupStatsView(mDiffTime);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(DIFF_TIME_KEY, mDiffTime);
        savedInstanceState.putLong(START_TIME_KEY, mStartTime);
    }
}
