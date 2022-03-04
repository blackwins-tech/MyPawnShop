package com.bwt.mypawnshop;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DocumentsUpload extends AppCompatActivity {

    private static final String TAG ="UPLOAD IMAGE";
    TextView uploadAadhar;
    ImageView aadharcardImage;
    public static final int CAMERA_REQUEST =100;
    public static final int STORAGE_REQUEST =101;
    String cameraPermission[];
    String storagePermission[];
    CustomerInfo newCustomerInfo;
    Button saveBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.documents_upload);
        uploadAadhar = (TextView) findViewById(R.id.aadharBrowseClick);
        saveBtn = (Button) findViewById(R.id.SaveBtn);// uploadbtn
        aadharcardImage = (ImageView) findViewById(R.id.AadhaarImage);

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        newCustomerInfo = (CustomerInfo) getIntent().getSerializableExtra("newCustomerInfo");
        initConfig();

        uploadAadhar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int picd=0;
                if(picd ==0){
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromGallery();
                    }

                }else if(picd==1){
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AttributeMethods methods = PawnShopRetrofitClient.getRetrofitInstance ().create (AttributeMethods.class);
                Call<CustomerInfo> call = methods.postCustomerInfo(ApiJsonMap());
                call.enqueue(new Callback<CustomerInfo>() {
                    @Override
                    public void onResponse(Call<CustomerInfo> call, Response<CustomerInfo> response) {
                        //Toast.makeText(DocumentsUpload.this, response.code(), Toast.LENGTH_LONG).show();
                        if(response.isSuccessful()){
                            Log.i(TAG, String.valueOf(response.code()));
                            Intent intent = new Intent(DocumentsUpload.this, Itemdetails.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerInfo> call, Throwable t) {
                        Toast.makeText(DocumentsUpload.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DocumentsUpload.this, Itemdetails.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "blackwinsstudio");
        config.put("api_key" , "933282817979874");
        config.put("api_secret", "U3G62LPNVDs8xEqa8souO0Y0TyM");
        //config.put("secure", true);
        MediaManager.init(this, config);
    }

    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean camera_result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean storage_result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return camera_result && storage_result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                aadharcardImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(resultUri).into(aadharcardImage);
                MediaManager.get().upload(resultUri).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.i(TAG, "onStart:"+"Started..");
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        Log.i(TAG, "onProgress:"+"Uploading..");
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        Log.i(TAG, "onSuccess:"+resultData.get("secure_url"));
                        long millis=System.currentTimeMillis();
                        java.sql.Date date=new java.sql.Date(millis);
                        newCustomerInfo.setCus_created_at(date);
                        newCustomerInfo.setCus_aadhar_front((String) resultData.get("secure_url"));
                        newCustomerInfo.setCus_aadhar_back("http://");
                        newCustomerInfo.setCus_other_proof1("http://");
                        newCustomerInfo.setCus_other_proof2("http://");
                        newCustomerInfo.setCus_pan_front("http://");
                        newCustomerInfo.setCus_pan_back("http://");
                        newCustomerInfo.setCus_mob_2("NA");
                        newCustomerInfo.setUser_id(1);

                        Log.i(TAG, "customerObject:"+newCustomerInfo.toString());
                        ShowAlertDialog("Confirmation" , "Uploaded Successfully..");
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.i(TAG, "onError:"+error);
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.i(TAG, "onReschedule:"+error);
                    }
                }).dispatch();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST:{
                if(grantResults.length > 0){
                    boolean camera_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    boolean storage_accepted = grantResults[1] == (PackageManager.PERMISSION_GRANTED);
                    if(camera_accepted && storage_accepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Please Enable camera and storage permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if(grantResults.length>0){
                    boolean storage_accepted = grantResults[0]==(PackageManager.PERMISSION_GRANTED);
                    if(storage_accepted){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this,"Please Enable storage permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;
        }
    }


    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(DocumentsUpload.this);

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


    private JsonObject ApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject jsonObj_ = new JSONObject();
            jsonObj_.put("cus_name", newCustomerInfo.getCus_name());
            jsonObj_.put("cus_mob_1", newCustomerInfo.getCus_mob_1());
            jsonObj_.put("cus_mob_2", newCustomerInfo.getCus_mob_2());
            jsonObj_.put("cus_gender",newCustomerInfo.getCus_gender());
            jsonObj_.put("cus_dob", newCustomerInfo.getCus_dob());
            jsonObj_.put("cus_marital_status", newCustomerInfo.getCus_marital_status());
            jsonObj_.put("cus_address", newCustomerInfo.getCus_address());
            jsonObj_.put("cus_aadhar_front", newCustomerInfo.getCus_aadhar_front());
            jsonObj_.put("cus_aadhar_back", newCustomerInfo.getCus_aadhar_back());
            jsonObj_.put("cus_pan_front", newCustomerInfo.getCus_pan_front());
            jsonObj_.put("cus_pan_back", newCustomerInfo.getCus_pan_back());
            jsonObj_.put("cus_other_proof1", newCustomerInfo.getCus_other_proof1());
            jsonObj_.put("cus_other_proof2", newCustomerInfo.getCus_other_proof2());
            jsonObj_.put("cus_created_at", newCustomerInfo.getCus_created_at());
            jsonObj_.put("user_id", newCustomerInfo.getUser_id());


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