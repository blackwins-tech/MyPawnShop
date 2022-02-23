package com.bwt.mypawnshop;

import static com.bwt.mypawnshop.PawnShopRetrofitClient.pawnShopRetrofitClient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_account extends AppCompatActivity {
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
        ownerAccountName = (EditText) findViewById(R.id.owner_account_name);
        //String tempValue = ownerInfo.getUser_mob_no() +" " + ownerInfo.getUser_mpin();
        //Toast.makeText(this, tempValue, Toast.LENGTH_LONG).show();
    }

    public void terms_on_condition(View view) {
        startActivity(new Intent(create_account.this, terms_condition.class));
    }

    public void create_account(View view) {
        if(!ownerAccountName.getText().equals(null)){
            ownerInfo.setUser_name(ownerAccountName.getText().toString());
            ownerInfo.setUser_app_lang("english");
            calendar = Calendar.getInstance();
            simpledateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date = simpledateformat.format(calendar.getTime());
            //ownerInfo.setUser_created_at(date);
            Toast.makeText(this, Date, Toast.LENGTH_LONG).show();
            String tempValue = ownerInfo.getUser_mob_no() +" " + ownerInfo.getUser_mpin() +" " + ownerInfo.getUser_name();
            Toast.makeText(this, tempValue, Toast.LENGTH_LONG).show();
            AttributeMethods methods = PawnShopRetrofitClient.getRetrofitInstance ().create (AttributeMethods.class);
            Call<ShopOwnerInfo> call = methods.postShopOwnerInfo(ownerInfo.getUser_app_lang(), ownerInfo.getUser_mob_no(), ownerInfo.getUser_mpin(), ownerInfo.getUser_name(),ownerInfo.getUser_created_at() );
            call.enqueue(new Callback<ShopOwnerInfo>() {
                @Override
                public void onResponse(Call<ShopOwnerInfo> call, Response<ShopOwnerInfo> response) {
                    Toast.makeText(create_account.this, response.body().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<ShopOwnerInfo> call, Throwable t) {
                    Toast.makeText(create_account.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            // error message
        }

        //startActivity(new Intent(create_account.this, create_loan.class));
    }
}