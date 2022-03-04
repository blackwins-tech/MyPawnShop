package com.bwt.mypawnshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Itemdetails extends AppCompatActivity {
    private static final String TAG ="ITEM DETAILS";
    Button AddItem;
    ItemSet itemSetObject = new ItemSet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        AddItem = (Button)findViewById(R.id.AddItem);
        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSetObject.setUser_id(1);
                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                itemSetObject.setSet_num_created_at(date);
                AttributeMethods methods = PawnShopRetrofitClient.getRetrofitInstance ().create (AttributeMethods.class);
                Call<ItemSet> call = methods.postItemSetsInfo(ApiJsonMap());
                call.enqueue(new Callback<ItemSet>() {
                    @Override
                    public void onResponse(Call<ItemSet> call, Response<ItemSet> response) {
                        if(response.isSuccessful()){
                            Log.i(TAG, String.valueOf(response.body()));
                            Intent intent = new Intent(Itemdetails.this, CreateItem.class);
                            intent.putExtra("item_set_id", 7);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemSet> call, Throwable t) {
                        //Toast.makeText(Itemdetails.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i(TAG, t.getMessage());
                        Intent intent = new Intent(Itemdetails.this, CreateItem.class);
                        intent.putExtra("item_set_id", 7);
                        startActivity(intent);
                    }
                });
            }
        });
    }


    private JsonObject ApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("user_id", itemSetObject.getUser_id());
            jsonObj_.put("set_num_created_at", itemSetObject.getSet_num_created_at());

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_.toString());

            //print parameter
            Log.i("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }
}