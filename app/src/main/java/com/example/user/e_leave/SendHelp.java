package com.example.user.e_leave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SendHelp extends AppCompatActivity {

    EditText subjectEditText, queryEditText;
    TextInputLayout subjectLayout, queryLayout;
    private final String emailID = "jestinajohn30@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        subjectEditText = (EditText) findViewById(R.id.subjectEditText);
        queryEditText = (EditText) findViewById(R.id.queryEditText);

        subjectLayout = (TextInputLayout) findViewById(R.id.subjectInputLayout);
        queryLayout = (TextInputLayout) findViewById(R.id.queryInputLayout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                subjectLayout.setErrorEnabled(false);
                queryLayout.setErrorEnabled(false);
                queryLayout.setErrorEnabled(true);
                subjectLayout.setErrorEnabled(true);

                String subject = subjectEditText.getText().toString();
                String query = queryEditText.getText().toString();
                if (subject.isEmpty()){
                    subjectLayout.setError("Required Field");
                }else if (query.isEmpty()){
                    queryLayout.setError("Required Field");
                }else {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailID));
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT,
                            "E-Leave Feedback\n\nMessage from:" + CurrentUser.getName(SendHelp.this) +
                            "\nUUID:" + FirebaseAuth.getInstance().getCurrentUser().getUid() +
                            "\nMessage:\n" + query);
                    startActivity(Intent.createChooser(intent, "Send Query"));
                    finish();
                }
            }
        });
    }
}
