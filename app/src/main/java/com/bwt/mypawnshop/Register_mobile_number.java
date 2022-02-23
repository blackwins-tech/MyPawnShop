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
    Button receiveOTP;
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
        receiveOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.i(TAG, "receiveOTP button clicked..");
                String RegMobNumber = RegMob.getText().toString();
                ownerInfo = new ShopOwnerInfo();
                ownerInfo.setUser_mob_no(RegMobNumber);
                regmob = "+910"+ RegMobNumber;
                //Log.i(TAG, regmob + "receiveOTP Invoked");
                recieve_otp(view);
            }
        });
    }

    public void recieve_otp(View view) {
        sendVerificationCodeToUser(regmob);
        Log.i(TAG, regmob + "recieve_otp Invoked");

        //startActivity(new Intent(Register_mobile_number.this, verify.class));
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
        //progressBar.setVisibility(View.VISIBLE);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCodeBySystem = s;
            //progressBar.setVisibility(View.INVISIBLE);
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
            Toast.makeText(Register_mobile_number.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    private void verifyCode(String codeByUser) {
        Log.i(TAG, "verifyCode invoking...");
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(phoneAuthCredential);
    }

    private void signInTheUserByCredentials(PhoneAuthCredential phoneAuthCredential) {
        Log.i(TAG, "signInTheUserByCredentials invoking...");
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(Register_mobile_number.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(TAG, "onComplete invoking...");
                    String RegMobNumber = RegMob.getText().toString();
                    ShopOwnerInfo ownerInfo = new ShopOwnerInfo();
                    ownerInfo.setUser_mob_no(RegMobNumber);
                    Intent intent = new Intent(Register_mobile_number.this, verify.class);
                    intent.putExtra ("ownerInfoObject", ownerInfo);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(Register_mobile_number.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
