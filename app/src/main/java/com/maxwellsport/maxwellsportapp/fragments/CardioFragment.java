package com.maxwellsport.maxwellsportapp.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.ConnectionHelper;
import com.maxwellsport.maxwellsportapp.helpers.DataConversionHelper;
import com.maxwellsport.maxwellsportapp.services.LocationUpdateService;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;
import com.maxwellsport.maxwellsportapp.helpers.TimerHelper;

import java.util.ArrayList;

public class CardioFragment extends Fragment implements OnMapReadyCallback {
    private MainActivity mContext;
    // Klucze do zapisania potrzebnych wartosci do Bundle
    protected final static String STATUS_KEY = "status-key";

    public String status;

    private View mView;
    private LocationUpdateReceiver mLocationUpdateReceiver;
    private TimerHelper mTimerHelper;
    private int mColor;
    private MapView mMapView;
    private GoogleMap mMap;
    private Polyline mPolyline;
    private ArrayList<ArrayList<LatLng>> mPolylinePoints;
    private ArrayList<LatLng> mPolylinePart;

    /* Pola do obliczania i wyswietlania dystansu */
    private Location mLastLocation;
    private float mDistance;
    private TextView mDistanceView;
    private float mPace;
    private TextView mPaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_cardio_title));

        int style = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_theme_key, R.style.CyanAccentColorTheme);
        int[] attr = {R.attr.colorAccent};
        TypedArray array = mContext.obtainStyledAttributes(style, attr);
        mColor = array.getColor(0, Color.WHITE);
        array.recycle();

        /* Przygotowanie widoku */
        mView = inflater.inflate(R.layout.fragment_cardio, container, false);

        /* Przygotowanie mapy */
        mMapView = (MapView) mView.findViewById(R.id.cardio_map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        /* Przygotowanie LocationUpdateService*/
        mTimerHelper = new TimerHelper((TextView) mView.findViewById(R.id.cardio_timer_view));
        onRestoreInstanceState(savedInstanceState);

        mDistanceView = (TextView) mView.findViewById(R.id.cardio_distance_view);
        mPaceView = (TextView) mView.findViewById(R.id.cardio_pace_view);

        /* Setup widoku przycisków i timera */
        setupMapView();
        mTimerHelper.setupTimerService(status);
        setupFabListeners();

        return mView;
    }

    private class LocationUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUserPosition((Location) intent.getParcelableExtra("location"));
        }
    }

    /*
     * Fragment lifecycle methods
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(STATUS_KEY, status);
        mTimerHelper.onSaveInstanceState(savedInstanceState);
        mMapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
    }

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
    public void onStart() {
        super.onStart();
        mLocationUpdateReceiver = new LocationUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LocationUpdateService.UPDATE_MAP_POSITION);
        mContext.registerReceiver(mLocationUpdateReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mContext.unregisterReceiver(mLocationUpdateReceiver);
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
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
        mMap.setPadding(0, 0, 0, 0);
    }

    /*
     * Fragment private setup methods
     */
    private void setupNewPolyline() {
        /* Tworzy nową polyline */
        mDistance = 0;
        mPolylinePoints = new ArrayList<>();
        setupNewPolylinePart();
    }

    private void setupNewPolylinePart() {
        /* Tworzy nową częśś wczesniej stworzononej lini */
        mPolyline = mMap.addPolyline(new PolylineOptions().width(15).color(mColor));
        mPolylinePart = new ArrayList<>();
        mPolylinePoints.add(mPolylinePart);
        mLastLocation = null;
    }

    private void setupFabListeners() {
        FloatingActionButton fab_start = (FloatingActionButton) mView.findViewById(R.id.cardio_fab_start);
        fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "running";
                mMap.setPadding(0, 80, 0, 0);
                setupMapView();
                setupNewPolyline();
                mTimerHelper.startTimer();
                mContext.startService(new Intent(mContext.getBaseContext(), LocationUpdateService.class));
            }
        });

        FloatingActionButton fab_stop = (FloatingActionButton) mView.findViewById(R.id.cardio_fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status = "stopped";
                mContext.stopService(new Intent(mContext.getBaseContext(), LocationUpdateService.class));
                mTimerHelper.stopTimer();
                setupMapView();
                mMap.setPadding(0, 0, 0, 0);

                summary();
            }
        });

        FloatingActionButton fab_pause = (FloatingActionButton) mView.findViewById(R.id.cardio_fab_pause);
        fab_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals("running")) {
                    status = "paused";
                    setupMapView();
                    mTimerHelper.pauseTimer();
                } else if (status.equals("paused")) {
                    status = "running";
                    setupMapView();
                    setupNewPolylinePart();
                    mTimerHelper.startTimer();
                }
            }
        });
    }

    private void setupMapView() {
        FloatingActionButton fab;
        switch (status) {
            case "stopped":
                mView.findViewById(R.id.cardio_stats_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.cardio_running_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.cardio_stopped_layout).setVisibility(View.VISIBLE);
                break;
            case "running":
                mView.findViewById(R.id.cardio_stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.cardio_running_layout).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.cardio_stats_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.cardio_running_layout).findViewById(R.id.cardio_fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_pause));
                break;
            case "paused":
                mView.findViewById(R.id.cardio_stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.cardio_running_layout).setVisibility(View.VISIBLE);
                mView.findViewById(R.id.cardio_stats_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.cardio_running_layout).findViewById(R.id.cardio_fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_start));
                break;
        }
    }

    private void summary() {
        Log.d("MAXWELL", "" + mPolylinePoints.get(0).size());
        Bundle bundle = new Bundle();
        bundle.putLong("running-time", mTimerHelper.getTimerTime());
        bundle.putFloat("running-distance", mDistance);
        bundle.putFloat("running-pace", mPace);

        /* send run data to server */
        ConnectionHelper mConnectionHelper = new ConnectionHelper(getActivity());
        mConnectionHelper.saveRunData(mTimerHelper.getTimerTime(), mDistance, mPace, mPolylinePoints);

        Fragment fragment = new CardioSummaryFragment();
        fragment.setArguments(bundle);
        mContext.addFragment(fragment);
    }

    //TODO: Naprawic rysowanie trasy w tle
    public void updateUserPosition(Location location) {
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17));

        if (status.equals("running")) {
            if (mLastLocation != null) {
                mDistance += mLastLocation.distanceTo(location);
                mDistanceView.setText(DataConversionHelper.convertDistance(mDistance));
                mPace = countPace(mTimerHelper.getTimerTime(), mDistance);
                mPaceView.setText(DataConversionHelper.convertPace(mPace));
            }
            mPolylinePart.add(position);
            mPolyline.setPoints(mPolylinePart);
            mLastLocation = location;
        }
    }

    private float countPace(long time, float distance) {
        float seconds = time / 1000;
        float minutes = seconds / 60;
        float kilometers = distance / 1000;
        float pace = minutes / kilometers;

        if (time != 0 && distance != 0)
            return pace;
        else
            return 0;
    }
}
