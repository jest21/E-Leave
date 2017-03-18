package com.example.user.e_leave;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by User on 24-01-2017.
 */
public class Registration extends Activity {
    EditText edt_facualtyId, edt_Name, edt_Dept, edt_Designation, edt_Phone, edt_Emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        edt_facualtyId = (EditText) findViewById(R.id.edt_facualtyId);
        edt_Name = (EditText) findViewById(R.id.edt_Name);
        edt_Dept = (EditText) findViewById(R.id.edt_Dept);
        edt_Designation = (EditText) findViewById(R.id.edt_Designation);
        edt_Phone = (EditText) findViewById(R.id.edt_Phone);
        edt_Emailid = (EditText) findViewById(R.id.edt_Emailid);
        Button btregister = (Button) findViewById(R.id.btregister);

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation(edt_facualtyId.getText().toString().trim(), edt_Name.getText().toString().trim(), edt_Dept.getText().toString().trim(), edt_Designation.getText().toString().trim(), edt_Phone.getText().toString().trim(), edt_Emailid.getText().toString().trim());



            }

        });
        Button btcancel = (Button) findViewById(R.id.btcancel);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }

    private void Validation(String facultyId, String name, String dept, String desgn, String phone, String email) {
        if (facultyId.length() == 0 || name.length() == 0 || dept.length() == 0) {
            if (facultyId.length() == 0) {
                edt_facualtyId.setError("Field can't be blank.");
            }
            if (name.length() == 0) {
                edt_Name.setError("Field can't be blank.");
            }
            if (dept.length() == 0) {
                edt_Dept.setError("field cant be blank");
            }
            if (desgn.length() == 0) {
                edt_Designation.setError("field cant be blank");
            }

            if (phone.length() == 0) {
                edt_Phone.setError("field cant be blank");
            }

            if (email.length() == 0) {
                edt_Emailid.setError("field cant be blank");
            }


        } else {
            Intent intent = new Intent(Registration.this, Login.class);
            startActivity(intent);
            toastmsg("Welcome");
            finish();
        }
    }


    public void toastmsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}

