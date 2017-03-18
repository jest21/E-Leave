package com.example.user.e_leave;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    //EditText edt_Username, edt_Password;
    final int SIGN_IN_REQUEST_CONST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_main);
        edt_Username = (EditText) findViewById(R.id.edt_Username);
        edt_Password = (EditText) findViewById(R.id.edt_Password);
        System.out.println("Hai");
        Button btlogin = (Button) findViewById(R.id.btlogin);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Username.getText().toString().trim(), edt_Password.getText().toString().trim());

            }
        });
        Button btnewuser = (Button) findViewById(R.id.btnewuser);
        btnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });*/

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                            .build(),
                    SIGN_IN_REQUEST_CONST);

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == SIGN_IN_REQUEST_CONST) {
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(this,Login.class));
                finish();
            } else {
                if (resultCode == RESULT_CANCELED){
                    Toast.makeText(this, "Request Cancelled", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "SigIn Failed. Please check your internet connection.", Toast.LENGTH_LONG).show();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setMessage("Retry?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivityForResult(
                                        AuthUI.getInstance()
                                                .createSignInIntentBuilder()
                                                .setProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                                                .build(),
                                        SIGN_IN_REQUEST_CONST);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        });
                builder.create().show();
            }
        }
    }
}



