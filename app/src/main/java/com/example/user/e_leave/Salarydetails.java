package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 18-01-2017.
 */
public class Salarydetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salarydetails);
        Button btsalaryok= (Button) findViewById(R.id.btsalaryok);
        btsalaryok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Salarydetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
