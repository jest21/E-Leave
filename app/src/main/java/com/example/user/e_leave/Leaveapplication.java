package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 18-01-2017.
 */
public class Leaveapplication extends Activity{
    EditText edt_Facid,edt_Leavetype,edt_From,edt_To;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveapplication);
        edt_Facid = (EditText) findViewById(R.id.edt_Facid);
        edt_Leavetype = (EditText) findViewById(R.id.edt_Leavetype);
        edt_From = (EditText) findViewById(R.id.edt_From);
        edt_To = (EditText) findViewById(R.id.edt_To);
        Button btsubmit= (Button) findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsg("Leave applied");
                Intent intent=new Intent(Leaveapplication.this,MainActivity.class);
                startActivity(intent);
            }
        });
        Button btapplycancel= (Button) findViewById(R.id.btapplycancel);
        btapplycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Facid.getText().toString().trim(), edt_Leavetype.getText().toString().trim(), edt_From.getText().toString().trim(), edt_To.getText().toString().trim());

            }
        });
    }

    private void validation(String facid,String Ltype,String from,String to) {
        if (facid.length() == 0 || Ltype.length() == 0 || from.length() == 0||to.length() == 0) {
            if (facid.length() == 0) {
                edt_Facid.setError("Field can't be blank.");
            }
            if (Ltype.length() == 0) {
                edt_Leavetype.setError("Field can't be blank.");
            }
            if (from.length() == 0) {
                edt_From.setError("Field can't be blank.");
            }
            if (to.length() == 0) {
                edt_To.setError("Field can't be blank.");
            }
        }
            else{
            Intent intent=new Intent(Leaveapplication.this,MainActivity.class);
            startActivity(intent);
            finish();

        }

        }

    public void toastMsg(String msg)
    {
        Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }
}
