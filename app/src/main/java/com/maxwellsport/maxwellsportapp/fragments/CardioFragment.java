package com.maxwellsport.maxwellsportapp.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
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
import com.maxwellsport.maxwellsportapp.services.TimerService;

public class CardioFragment extends Fragment implements OnMapReadyCallback {
    // Klucze do zapisania potrzebnych wartosci do Bundle
    protected final static String STATUS_KEY = "status-key";

    public String status;

    private View mView;
    private LocationUpdateService mLocationUpdateService;
    private TimerService mTimerService;
    private MapView mMapView;
    private GoogleMap mMap;

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
        mTimerService = new TimerService(getActivity(), (TextView) mView.findViewById(R.id.stats_layout).findViewById(R.id.timeView));
        onRestoreInstanceState(savedInstanceState);

        /* Setup widoku przycisków i timera */
        setupMapView();
        mTimerService.setupTimerService(status);
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
        savedInstanceState.putString(STATUS_KEY, status);
        mTimerService.onSaveInstanceState(savedInstanceState);
        mLocationUpdateService.onSaveInstanceState(savedInstanceState);
        mMapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

    /* Pomocnicza metoda do wczytania danych z Bundle */
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        mLocationUpdateService.onRestoreInstanceState(savedInstanceState);
        mTimerService.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            status = savedInstanceState.getString(STATUS_KEY);
        } else {
            status = "stopped";
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
                mTimerService.startTimer();
                mLocationUpdateService.startUpdatesButtonHandler();
            }
        });

        FloatingActionButton fab_stop = (FloatingActionButton) mView.findViewById(R.id.fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "stopped";
                setupMapView();
                mTimerService.stopTimer();
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
                    mTimerService.pauseTimer();
                    mLocationUpdateService.stopUpdatesButtonHandler();
                } else if (status.equals("paused")) {
                    status = "running";
                    setupMapView();
                    mTimerService.startTimer();
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
