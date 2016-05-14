package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maxwellsport.maxwellsportapp.R;

//TODO: Automatyczne pobieranie pozycji przez gps, Dodanie google services
public class CardioFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private MapView mMapView;
    private String mStatus;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cardio, container, false);
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        if (savedInstanceState != null) {
            mStatus = savedInstanceState.getString("status");
            setupFabView();
        } else {
            mStatus = "stopped";
        }

        setupFabListeners();

        return mView;
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("status", mStatus);
        mMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

        LatLng latLng = new LatLng(52.221450, 21.006319);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in PW"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
    }

    private void setupFabListeners() {
        FloatingActionButton fab_start = (FloatingActionButton) mView.findViewById(R.id.fab_start);
        fab_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatus.equals("stopped")) {
                    mStatus = "running";
                    setupFabView();
                }
            }
        });

        FloatingActionButton fab_stop = (FloatingActionButton) mView.findViewById(R.id.fab_stop);
        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatus.equals("running")) {
                    mStatus = "stopped";
                    setupFabView();
                }
            }
        });

        FloatingActionButton fab_pause = (FloatingActionButton) mView.findViewById(R.id.fab_pause);
        fab_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mStatus.equals("running")) {
                    mStatus = "paused";
                    setupFabView();
                } else if (mStatus.equals("paused")) {
                    mStatus = "running";
                    setupFabView();
                }
            }
        });
    }

    private void setupFabView() {
        FloatingActionButton fab;
        switch (mStatus) {
            case "stopped":
                mView.findViewById(R.id.running_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.stopped_layout).setVisibility(View.VISIBLE);
                break;
            case "running":
                mView.findViewById(R.id.stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.running_layout).findViewById(R.id.fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_pause));
                break;
            case "paused":
                mView.findViewById(R.id.stopped_layout).setVisibility(View.INVISIBLE);
                mView.findViewById(R.id.running_layout).setVisibility(View.VISIBLE);
                fab = (FloatingActionButton) mView.findViewById(R.id.running_layout).findViewById(R.id.fab_pause);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_fab_start));
                break;
        }
    }
}
