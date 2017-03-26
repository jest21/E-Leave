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

import java.util.Calendar;

public class SalaryDetails extends AppCompatActivity {

    TextView basicPayTextView, daTextView, hraTextView, leavesTextView, insuranceTextView, salaryTextView, deductedAmounTextView;

    int halfDayLeaves = 0, fullDayLeaves = 0;

    double hodHra = 5000,
            profHra = 2500,
            hodDa = 2000,
            profDa = 1000,
            insurance = 1500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.salarydetails);

        Button btsalaryok= (Button) findViewById(R.id.btsalaryok);
        btsalaryok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        basicPayTextView = (TextView) findViewById(R.id.basicPayTextView);
        daTextView = (TextView) findViewById(R.id.daTextView);
        hraTextView = (TextView) findViewById(R.id.hraTextView);
        leavesTextView = (TextView) findViewById(R.id.leavesTextView);
        insuranceTextView = (TextView) findViewById(R.id.insuranceTextView);
        salaryTextView = (TextView) findViewById(R.id.salaryTextView);
        deductedAmounTextView = (TextView) findViewById(R.id.deductAmountTextView);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Calculating...");
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

                            if (!child.child("type").getValue().toString().toLowerCase().equals("maternity")){
                                if (child.child("days_type").getValue().toString().toLowerCase().equals("half")){
                                    halfDayLeaves += (Integer.parseInt(toDateStr.split("-")[0]) - Integer.parseInt(fromDateStr.split("-")[0]));
                                    halfDayLeaves += 1;
                                }else {
                                    fullDayLeaves += (Integer.parseInt(toDateStr.split("-")[0]) - Integer.parseInt(fromDateStr.split("-")[0]));
                                    fullDayLeaves += 1;
                                }
                            }
                            
                        }
                        double totalNoOfLeaves = (halfDayLeaves/2.0) + fullDayLeaves;
                        leavesTextView.setText(String.valueOf(totalNoOfLeaves) + " days");
                        if (CurrentUser.getDesignation(SalaryDetails.this).toLowerCase().equals("hod")){
                            double basicPay = 40000,
                                    salPerDay = basicPay/30,
                                    deductableAmountForFullDays = salPerDay * fullDayLeaves,
                                    deductableAmountForHalfDays = (salPerDay/2) * halfDayLeaves,
                                    totalDeductedAmount = deductableAmountForFullDays + deductableAmountForHalfDays,
                                    payableSalary = basicPay + hodDa + hodHra - totalDeductedAmount;
                            salaryTextView.setText("₹ " + String.valueOf(payableSalary) + "/-");
                            daTextView.setText("₹ " + String.valueOf(hodDa) + "/-");
                            hraTextView.setText("₹ " + String.valueOf(hodHra) + "/-");
                            insuranceTextView.setText("₹ " + String.valueOf(insurance) + "/-");
                            deductedAmounTextView.setText("₹ " + String.valueOf(totalDeductedAmount).substring(0,String.valueOf(totalDeductedAmount).indexOf(".")+2) + "/-");
                            basicPayTextView.setText("₹ " + String.valueOf(basicPay) + "/-");
                        }else {
                            double basicPay = 35000,
                                    salPerDay = basicPay/30,
                                    deductableAmountForFullDays = salPerDay * fullDayLeaves,
                                    deductableAmountForHalfDays = (salPerDay/2) * halfDayLeaves,
                                    totalDeductedAmount = deductableAmountForFullDays + deductableAmountForHalfDays,
                                    payableSalary = basicPay + profDa + profHra + insurance - totalDeductedAmount;
                            salaryTextView.setText("₹ " + String.valueOf(payableSalary).substring(0,String.valueOf(payableSalary).indexOf(".")+2) + "/-");
                            daTextView.setText("₹ " + String.valueOf(profDa) + "/-");
                            hraTextView.setText("₹ " + String.valueOf(profHra) + "/-");
                            insuranceTextView.setText("₹ " + String.valueOf(insurance) + "/-");
                            deductedAmounTextView.setText("₹ " + String.valueOf(totalDeductedAmount).substring(0,String.valueOf(totalDeductedAmount).indexOf(".")+2) + "/-");
                            basicPayTextView.setText("₹ " + String.valueOf(basicPay) + "/-");
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
