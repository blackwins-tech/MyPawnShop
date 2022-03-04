package com.bwt.mypawnshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Protect extends AppCompatActivity {
    private static final String TAG = "MYPAWNSHOP_LOG";
    EditText firtDigitno, secondDigitno, thirdDigitno, fourthDigitno;
    EditText refirtDigitno, resecondDigitno, rethirdDigitno, refourthDigitno;
    String mPin, confirm_mPin;
    //int mPin_in_number;
    ShopOwnerInfo ownerInfo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect);
        ownerInfo = (ShopOwnerInfo) getIntent().getSerializableExtra("ownerInfoObject");
        Toast.makeText(this, ownerInfo.getOtp_verification(), Toast.LENGTH_LONG).show();
        // Storing data into SharedPreferences
        sharedPreferences = getSharedPreferences("MyPawnShopPreferences",MODE_PRIVATE);
        // Creating an Editor object to edit(write to the file)
        sh = sharedPreferences.edit();
        firtDigitno = (EditText) findViewById(R.id.firstDigit);
        secondDigitno = (EditText) findViewById(R.id.secondDigit);
        thirdDigitno = (EditText) findViewById(R.id.thirdDigit);
        fourthDigitno = (EditText) findViewById(R.id.fourthDigit);


        refirtDigitno = (EditText) findViewById(R.id.refirstDigit);
        resecondDigitno = (EditText) findViewById(R.id.resecondDigit);
        rethirdDigitno = (EditText) findViewById(R.id.rethirdDigit);
        refourthDigitno = (EditText) findViewById(R.id.refourthDigit);

    }

    public void submit(View view) {
        mPin = firtDigitno.getText().toString() + secondDigitno.getText().toString() + thirdDigitno.getText().toString()+fourthDigitno.getText().toString();
        confirm_mPin = refirtDigitno.getText().toString() + resecondDigitno.getText().toString() + rethirdDigitno.getText().toString()+refourthDigitno.getText().toString();

        if(mPin.equals(confirm_mPin)) {
            //mPin_in_number = Integer.parseInt(mPin);
            ownerInfo.setUser_mpin(mPin);
            if(sharedPreferences.getString("isAlreadyLogin", "").isEmpty()){
                // Storing the key and its value as the data fetched from edittext
                sh.putString("mobileNumber", ownerInfo.getUser_mob_no());
                sh.putInt("mPin", Integer.parseInt(mPin));
                sh.putString("isAlreadyLogin","yes");
                sh.apply();
                sh.commit();
            }
            Log.i(TAG,sharedPreferences.getString("isAlreadyLogin", ""));
            Intent intent = new Intent(Protect.this, create_account.class);
            intent.putExtra ("ownerInfoObject", ownerInfo);
            startActivity(intent);
            finish();
            //Toast.makeText(this, "pin matched", Toast.LENGTH_LONG).show();
        }else{
            // show the error message of mismatched
            Toast.makeText(this, "pin mismatch try again", Toast.LENGTH_LONG).show();
        }

    }
}