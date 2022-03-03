package com.bwt.mypawnshop;

import static com.bwt.mypawnshop.PawnShopRetrofitClient.pawnShopRetrofitClient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_account extends AppCompatActivity {
    private static final String TAG = "MYPAWNSHOP_LOG";
    ShopOwnerInfo ownerInfo;
    EditText ownerAccountName;
    Calendar calendar;
    SimpleDateFormat simpledateformat;
    String Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ownerInfo = (ShopOwnerInfo) getIntent().getSerializableExtra("ownerInfoObject");
        //Toast.makeText(this, ownerInfo.getOtp_verification(), Toast.LENGTH_LONG).show();
        ownerAccountName = (EditText) findViewById(R.id.owner_account_name);
    }

    public void terms_on_condition(View view) {
        startActivity(new Intent(create_account.this, terms_condition.class));
    }
    private JsonObject ApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_app_lang", ownerInfo.getUser_app_lang());
            jsonObj_.put("user_mob_no", ownerInfo.getUser_mob_no());
            jsonObj_.put("user_mpin", ownerInfo.getUser_mpin());
            jsonObj_.put("user_name", ownerInfo.getUser_name());
            jsonObj_.put("user_created_at", ownerInfo.getUser_created_at());
            jsonObj_.put("otp_verification", ownerInfo.getOtp_verification());


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.i("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void create_account(View view) {
        if(!ownerAccountName.getText().equals(null)){
            ownerInfo.setUser_name(ownerAccountName.getText().toString());
            ownerInfo.setUser_app_lang("english");
            long millis=System.currentTimeMillis();
            java.sql.Date date=new java.sql.Date(millis);
            ownerInfo.setUser_created_at(date);
            Log.i(TAG, ownerInfo.toString());

             AttributeMethods methods = PawnShopRetrofitClient.getRetrofitInstance ().create (AttributeMethods.class);
             Call<ShopOwnerInfo> call = methods.postShopOwnerInfo(ApiJsonMap());
             call.enqueue(new Callback<ShopOwnerInfo>() {
                 @Override
                 public void onResponse(Call<ShopOwnerInfo> call, Response<ShopOwnerInfo> response) {
                     //Toast.makeText(create_account.this, response.code(), Toast.LENGTH_LONG).show();
                     Log.i(TAG, "OnResponse"+response.code());
                     Intent intent = new Intent(create_account.this, NavigationActivity.class);
                     //intent.putExtra ("ownerInfoObject", ownerInfo);
                     startActivity(intent);
                 }

                 @Override
                 public void onFailure(Call<ShopOwnerInfo> call, Throwable t) {
                    //Toast.makeText(create_account.this, t.getMessage(), Toast.LENGTH_LONG).show();
                     Log.i(TAG,"onFailure"+ t.getMessage());
                 }
             });


            /*AttributeMethods methods = PawnShopRetrofitClient.getRetrofitInstance ().create (AttributeMethods.class);
            Call<ShopOwnerInfo> call = methods.postShopOwnerInfo(ownerInfo.getUser_app_lang(), ownerInfo.getUser_mob_no(), ownerInfo.getUser_mpin(), ownerInfo.getUser_name(),ownerInfo.getUser_created_at(), ownerInfo.getOtp_verification());
            call.enqueue(new Callback<ShopOwnerInfo>() {
                @Override
                public void onResponse(Call<ShopOwnerInfo> call, Response<ShopOwnerInfo> response) {
                    Toast.makeText(create_account.this, response.code(), Toast.LENGTH_LONG).show();
                    *//*Intent intent = new Intent(create_account.this, create_loan.class);
                    intent.putExtra ("ownerInfoObject", ownerInfo);
                    startActivity(intent);*//*
                }

                @Override
                public void onFailure(Call<ShopOwnerInfo> call, Throwable t) {
                    Toast.makeText(create_account.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });*/
        }else{
            // error message
        }


    }

    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder = new AlertDialog
                .Builder(create_account.this);

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