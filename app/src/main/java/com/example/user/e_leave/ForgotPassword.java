package com.example.user.e_leave;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout emailInputLayout;
    EditText emailEditText;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
    }

    public void resetPassword(View view) {

        String email = emailEditText.getText().toString();

        emailInputLayout.setErrorEnabled(false);
        emailInputLayout.setErrorEnabled(true);



        if (email.isEmpty()){
            emailInputLayout.setError("Please enter your Email ID");
        }else if (!Pattern.matches(EMAIL_PATTERN,email)){
            emailInputLayout.setError("Invalid Email ID");
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Trying to send reset email to " + email + "");
            progressDialog.setCancelable(false);
            progressDialog.show();
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this, "Please check you mail and follow the instructions.", Toast.LENGTH_LONG).show();
                                finish();
                            }else {
                                emailInputLayout.setError("Email Id doesn't exist");
                            }
                            progressDialog.dismiss();
                        }
                    });
        }

    }
}
