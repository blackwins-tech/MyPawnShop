package com.bwt.mypawnshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register_mobile_number extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_mobile_number);
    }

    public void recieve_otp(View view) {
        startActivity(new Intent(Register_mobile_number.this, verify.class));
    }
}