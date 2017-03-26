package com.example.user.e_leave;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class LeaveApplication extends AppCompatActivity{
    EditText edt_Facid,edt_From,edt_To,reason_edt;
    Spinner spinnerLtype, spinnerLdays;
    TextInputLayout toLayout, fromLayout, reasonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveapplication);
        edt_Facid = (EditText) findViewById(R.id.edt_Facid);
        edt_From = (EditText) findViewById(R.id.edt_From);
        edt_To = (EditText) findViewById(R.id.edt_To);
        reason_edt = (EditText) findViewById(R.id.reson_edt);
        spinnerLdays = (Spinner) findViewById(R.id.spinner_Leavedays);
        spinnerLtype = (Spinner) findViewById(R.id.spinner_Leavetype);

        toLayout = (TextInputLayout) findViewById(R.id.textInputLayoutTo);
        fromLayout = (TextInputLayout) findViewById(R.id.textInputLayoutFrom);
        reasonLayout = (TextInputLayout) findViewById(R.id.textInputLayoutReason);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,new String[]{"Casual","Maternity","Sick","Other"});
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerLtype.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,new String[]{"Half","Full","Many"});
        arrayAdapter2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerLdays.setAdapter(arrayAdapter2);

        edt_Facid.setText(CurrentUser.getFacultyID(this));

        int month = new Months().thisMonth()+1;

        if (month>9){
            fromLayout.setHint("From (DD-" + month + "-2017)");
            toLayout.setHint("To (DD-" + month + "-2017)");
        }else {
            fromLayout.setHint("From (DD-0" + month + "-2017)");
            toLayout.setHint("To (DD-0" + month + "-2017)");
        }


        Button btsubmit= (Button) findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Facid.getText().toString().trim(), spinnerLtype.getSelectedItem().toString().trim(), edt_From.getText().toString().trim(), edt_To.getText().toString().trim(), reason_edt.getText().toString().trim(), spinnerLdays.getSelectedItem().toString().trim());
            }
        });
        Button btapplycancel= (Button) findViewById(R.id.btapplycancel);
        btapplycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportActionBar().setTitle("Leave Application: " + new Months().getNameOfThisMonth());

    }

    private void validation(String facid,String Ltype,String from,String to,String reason, String Ldays) {
        toLayout.setErrorEnabled(false);
        fromLayout.setErrorEnabled(false);
        reasonLayout.setErrorEnabled(false);
        toLayout.setErrorEnabled(true);
        fromLayout.setErrorEnabled(true);
        reasonLayout.setErrorEnabled(true);
        if (facid.length() == 0 || Ltype.length() == 0 || from.length() == 0||to.length() == 0||Ldays.isEmpty()) {
            if (facid.length() == 0) {
                edt_Facid.setError("Field can't be blank.");
            }
            if (from.length() == 0) {
                fromLayout.setError("Field can't be blank.");
            }

            if (to.length() == 0) {
                toLayout.setError("Field can't be blank.");
            }

        }else if (!from.matches("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$")){
            fromLayout.setError("Incorrect date format");
        }else if (!to.matches("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$")){
            toLayout.setError("Incorrect date format");
        } else{
            Calendar fromDate = Calendar.getInstance();
            fromDate.set(
                    Integer.parseInt(from.split("-")[2]),
                    Integer.parseInt(from.split("-")[1]),
                    Integer.parseInt(from.split("-")[0])
            );
            Calendar toDate = Calendar.getInstance();
            toDate.set(
                    Integer.parseInt(to.split("-")[2]),
                    Integer.parseInt(to.split("-")[1]),
                    Integer.parseInt(to.split("-")[0])
            );
            if (fromDate.getTime().after(toDate.getTime())){

                fromLayout.setError("From date comes after to date");
                toLayout.setError("To date comes before from date");

            }else if(Integer.parseInt(from.split("-")[1])-1 != new Months().thisMonth()
                    || Integer.parseInt(from.split("-")[2]) != 2017) {
                fromLayout.setError("Month should be " + new Months().getNameOfThisMonth() + " and year should be 2017");
            }else if(Integer.parseInt(to.split("-")[1])-1 != new Months().thisMonth()
                    || Integer.parseInt(to.split("-")[2]) != 2017) {
                toLayout.setError("Month should be " + new Months().getNameOfThisMonth() + " and year should be 2017");
            }else if (Ltype.toLowerCase().equals("other") && reason.isEmpty()){
                reasonLayout.setError("Please specify your reason");
            }else {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("leave_applications").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(new Months().getNameOfThisMonth()).child(randomIdGenerator());
                ref.child("type").setValue(Ltype);
                ref.child("from").setValue(from);
                ref.child("to").setValue(to);
                ref.child("days_type").setValue(Ldays);
                ref.child("reason").setValue(reason);
                toastMsg("Success");
                finish();
            }
        }

        }

    public String randomIdGenerator(){
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public void toastMsg(String msg)
    {
        Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }

}
