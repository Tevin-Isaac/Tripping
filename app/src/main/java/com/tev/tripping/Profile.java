package com.tev.tripping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView uname,uemail;
    private FirebaseDatabase mydb;
    private DatabaseReference  mydbref;
    private ImageView uphoto;
    private String id;
    private String themail, thename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth =FirebaseAuth.getInstance();
        uname = findViewById(R.id.myname);
        uemail = findViewById(R.id.myemail);
        mydb = FirebaseDatabase.getInstance();
        mydbref = mydb.getReference();
        FirebaseUser theuser = mAuth.getCurrentUser();
        id = theuser.getUid();
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
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(Profile.this,"Logout successfully",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;

    }


}
