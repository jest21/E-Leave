package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 18-01-2017.
 */
public class Leavedetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavedetails);
        Button btdetailsok= (Button) findViewById(R.id.btdetailsok);
        btdetailsok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Leavedetails.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
