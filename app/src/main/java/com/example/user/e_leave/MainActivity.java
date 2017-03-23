package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by User on 18-01-2017.
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main);
        Button btapplyleave = (Button) findViewById(R.id.btapplyleave);
        btapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Leaveapplication.class);
                startActivity(intent);
            }

        });
        Button btleavedetails = (Button) findViewById(R.id.btleavedetails);
        btleavedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Leavedetails.class);
                startActivity(intent);
            }
        });
        Button btsalarydetails = (Button) findViewById(R.id.btsalarydetails);
        btsalarydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Salarydetails.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    public void logout(MenuItem item) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,Login.class));
        finish();
    }
}

