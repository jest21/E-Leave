package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 18-01-2017.
 */
public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.login);
        Button btapplyleave = (Button) findViewById(R.id.btapplyleave);
        btapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Leaveapplication.class);
                startActivity(intent);
            }

        });
        Button btleavedetails = (Button) findViewById(R.id.btleavedetails);
        btleavedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Leavedetails.class);
                startActivity(intent);
            }
        });
        Button btsalarydetails = (Button) findViewById(R.id.btsalarydetails);
        btsalarydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Salarydetails.class);
                startActivity(intent);
                finish();
            }
        });

    }


    }

