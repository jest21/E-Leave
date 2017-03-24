package com.example.user.e_leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LeaveDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavedetails);
        Button btdetailsok= (Button) findViewById(R.id.btdetailsok);
        btdetailsok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LeaveDetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
