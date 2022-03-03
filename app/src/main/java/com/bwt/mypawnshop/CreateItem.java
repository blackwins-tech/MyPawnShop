package com.bwt.mypawnshop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class CreateItem extends AppCompatActivity {
    Button AddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item);
        /*AddItem = (Button)findViewById(R.id.AddItem);
        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAlertDialog("Confirmation", "Do you want to add Item to loan ?");
            }
        });*/
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
