package com.bwt.mypawnshop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public class create_loan extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText CusName, CusPrimaryMobileNo, cusDOB,cusAddress;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    Button saveCustomerBtn;
    CustomerInfo customerObject = new CustomerInfo();
    String selectedRBText;
    Spinner spinner;
    String[] martialStatus = { "Married", "Unmarried"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_loan);
        CusName = (EditText) findViewById(R.id.CusName);
        CusPrimaryMobileNo = (EditText) findViewById(R.id.CusPrimaryMobileNo);
        cusDOB = (EditText) findViewById(R.id.CusDOB);
        cusAddress = (EditText) findViewById(R.id.CusAddress);
        radioGroup = (RadioGroup) findViewById(R.id.GenderRadiogroup);
        saveCustomerBtn = (Button) findViewById(R.id.saveCustomerBtn);
        spinner = (Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, martialStatus);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        saveCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCustomerPrimaryInfo(view);
            }
        });
    }

    public void saveCustomerPrimaryInfo(View view) {
        customerObject.setCus_name(CusName.getText().toString());
        customerObject.setCus_mob_1(CusPrimaryMobileNo.getText().toString());
        customerObject.setCus_dob(cusDOB.getText().toString());
        customerObject.setCus_address(cusAddress.getText().toString());
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if(selectedRadioButtonId!=-1){
            selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedRBText = selectedRadioButton.getText().toString();
        }
        customerObject.setCus_gender(selectedRBText);
       // startActivity(new Intent(create_loan.this, DocumentsUpload.class));
        //ShowAlertDialog("Confirmation" , customerObject.toString());
        Intent intent = new Intent(create_loan.this, DocumentsUpload.class);
        intent.putExtra ("newCustomerInfo", customerObject);
        startActivity(intent);
    }



    private void ShowAlertDialog(String MessageTitle, String MessageText) {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(create_loan.this);

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        customerObject.setCus_marital_status(martialStatus[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //ShowAlertDialog("Warning", "Selected the Martial Status");
    }
}