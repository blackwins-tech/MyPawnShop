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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CreateItem extends AppCompatActivity {
    private static final String TAG ="CREATE ITEM";
    EditText itemName, itemWeight, itemDescription;
    Button ItemSaveBtn;
    TextView itemImageUpload;
    Spinner itemKeepingUnit, itemQuality;
    ImageView ItemImage;
    ProgressBar imageUploadProgressBar;

    //camera
    public static final int CAMERA_REQUEST =100;
    public static final int STORAGE_REQUEST =101;
    String cameraPermission[];
    String storagePermission[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        itemKeepingUnit = findViewById(R.id.itemKeepingUnit);
        ItemImage = (ImageView) findViewById(R.id.ItemImage);
        imageUploadProgressBar = (ProgressBar) findViewById(R.id.imageUploadProgressBar);
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //initConfig();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Main Locker");
        arrayList.add("Warehouse");
        arrayList.add("Shop Locker");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemKeepingUnit.setAdapter(arrayAdapter);

        itemKeepingUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedKeepingUnit = parent.getItemAtPosition(position).toString();
                Log.i(TAG, selectedKeepingUnit);

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        itemQuality= findViewById(R.id.itemQuality);
        ArrayList<String> arrayItemQualityList = new ArrayList<>();
        arrayItemQualityList.add("14k");
        arrayItemQualityList.add("18k");
        arrayItemQualityList.add("22k");
        arrayItemQualityList.add("24k");
        ArrayAdapter<String> arrayItemQualityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayItemQualityList);
        arrayItemQualityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemQuality.setAdapter(arrayItemQualityAdapter);

        itemKeepingUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuality = parent.getItemAtPosition(position).toString();
                Log.i(TAG, selectedQuality);

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        itemImageUpload.setOnClickListener(new View.OnClickListener() {

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


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                ItemImage.setVisibility(View.VISIBLE);
                Picasso.with(this).load(resultUri).into(ItemImage);
                MediaManager.get().upload(resultUri).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.i(TAG, "onStart:"+"Started..");
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        Log.i(TAG, "onProgress:"+"Uploading..");
                        imageUploadProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        Log.i(TAG, "onSuccess:"+resultData.get("secure_url"));
                        imageUploadProgressBar.setVisibility(View.GONE);
                        /*long millis=System.currentTimeMillis();
                        java.sql.Date date=new java.sql.Date(millis);
                       */
                        //Log.i(TAG, "customerObject:"+newCustomerInfo.toString());
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
    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(CreateItem.this);

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
                                Intent intent = new Intent(CreateItem.this, Itemdetails.class);
                            }
                        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}
