package com.maxwellsport.maxwellsportapp.services;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.maxwellsport.maxwellsportapp.fragments.CardioFragment;

import java.util.Locale;

public class LocationUpdateService implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    private CardioFragment mCardioFragment;
    private static final String TAG = "MaxwellSport";

    /* Ustawienie interwałów odswieżania pozycji uzytkownika */
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 2000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;

    /* Klucze do zapisania wartości w Bundle Fragmentu */
    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String LOCATION_KEY = "location-key";

    /* Wartosci do zapisania w Bundle */
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    public LocationUpdateService(CardioFragment cardioFragment) {
        mCardioFragment = cardioFragment;
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        Log.i("MaxwellSport", "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(mCardioFragment.getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        Log.i("MaxwellSport", "Creating Location Request");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startLocationUpdates() {
        Log.i("MaxwellSport", "Started Location Updates");
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdates() {
        Log.i("MaxwellSport", "Stopped Location Updates");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void startUpdatesButtonHandler() {
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            startLocationUpdates();
        }
    }

    public void stopUpdatesButtonHandler() {
        if (mRequestingLocationUpdates) {
            mRequestingLocationUpdates = false;
            stopLocationUpdates();
        }
    }

    /* Metody interfejsów ConnectionCallbacks, OnConnectionFailedListener */
    @Override
    public void onConnected(Bundle bundle) {
        Log.i("MaxwellSport", "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("MaxwellSport", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("MaxwellSport", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        Toast.makeText(mCardioFragment.getActivity(), String.format(Locale.getDefault(), "Lat: %f Lng: %f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), Toast.LENGTH_SHORT).show();
    }

    /*
     * Metody sluzace do polaczenia Location update service z Cardio Fragment life cycle
     */
    public void onStart() {
        mGoogleApiClient.connect();
    }

    public void onResume() {
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void onDestroy() {
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
        mGoogleApiClient.disconnect();
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mRequestingLocationUpdates = savedInstanceState.getBoolean(REQUESTING_LOCATION_UPDATES_KEY);
            mCurrentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
        } else {
            mRequestingLocationUpdates = false;
            mCurrentLocation = null;
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
    }
}
