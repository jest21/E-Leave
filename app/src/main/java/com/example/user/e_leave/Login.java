package com.example.user.e_leave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    TextInputEditText edt_Username, edt_Password;
    TextInputLayout emailInputLayout, passInputLayout;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        edt_Username = (TextInputEditText) findViewById(R.id.edt_Username);
        edt_Password = (TextInputEditText) findViewById(R.id.edt_Password);
        emailInputLayout = (TextInputLayout) findViewById(R.id.emailInputLayout);
        passInputLayout = (TextInputLayout) findViewById(R.id.passwordInputLayout);

        Button loginButton = (Button) findViewById(R.id.btlogin);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Login", "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Username.getText().toString().trim(), edt_Password.getText().toString().trim());

            }
        });

        Button newUserButton = (Button) findViewById(R.id.btnewuser);
        newUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validation(String id, String pass) {

        emailInputLayout.setErrorEnabled(false);
        passInputLayout.setErrorEnabled(false);
        emailInputLayout.setErrorEnabled(true);
        passInputLayout.setErrorEnabled(true);

        if (id.isEmpty()){
            emailInputLayout.setError("Please Enter Your Email ID");
        }else if (pass.isEmpty()){
            passInputLayout.setError("Please Enter Your Password");
        }else if (!Pattern.matches(EMAIL_PATTERN,id)){
            emailInputLayout.setError("Invalid Email ID");
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(id, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Login", "signInWithEmail:onComplete:" + task.isSuccessful());
                            if (!task.isSuccessful()) {
                                Log.w("Login", "signInWithEmail:failed", task.getException());
                                if (task.getException().toString().toLowerCase().contains("no user record")){
                                    emailInputLayout.setError("Not a registered user");
                                }else if (task.getException().toString().toLowerCase().contains("password is invalid")){
                                    passInputLayout.setError("Wrong Password");
                                }
                            }else {
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(this,ForgotPassword.class));
    }
}



