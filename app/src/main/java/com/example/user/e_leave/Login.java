package com.example.user.e_leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText edt_Username, edt_Password;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edt_Username = (EditText) findViewById(R.id.edt_Username);
        edt_Password = (EditText) findViewById(R.id.edt_Password);
        System.out.println("Hai");
        Button btlogin = (Button) findViewById(R.id.btlogin);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Login", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation(edt_Username.getText().toString().trim(), edt_Password.getText().toString().trim());

            }
        });
        Button btnewuser = (Button) findViewById(R.id.btnewuser);
        btnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



