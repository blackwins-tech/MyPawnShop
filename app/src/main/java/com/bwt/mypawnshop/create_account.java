package com.bwt.mypawnshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class create_account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void terms_on_condition(View view) {
        startActivity(new Intent(create_account.this, terms_condition.class));
    }

    public void create_account(View view) {
        startActivity(new Intent(create_account.this, create_loan.class));
    }
}