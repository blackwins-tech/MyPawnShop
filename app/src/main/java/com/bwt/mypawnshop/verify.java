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
import android.widget.TextView;
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

public class verify extends AppCompatActivity {
    private static final String TAG = "MYPAWNSHOP_LOG";
    TextView sentNumberedTextView;
    String regmob = "";
    ShopOwnerInfo ownerInfo;
    String verificationCodeBySystem;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button submitOTP;
    EditText otpDigit01;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        sentNumberedTextView = (TextView) findViewById(R.id.sentNumberedTextView);
        otpDigit01 = (EditText)findViewById(R.id.otp_digit_01);
        ownerInfo = (ShopOwnerInfo) getIntent().getSerializableExtra("ownerInfoObject");
        progressBar = findViewById(R.id.progressBar);
        regmob = ownerInfo.getUser_mob_no();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        Log.i(TAG, "onCreate Invoked");
        sentNumberedTextView.setText("We have sent a OTP to" +" "+  regmob);
        sendVerificationCodeToUser(regmob);
        submitOTP = (Button) findViewById(R.id.button2);
        submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "submitOTP button clicked..");
                submit_otp(view);
                //For checking the create post API
                /*ownerInfo.setOtp_verification("verified");
                Intent intent = new Intent(verify.this, Protect.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra ("ownerInfoObject", ownerInfo);
                startActivity(intent);*/
            }
        });
    }

    public void submit_otp(View view) {
        Log.i(TAG, "submit_otp Invoked");
        String enteredCode = verifyEnteredOTPCode();
        if(enteredCode.isEmpty() || enteredCode.length() <6) {
            ShowAlertDialog("Error" , "Enter valid Code");
            otpDigit01.requestFocus();
            return;
        }
        verifyCode(enteredCode);
        //startActivity(new Intent(verify.this, Protect.class));
    }

    private String verifyEnteredOTPCode() {
        String fullOTPDigit = otpDigit01.getText().toString();
        //Toast.makeText(verify.this, fullOTPDigit, Toast.LENGTH_LONG).show();
        return fullOTPDigit;
    }

    private void sendVerificationCodeToUser(String regmob) {
        Log.i(TAG, "sendVerificationCodeToUser started..");
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(regmob)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        Log.i(TAG, "sendVerificationCodeToUser ended...");
        progressBar.setVisibility(View.VISIBLE);

    }
     private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


         @Override
         public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
             super.onCodeSent(s, forceResendingToken);
             verificationCodeBySystem = s;
             progressBar.setVisibility(View.INVISIBLE);
             Log.i(TAG, "onCodeSent invoking...");
         }

         @Override
         public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                Log.i(TAG, "onVerificationCompleted invoking...");
                //Toast.makeText(verify.this, code, Toast.LENGTH_LONG).show();
                if(code !=null){
                    otpDigit01.setText(code);
                    verifyCode(code);
                }
         }

         @Override
         public void onVerificationFailed(@NonNull FirebaseException e) {
             Toast.makeText(verify.this, e.getMessage(), Toast.LENGTH_LONG).show();
         }
     };

    private void verifyCode(String codeByUser) {
        Log.i(TAG, "verifyCode invoking...");
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(phoneAuthCredential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential phoneAuthCredential) {
        Log.i(TAG, "signInTheUserByCredentials invoking...");
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(verify.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Log.i(TAG, "Verified...");
                        //ShowAlertDialog("Confirmation", "Mobile Verified");
                        ownerInfo.setOtp_verification("verified");
                        Intent intent = new Intent(verify.this, Protect.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra ("ownerInfoObject", ownerInfo);
                        startActivity(intent);
                    }else{
                        Toast.makeText(verify.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
            }
        });

    }

    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder = new AlertDialog
                .Builder(verify.this);

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