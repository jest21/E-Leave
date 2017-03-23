package com.example.user.e_leave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/***
 * Created by User on 24-01-2017.
 */
public class Registration extends AppCompatActivity {
    EditText edt_facualtyId, edt_Name, edt_Dept, edt_Designation, edt_Phone, edt_Emailid, edt_Password;

    FirebaseAuth auth;

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
        edt_Password = (EditText) findViewById(R.id.edt_Password);
        Button btregister = (Button) findViewById(R.id.btregister);

        auth = FirebaseAuth.getInstance();

        btregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation(edt_facualtyId.getText().toString().trim(), edt_Name.getText().toString().trim(), edt_Dept.getText().toString().trim(), edt_Designation.getText().toString().trim(), edt_Phone.getText().toString().trim(), edt_Emailid.getText().toString().trim(), edt_Password.getText().toString().trim());
            }

        });

        Button btcancel = (Button) findViewById(R.id.btcancel);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();


            }
        });
    }

    private void Validation(final String facultyId, final String name, final String dept, final String desgn, final String phone, final String email, String password) {
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
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Registering...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Registration", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Toast.makeText(Registration.this, "Registration Failed",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
                                myRef = myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                myRef.child("name").setValue(name);
                                myRef.child("faculty_id").setValue(facultyId);
                                myRef.child("designation").setValue(desgn);
                                myRef.child("phone").setValue(phone);
                                myRef.child("email").setValue(email);
                                myRef.child("department").setValue(dept);
                                Intent intent = new Intent(Registration.this, MainActivity.class);
                                startActivity(intent);
                                toastmsg("Welcome, " + name);
                                finish();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }


    public void toastmsg(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toast.show();
    }

}

