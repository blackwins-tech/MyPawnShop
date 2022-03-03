package com.bwt.mypawnshop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                SharedPreferences sh = getSharedPreferences("MyPawnShopPreferences", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sh.edit();

                myEdit.putString("isAlreadyLogin","no");
                myEdit.apply();
                myEdit.commit();
                // The value will be default as empty string because for
                // the very first time when the app is opened, there is nothing to show
                String s1 = sh.getString("mobile number", "");
                int a = sh.getInt("age", 0);
                Log.i(TAG,sh.getString("isAlreadyLogin", ""));
                     if(sh.getString("isAlreadyLogin","").equals("yes")){
                         Intent i = new Intent(MainActivity.this, Register_mobile_number.class);
                         startActivity(i);
                         finish();
                     } else {
                         Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                         startActivity(i);
                         finish();
                     }


            }
        }, 2000);
    }
}