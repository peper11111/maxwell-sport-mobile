package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.services.LocationUpdateService;

import java.util.Locale;

public class CardioFragment extends Fragment implements OnMapReadyCallback {
    // Klucze do zapisania potrzebnych wartosci do Bundle
    protected final static String STATUS_KEY = "status-key";
    protected final static String START_TIME_KEY = "start-time-key";
    protected final static String DIFF_TIME_KEY = "diff-time-key";

    public String status;
    private long mStartTime;
    private long mDiffTime;

    private View mView;
    private LocationUpdateService mLocationUpdateService;
    private MapView mMapView;
    private GoogleMap mMap;

    private TextView mTimeView;

    private Handler mTimerHandler = new Handler();
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long time = (SystemClock.uptimeMillis() - mStartTime) + mDiffTime;
            setupStatsView(time);
            mTimerHandler.post(mTimerRunnable);
        }
    };

    /*
     * Timer functions
     */
    private void startTimer() {
        mStartTime = SystemClock.uptimeMillis();
        mTimerHandler.post(mTimerRunnable);
    }

    private void stopTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mDiffTime = 0;
    }

    private void pauseTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
        mDiffTime += SystemClock.uptimeMillis() - mStartTime;
    }

    private void setupStatsView(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long hours = minutes / 60;
        minutes = minutes % 60;
        mTimeView.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Przygotowanie widoku */
        mView = inflater.inflate(R.layout.fragment_cardio, container, false);

        /* Przygotowanie mapy */
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        /* Przygotowanie LocationUpdateService*/
        mLocationUpdateService = new LocationUpdateService(this);
        onRestoreInstanceState(savedInstanceState);

        mTimeView = (TextView) mView.findViewById(R.id.stats_layout).findViewById(R.id.timeView);
        /* Setup widoku przycisków i timera */
        setupMapView();
        if (status.equals("running"))
            mTimerHandler.post(mTimerRunnable);
        else if (status.equals("paused"))
            setupStatsView(mDiffTime);

        setupFabListeners();

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((LinearLayout) mView.findViewById(R.id.running_layout)).setOrientation(LinearLayout.HORIZONTAL);
        }
        return mView;
    }

    /*
     * Fragment lifecycle methods
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(DIFF_TIME_KEY, mDiffTime);
        savedInstanceState.putLong(START_TIME_KEY, mStartTime);
        savedInstanceState.putString(STATUS_KEY, status);
        mLocationUpdateService.onSaveInstanceState(savedInstanceState);
        mMapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    /* Pomocnicza metoda do wczytania danych z Bundle */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mLocationUpdateService.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            status = savedInstanceState.getString(STATUS_KEY);
            mStartTime = savedInstanceState.getLong(START_TIME_KEY);
            mDiffTime = savedInstanceState.getLong(DIFF_TIME_KEY);
        } else {
            status = "stopped";
            mStartTime = 0;
            mDiffTime = 0;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mLocationUpdateService.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        mLocationUpdateService.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mLocationUpdateService.onDestroy();
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0, 80, 0, 0);
    }

    /*
     * Fragment private setup methods
     */
    private void setupFabListeners() {
        FloatingActionButton fab_start = (FloatingActionButton) mView.findViewById(R.id.fab_start);
        fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "running";
                setupMapView();
                startTimer();
                mLocationUpdateService.startUpdatesButtonHandler();
            }
        });

        FloatingActionButton fab_stop = (FloatingActionButton) mView.findViewById(R.id.fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "stopped";
                setupMapView();
                stopTimer();
                mLocationUpdateService.stopUpdatesButtonHandler();
            }
        });

        FloatingActionButton fab_pause = (FloatingActionButton) mView.findViewById(R.id.fab_pause);
        fab_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("running")) {
                    status = "paused";
                    setupMapView();
                    pauseTimer();
                    mLocationUpdateService.stopUpdatesButtonHandler();
                } else if (status.equals("paused")) {
                    status = "running";
                    setupMapView();
                    startTimer();
                    mLocationUpdateService.startUpdatesButtonHandler();
                }
            }
        });
    }

    private void setupMapView() {
        FloatingActionButton fab;
        switch (status) {
            case "stopped":
                mView.findViewById(R.id.stats_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.stopped_layout).setVisibility(View.VISIBLE);
                mTimeView.setText(getResources().getString(R.string.time_value));
                break;
            case "running":
                mView.findViewById(R.id.stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.stats_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.running_layout).findViewById(R.id.fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_pause));
                break;
            case "paused":
                mView.findViewById(R.id.stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.stats_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.running_layout).findViewById(R.id.fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_start));
                break;
        }
    }
}
