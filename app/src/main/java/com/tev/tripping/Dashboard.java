package com.tev.tripping;

import android.location.Location;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import java.util.ArrayList;
import com.airbnb.lottie.LottieAnimationView;

public class Dashboard extends AppCompatActivity

        implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);

        // Initializing the list of places in app around the user.
        GpsTracker g = GpsTracker.getInstance();
        Location l = g.getLocation();

        if(l != null){
            double lat = l.getLatitude();
            double lon = l.getLongitude();
            ApiController apiController = ApiController.getInstance();
            apiController.setParam(lat, lon, 10000);
            ArrayList<Place> places = apiController.generateApiList();
        }
    }
    LottieAnimationView lottie;

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        lottie=findViewById(R.id.lottie);

        Fragment fragment = new SettingsFragment();

        switch(menuItem.getItemId()) {

            case R.id.navigation_find_trip:

                fragment = new FindTripFragment();
                break;

            case R.id.navigation_my_trip:
                fragment = new MyTripFragment();
                break;

            case R.id.navigation_settings:
                fragment = new SettingsFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
