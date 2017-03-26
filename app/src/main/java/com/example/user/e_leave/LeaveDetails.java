package com.example.user.e_leave;

import android.app.ProgressDialog;
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

public class LeaveDetails extends AppCompatActivity {

    TextView sickLeavesRemainingTextView,
            casualLeavesRemainingTextView,
            sickLeavesTakenTextView,
            casualLeavesTakenTextView,
            maternityLeavesTakenTextView,
            otherLeavesTakenTextView;

    int halfSickLeavesCount = 0;
    int halfCasualLeavesCount = 0;
    int halfMaternityLeavesCount = 0;
    int halfOtherLeavesCount = 0;
    int fullSickLeavesCount = 0;
    int fullCasualLeavesCount = 0;
    int fullMaternityLeavesCount = 0;
    int fullOtherLeavesCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leavedetails);

        Button btdetailsok= (Button) findViewById(R.id.btdetailsok);

        sickLeavesRemainingTextView = (TextView) findViewById(R.id.remainingSickLeavesTextView);
        casualLeavesRemainingTextView = (TextView) findViewById(R.id.remainingCasualLeaves);

        sickLeavesTakenTextView = (TextView) findViewById(R.id.sickLeavesTextView);
        casualLeavesTakenTextView = (TextView) findViewById(R.id.casualLeavesTaken);
        maternityLeavesTakenTextView = (TextView) findViewById(R.id.maternityLeavesTaken);
        otherLeavesTakenTextView = (TextView) findViewById(R.id.otherLeavesTaken);

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

                            int to = Integer.parseInt(toDateStr.split("-")[0]);
                            int from = Integer.parseInt(fromDateStr.split("-")[0]);

                            int count = to - from + 1;

                            if (child.child("type").getValue().toString().toLowerCase().equals("maternity")){
                                if (child.child("days_type").getValue().toString().toLowerCase().equals("half")){
                                    halfMaternityLeavesCount += count;
                                }else {
                                    fullMaternityLeavesCount += count;
                                }
                            }else if (child.child("type").getValue().toString().toLowerCase().equals("sick")){
                                if (child.child("days_type").getValue().toString().toLowerCase().equals("half")){
                                    halfSickLeavesCount += count;
                                }else {
                                    fullSickLeavesCount += count;
                                }
                            }else if (child.child("type").getValue().toString().toLowerCase().equals("casual")){
                                if (child.child("days_type").getValue().toString().toLowerCase().equals("half")){
                                    halfCasualLeavesCount += count;
                                }else {
                                    fullCasualLeavesCount += count;
                                }
                            }else {
                                if (child.child("days_type").getValue().toString().toLowerCase().equals("half")){
                                    halfOtherLeavesCount += count;
                                }else {
                                    fullOtherLeavesCount += count;
                                }
                            }
                        }
                        double totalNoOfSickLeaves = (halfSickLeavesCount /2.0) + fullSickLeavesCount;
                        double totalNoOfMaternityLeaves = (halfMaternityLeavesCount /2.0) + fullMaternityLeavesCount;
                        double totalNoOfCasualLeaves = (halfCasualLeavesCount /2.0) + fullCasualLeavesCount;
                        double totalNoOfOtherLeaves = (halfOtherLeavesCount /2.0) + fullOtherLeavesCount;

                        sickLeavesTakenTextView.setText(String.valueOf(totalNoOfSickLeaves) + " days");
                        maternityLeavesTakenTextView.setText(String.valueOf(totalNoOfMaternityLeaves) + " days");
                        casualLeavesTakenTextView.setText(String.valueOf(totalNoOfCasualLeaves) + " days");
                        otherLeavesTakenTextView.setText(String.valueOf(totalNoOfOtherLeaves) + " days");

                        if (totalNoOfCasualLeaves > 0){
                            casualLeavesRemainingTextView.setText("0 days");
                        }else {
                            casualLeavesRemainingTextView.setText("1 day");
                        }

                        if (totalNoOfSickLeaves > 0){
                            sickLeavesRemainingTextView.setText("0 days");
                        }else {
                            sickLeavesRemainingTextView.setText("1 day");
                        }

                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
