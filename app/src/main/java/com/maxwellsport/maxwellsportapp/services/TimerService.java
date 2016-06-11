package com.maxwellsport.maxwellsportapp.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

public class TimerService {
    protected final static String START_TIME_KEY = "start-time-key";
    protected final static String DIFF_TIME_KEY = "diff-time-key";

    private long mTime;
    private long mStartTime;
    private long mDiffTime;

    private TextView mTimerView;
    private Handler mTimerHandler;

    private String mStatus;

    public TimerService(TextView timerView) {
        mTimerHandler = new Handler();
        mTimerView = timerView;
    }

    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            mTime = (SystemClock.uptimeMillis() - mStartTime) + mDiffTime;
            setupStatsView(mTime);
            mTimerHandler.post(mTimerRunnable);
        }
    };

    public void startTimer() {
        mStatus = "running";
        mStartTime = SystemClock.uptimeMillis();
        mTimerHandler.post(mTimerRunnable);
    }

    public void stopTimer() {
        if (mStatus.equals("running"))
            pauseTimer();
        mStatus = "stopped";
        mDiffTime = 0;
    }

    public void pauseTimer() {
        mStatus = "paused";
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mDiffTime += SystemClock.uptimeMillis() - mStartTime;
    }

    private void setupStatsView(long time) {
        mTimerView.setText(DataConversionService.convertTime(time));
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

    public long getTimerTime() {
        return mTime;
    }
}
