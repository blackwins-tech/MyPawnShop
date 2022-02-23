package com.bwt.mypawnshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    //ProgressBar progressBar;
    Button submitOTP;
    EditText otpDigit01,otpDigit02,otpDigit03,otpDigit04,otpDigit05,otpDigit06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        sentNumberedTextView = (TextView) findViewById(R.id.sentNumberedTextView);
        otpDigit01 = (EditText)findViewById(R.id.otp_digit_01);
        otpDigit02 = (EditText)findViewById(R.id.otp_digit_02);
        otpDigit03 = (EditText)findViewById(R.id.otp_digit_03);
        otpDigit04 = (EditText)findViewById(R.id.otp_digit_04);
        otpDigit05 = (EditText)findViewById(R.id.otp_digit_05);
        otpDigit06 = (EditText)findViewById(R.id.otp_digit_06);
        ownerInfo = (ShopOwnerInfo) getIntent().getSerializableExtra("ownerInfoObject");
        //progressBar = findViewById(R.id.progressBar);
        String temp = ownerInfo.getUser_mob_no();
        regmob = "+910"+ temp;
        // Initialize Firebase Auth
        //mAuth = FirebaseAuth.getInstance();
        Log.i(TAG, "onCreate Invoked");
        sentNumberedTextView.setText("We have sent a OTP to" +" "+  regmob);
        submitOTP = (Button) findViewById(R.id.button2);
        submitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "submitOTP button clicked..");
                submit_otp(view);
            }
        });
    }

    public void submit_otp(View view) {
        //sendVerificationCodeToUser(regmob);
        Log.i(TAG, "submit_otp Invoked");
        verifyTheOTPCode();
        startActivity(new Intent(verify.this, Protect.class));
    }

    private void verifyTheOTPCode() {
        String fullOTPDigit = otpDigit01.getText().toString()+otpDigit02.getText().toString()+otpDigit03.getText().toString()+otpDigit04.getText().toString()+otpDigit05.getText().toString()+otpDigit06.getText().toString();
        Toast.makeText(verify.this, fullOTPDigit, Toast.LENGTH_LONG).show();
    }

    /*private void sendVerificationCodeToUser(String regmob) {
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
                        Log.i(TAG, "onComplete invoking...");
                        Intent intent = new Intent(verify.this, Protect.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.putExtra ("ownerInfoObject", ownerInfo);
                        startActivity(intent);
                    }else{
                        Toast.makeText(verify.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
            }
        });

    }*/

}