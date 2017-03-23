package com.example.user.e_leave;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SalaryDetails extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salarydetails);
        Button btsalaryok= (Button) findViewById(R.id.btsalaryok);
        btsalaryok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SalaryDetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
