package com.tev.tripping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {
    private Button Joinbutton;
    private Button Loginbutton;
    LottieAnimationView lottie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottie=findViewById(R.id.lottie);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        ApiController apiController = ApiController.getInstance();
        GpsTracker gps = GpsTracker.getInstance();
        gps.setContext(getApplicationContext());

        Joinbutton = findViewById(R.id.Joinus);
        Loginbutton = findViewById(R.id.Login);
        Joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openjoinactivity();
            }
        });
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openloginactivity();
            }
        });
    }
    public void openjoinactivity(){
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    public void openloginactivity(){
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }


}
