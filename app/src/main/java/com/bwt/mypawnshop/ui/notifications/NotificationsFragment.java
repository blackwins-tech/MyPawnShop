package com.bwt.mypawnshop.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bwt.mypawnshop.CustomerInfo;
import com.bwt.mypawnshop.DocumentsUpload;
import com.bwt.mypawnshop.create_loan;
import com.bwt.mypawnshop.databinding.FragmentNotificationsBinding;


import com.bwt.mypawnshop.R;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    EditText CusName, CusPrimaryMobileNo, cusDOB,cusAddress;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    Button saveCustomerBtn;
    CustomerInfo customerObject = new CustomerInfo();
    String selectedRBText;
    Spinner spinner;
    String[] martialStatus = { "Married", "Unmarried"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        Intent intent = new Intent(getActivity(), create_loan.class);
        startActivity(intent);



        /*final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

}