package com.bwt.mypawnshop;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyPawnShop:MainActivity";
    boolean isLoggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sh = getSharedPreferences("MyPawnShopPreferences", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();
        String isAlreadyLoginFlag = sh.getString("isAlreadyLogin", "");
        if(isAlreadyLoginFlag.equals("yes")){
            isLoggedIn = true;
        }else{
            isLoggedIn = false;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // the very first time when the app is opened, there is nothing to show
                Log.i(TAG,sh.getString("isAlreadyLogin", ""));
                if(isLoggedIn){
                    Log.i(TAG,"If.."+ String.valueOf(isLoggedIn));
                    Intent i = new Intent(MainActivity.this, NavigationActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Log.i(TAG, "Else.."+String.valueOf(isLoggedIn));
                    Intent i = new Intent(MainActivity.this, Register_mobile_number.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 2000);
    }
}