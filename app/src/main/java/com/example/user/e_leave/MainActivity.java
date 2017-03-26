package com.example.user.e_leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
        setContentView(R.layout.activity_main);
        Button btapplyleave = (Button) findViewById(R.id.btapplyleave);
        btapplyleave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaveApplication.class);
                startActivity(intent);
            }

        });
        Button btleavedetails = (Button) findViewById(R.id.btleavedetails);
        btleavedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LeaveDetails.class);
                startActivity(intent);
            }
        });
        Button btsalarydetails = (Button) findViewById(R.id.btsalarydetails);
        btsalarydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SalaryDetails.class);
                startActivity(intent);
            }
        });

        if (!FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(CurrentUser.getEmail(this))){
            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            CurrentUser.setEmail(MainActivity.this,dataSnapshot.child("email").getValue().toString());
                            CurrentUser.setFacultyId(MainActivity.this,dataSnapshot.child("faculty_id").getValue().toString());
                            CurrentUser.setDesignation(MainActivity.this,dataSnapshot.child("designation").getValue().toString());
                            CurrentUser.setName(MainActivity.this,dataSnapshot.child("name").getValue().toString());
                            Toast.makeText(MainActivity.this, "Welcome, " + CurrentUser.getName(MainActivity.this), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }

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

    public void helpActivity(MenuItem item) {
        startActivity(new Intent(this,SendHelp.class));
    }
}

