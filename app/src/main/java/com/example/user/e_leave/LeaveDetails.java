package com.example.user.e_leave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class LeaveDetails extends AppCompatActivity {

    TextView leavesRemainingTextView, leavesTakenTextView;
    int leavesTaken = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavedetails);
        Button btdetailsok= (Button) findViewById(R.id.btdetailsok);
        leavesRemainingTextView = (TextView) findViewById(R.id.leavesRemainingTextView);
        leavesTakenTextView = (TextView) findViewById(R.id.leavesTakenTextView);
        btdetailsok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Details...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseDatabase.getInstance().getReference().child("leave_applications")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(new Months().getNameOfThisMonth())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot child : dataSnapshot.getChildren()){
                            String fromDateStr = child.child("from").getValue().toString();
                            String toDateStr = child.child("to").getValue().toString();
                            Calendar fromDate = Calendar.getInstance();
                            fromDate.set(
                                    Integer.parseInt(fromDateStr.split("-")[2]),
                                    Integer.parseInt(fromDateStr.split("-")[1]),
                                    Integer.parseInt(fromDateStr.split("-")[0])
                            );
                            Calendar toDate = Calendar.getInstance();
                            toDate.set(
                                    Integer.parseInt(toDateStr.split("-")[2]),
                                    Integer.parseInt(toDateStr.split("-")[1]),
                                    Integer.parseInt(toDateStr.split("-")[0])
                            );
                            leavesTaken += (toDate.get(Calendar.DAY_OF_MONTH) - fromDate.get(Calendar.DAY_OF_MONTH));
                            leavesTaken += 1;
                        }
                        leavesTakenTextView.setText(String.valueOf(leavesTaken));
                        if (leavesTaken > 0){
                            leavesRemainingTextView.setText("0");
                        }else {
                            leavesRemainingTextView.setText("1");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
