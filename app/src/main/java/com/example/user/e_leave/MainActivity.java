package com.example.user.e_leave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edt_Username, edt_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt_Username = (EditText) findViewById(R.id.edt_Username);
        edt_Password = (EditText) findViewById(R.id.edt_Password);
        System.out.println("Hai");
        Button btlogin = (Button) findViewById(R.id.btlogin);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Username.getText().toString().trim(), edt_Password.getText().toString().trim());

            }
        });
        Button btnewuser = (Button) findViewById(R.id.btnewuser);
        btnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validation(String username, String password) {
        if (username.length() == 0 || password.length() == 0) {
            if (username.length() == 0) {
                edt_Username.setError("Field can't be blank.");
            }
            if (password.length() == 0) {
                edt_Password.setError("Field can't be blank.");
            }
        }else

            {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }
    }

