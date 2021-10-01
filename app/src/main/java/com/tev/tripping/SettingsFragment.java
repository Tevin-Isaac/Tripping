package com.tev.tripping;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment {
    //for database usage
    private FirebaseAuth mAuth;
    private TextView uname,uemail;
    private FirebaseDatabase mydb;
    private DatabaseReference mydbref;
    private ImageView uphoto;
    private String id;
    private String themail, thename;
    private View view;

    //for camera usage
    ImageView imageView;
    Button btnPic;
    Button btnPho;
    Uri image_uri;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_settings, null);
        return this.view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Load user data from database
        retriveUserData(view, savedInstanceState);

        //prepare to use camera if needed
        imageView = getView().findViewById(R.id.imageView5);
        btnPic = getView().findViewById(R.id.button_from_file);
        btnPho =getView().findViewById(R.id.button_take_pic);

        btnPho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED||
                            ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        openCamera();
                    }
                }
                else{
                    openCamera();
                }
            }
        });

        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED){
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        pickImageFromGallery();
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        image_uri = getActivity().getApplicationContext()
                .getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, PERMISSION_CODE);
    }

    private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            imageView.setImageURI(data.getData());
        }
        else if(resultCode == RESULT_OK){
            imageView.setImageURI(image_uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(getContext(), "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void retriveUserData(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        uname =getView().findViewById(R.id.myname);
        uemail = getView().findViewById(R.id.myemail);
        mydb = FirebaseDatabase.getInstance();
        mydbref = mydb.getReference();
        FirebaseUser theuser = mAuth.getCurrentUser();
        if(theuser != null) {
            id = theuser.getUid();
        }
        themail = theuser.getEmail();
        uemail.setText(themail);
        mydbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if(ds.getValue()!=null) {
                        Myusers myu = new Myusers();
                        //Log.d("myu",ds.getValue().toString());
                        myu.setUserName(ds.child(id).getValue(Myusers.class).getUserName());
                        myu.setUserEmail(ds.child(id).getValue(Myusers.class).getUserEmail());
                        myu.setUserId(ds.child(id).getValue(Myusers.class).getUserId());
                        //Log.d("1234",ds.child(id).getValue(Myusers.class).toString());
                        if (myu.getUserName() != null && myu.getUserEmail().toLowerCase().equals(themail)) {
                            uname.setText(myu.getUserName());
                        }
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        Toolbar toolbar = getView().findViewById(R.id.mytoolbar);
        ( ((AppCompatActivity) getActivity())).setSupportActionBar(toolbar);
    }
}