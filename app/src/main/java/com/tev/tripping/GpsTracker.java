package com.tev.tripping;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;


public class GpsTracker implements LocationListener {
    private static GpsTracker instance = null;
    private Context context;

    public GpsTracker(){
    }

    public static GpsTracker getInstance(){
        if(instance == null){
            instance = new GpsTracker();
        }

        return instance;
    }

    public void setContext(Context c){
        context = c;
    }
    /**
     * This method is used to call the eventListener to find the coordinates of the user.
     * @return
     */
    @SuppressLint("MissingPermission")
    public Location getLocation(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(context, "Permission not granted", Toast.LENGTH_SHORT).show();
        }

        Location l = null;
        LocationManager lm = (LocationManager) (context.getSystemService(Context.LOCATION_SERVICE));

        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSEnabled){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 1, this);
            l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        } else{
            Toast.makeText(context, "Please enable GPS", Toast.LENGTH_LONG).show();
        }

        return l;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

