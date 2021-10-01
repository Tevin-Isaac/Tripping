package com.tev.tripping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private ImageButton back;
    EditText username,password;
    Button login;
    LottieAnimationView lottie;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lottie=findViewById(R.id.lottie);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back = findViewById(R.id.loginback);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.mylogin);
        mFirebaseAuth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backtomain();
            }
        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(Login.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Dashboard.class);
                    startActivity(i);
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    username.setError("Please enter email id");
                    username.requestFocus();
                }
                else  if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else  if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(Login.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else  if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Login.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Login.this,"Login Successfully ",Toast.LENGTH_SHORT).show();
                                Intent intTonext = new Intent(Login.this,Dashboard.class);
                                startActivity(intTonext);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    public void backtomain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

}
