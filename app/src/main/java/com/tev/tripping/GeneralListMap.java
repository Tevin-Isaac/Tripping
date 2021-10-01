package com.tev.tripping;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;


public class GeneralListMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ActivityCompat.requestPermissions(GeneralListMap.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        Log.d("Mayank", "Inside the map");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *
     * This method reads all the places values from the formed list and put it on the map for the user
     * to see.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ApiController api = ApiController.getInstance();
        ArrayList<Place> places = api.getApiList();

        // Marking the places of the user.
        for(Place p: places){
            MarkerOptions markerOptions = new MarkerOptions();

            String placeName = p.name;
            String vicinity = p.vicinity;
            double lat = p.latitutde;
            double lng = p.longitude;

            LatLng latLng = new LatLng( lat, lng);

            markerOptions.position(latLng);
            markerOptions.title("Name: "+ placeName + " | Rating: " + p.rating + " / " + p.totalRatings + "Address: "+ vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

        // Marking the current location
        MarkerOptions markerOptions = new MarkerOptions();
        GpsTracker gps = GpsTracker.getInstance();
        Location location = gps.getLocation();

        if(location != null){
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            LatLng latLng = new LatLng( lat, lng);

            markerOptions.position(latLng);
            markerOptions.title("Current Location");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

}
