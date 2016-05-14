package com.maxwellsport.maxwellsportapp.fragments;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.maxwellsport.maxwellsportapp.R;

import java.util.Locale;

//TODO: Automatyczne pobieranie pozycji przez gps, Dodanie google services
public class CardioFragment extends Fragment implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {
    private View mView;
    private String mStatus;

    private MapView mMapView;
    private GoogleMap mMap;

    private GoogleApiClient mGoogleApiClient;

    private TextView mTimeView;
    private long mStartTime = 0;
    private long mDiffTime = 0;
    private Handler mTimerHandler = new Handler();
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            long time = (SystemClock.uptimeMillis() - mStartTime) + mDiffTime;
            setupTimerView(time);
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

    private void setupTimerView(long time) {
        long seconds = time / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        mTimeView.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cardio, container, false);

        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        mTimeView = (TextView) mView.findViewById(R.id.stats_layout).findViewById(R.id.timeView);

        if (savedInstanceState != null) {
            mStatus = savedInstanceState.getString("mStatus");
            mStartTime = savedInstanceState.getLong("mStartTime");
            mDiffTime = savedInstanceState.getLong("mDiffTime");
            setupFabView();
            setupTimerView(mDiffTime);
            if(mStatus.equals("running"))
                mTimerHandler.post(mTimerRunnable);

        } else {
            mStatus = "stopped";
        }

        setupFabListeners();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        return mView;
    }

    /*
     * Fragment lifecycle methods
     */
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("mStartTime", mStartTime);
        savedInstanceState.putLong("mDiffTime", mDiffTime);
        savedInstanceState.putString("mStatus", mStatus);
        mMapView.onSaveInstanceState(savedInstanceState);
        super.onSaveInstanceState(savedInstanceState);
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
        mMap.setPadding(0, 48, 0, 0);
    }

    /*
     * Fragment private setup methods
     */
    private void setupFabListeners() {
        FloatingActionButton fab_start = (FloatingActionButton) mView.findViewById(R.id.fab_start);
        fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatus = "running";
                setupFabView();
                startTimer();
            }
        });

        FloatingActionButton fab_stop = (FloatingActionButton) mView.findViewById(R.id.fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatus = "stopped";
                setupFabView();
                stopTimer();
            }
        });

        FloatingActionButton fab_pause = (FloatingActionButton) mView.findViewById(R.id.fab_pause);
        fab_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatus.equals("running")) {
                    mStatus = "paused";
                    setupFabView();
                    pauseTimer();
                } else if (mStatus.equals("paused")) {
                    mStatus = "running";
                    setupFabView();
                    startTimer();
                }
            }
        });
    }

    private void setupFabView() {
        FloatingActionButton fab;
        switch (mStatus) {
            case "stopped":
                mView.findViewById(R.id.stats_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.stopped_layout).setVisibility(View.VISIBLE);
                mTimeView.setText("00:00");
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

    /*
     * Localization services
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
