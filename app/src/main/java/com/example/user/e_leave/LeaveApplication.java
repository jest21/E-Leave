package com.example.user.e_leave;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LeaveApplication extends AppCompatActivity{
    EditText edt_Facid,edt_Leavetype,edt_From,edt_To,reason_edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaveapplication);
        edt_Facid = (EditText) findViewById(R.id.edt_Facid);
        edt_Leavetype = (EditText) findViewById(R.id.edt_Leavetype);
        edt_From = (EditText) findViewById(R.id.edt_From);
        edt_To = (EditText) findViewById(R.id.edt_To);
        reason_edt = (EditText) findViewById(R.id.reson_edt);

        edt_Facid.setText(CurrentUser.getFacultyID(this));

        Button btsubmit= (Button) findViewById(R.id.btsubmit);
        btsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(edt_Facid.getText().toString().trim(), edt_Leavetype.getText().toString().trim(), edt_From.getText().toString().trim(), edt_To.getText().toString().trim(), reason_edt.getText().toString().trim());
            }
        });
        Button btapplycancel= (Button) findViewById(R.id.btapplycancel);
        btapplycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validation(String facid,String Ltype,String from,String to,String reason) {
        if (facid.length() == 0 || Ltype.length() == 0 || from.length() == 0||to.length() == 0) {
            if (facid.length() == 0) {
                edt_Facid.setError("Field can't be blank.");
            }
            if (Ltype.length() == 0) {
                edt_Leavetype.setError("Field can't be blank.");
            }

            if (from.length() == 0) {
                edt_From.setError("Field can't be blank.");
            }

            if (to.length() == 0) {
                edt_To.setError("Field can't be blank.");
            }
        }else if (!Ltype.toLowerCase().equals("sick")
                && !Ltype.toLowerCase().equals("casual")
                && !Ltype.toLowerCase().equals("maternity")
                && !Ltype.toLowerCase().equals("other")) {
            edt_Leavetype.setError("Incorrect Leave Type");
            new AlertDialog.Builder(this)
                    .setMessage("Leave type should be any one of the following:\n1. Sick\n2. Casual\n3. Maternity\n4. Other")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }else if (!from.matches("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$")){
            edt_From.setError("Incorrect date format");
        }else if (!to.matches("^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$")){
            edt_To.setError("Incorrect date format");
        } else{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("leave_applications").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.child("type").setValue(Ltype);
            ref.child("from").setValue(from);
            ref.child("to").setValue(to);
            ref.child("reason").setValue(reason);
            toastMsg("Success");
            finish();
        }

        }

    public void toastMsg(String msg)
    {
        Toast toast=Toast.makeText(this,msg,Toast.LENGTH_LONG);
        toast.show();
    }

}
