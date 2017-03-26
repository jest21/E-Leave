package com.example.user.e_leave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
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

import java.util.regex.Pattern;

/***
 * Created by User on 24-01-2017.
 */
public class Registration extends AppCompatActivity {
    EditText edt_facualtyId, edt_Name, edt_Dept, edt_Designation, edt_Phone, edt_Emailid, edt_Password;
    TextInputLayout fIdLayout, nameLayout, deptLayout, desigLayout, phoneLayout, emailLayout, passLayout;
    FirebaseAuth auth;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

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
        fIdLayout = (TextInputLayout) findViewById(R.id.facultyIdInputLayout);
        nameLayout = (TextInputLayout) findViewById(R.id.nameInputLayout);
        deptLayout = (TextInputLayout) findViewById(R.id.deptInputLayout);
        desigLayout = (TextInputLayout) findViewById(R.id.designationInputLayout);
        phoneLayout = (TextInputLayout) findViewById(R.id.phoneNumberInputLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        passLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);
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

        fIdLayout.setErrorEnabled(false);
        nameLayout.setErrorEnabled(false);
        desigLayout.setErrorEnabled(false);
        deptLayout.setErrorEnabled(false);
        emailLayout.setErrorEnabled(false);
        passLayout.setErrorEnabled(false);
        phoneLayout.setErrorEnabled(false);
        fIdLayout.setErrorEnabled(true);
        nameLayout.setErrorEnabled(true);
        desigLayout.setErrorEnabled(true);
        deptLayout.setErrorEnabled(true);
        emailLayout.setErrorEnabled(true);
        passLayout.setErrorEnabled(true);
        phoneLayout.setErrorEnabled(true);

        if (facultyId.length() == 0) {
            fIdLayout.setError("Required Field");
        }else if (name.length() == 0) {
            nameLayout.setError("Required Field");
        }else if (dept.length() == 0) {
            deptLayout.setError("Required Field");
        }else if (desgn.length() == 0) {
            desigLayout.setError("Required Field");
        }else if (phone.length() == 0) {
            phoneLayout.setError("Required Field");
        }else if (email.length() == 0) {
            emailLayout.setError("Required Field");
        }else if (password.length() == 0) {
            passLayout.setError("Required Field");
        }else if (!Pattern.matches(EMAIL_PATTERN,email)){
            emailLayout.setError("Invalid Email Id");
        }else {
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

