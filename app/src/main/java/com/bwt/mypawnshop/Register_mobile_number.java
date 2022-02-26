package com.bwt.mypawnshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class Register_mobile_number extends AppCompatActivity {
    private static final String TAG = "MYPAWNSHOP_LOG";
    EditText RegMob;
    String regmob = "";
    String verificationCodeBySystem;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button receiveOTP, TestButton;
    ShopOwnerInfo ownerInfo;

    {
        ownerInfo = new ShopOwnerInfo();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mobile_number);
        RegMob = (EditText) findViewById(R.id.RegMob);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Log.i(TAG, "onCreate Invoked");
        receiveOTP = (Button) findViewById(R.id.button);
        TestButton = (Button) findViewById(R.id.TestButton);

        receiveOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "receiveOTP button clicked..");
                String RegMobNumber = RegMob.getText().toString().trim();
                if(RegMobNumber.isEmpty() || RegMobNumber.length() <10){
                    ShowAlertDialog("Error" , "Mobile Number Should not be empty");
                    return;
                }else{
                    ownerInfo = new ShopOwnerInfo();
                    regmob = "+910"+ RegMobNumber;
                    ownerInfo.setUser_mob_no(regmob);
                    recieve_otp(view);
                }

            }
        });


        TestButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register_mobile_number.this, create_loan.class));
            }
        });
    }

    public void recieve_otp(View view) {
        Log.i(TAG, regmob + "recieve_otp Invoked");
        Intent intent = new Intent(Register_mobile_number.this, verify.class);
        intent.putExtra("ownerInfoObject", ownerInfo);
        startActivity(intent);
        //startActivity(new Intent(Register_mobile_number.this, verify.class));
    }


    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Register_mobile_number.this);

        // Set the message show for the Alert time
        builder.setMessage(MessageText);

        // Set Alert Title
        builder.setTitle(MessageTitle);

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                //finish();
                                dialog.cancel();
                            }
                        });

        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        /*builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });*/

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }

}
