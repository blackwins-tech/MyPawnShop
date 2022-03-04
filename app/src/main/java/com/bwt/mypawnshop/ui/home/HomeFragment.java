package com.bwt.mypawnshop.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bwt.mypawnshop.ApplicationConstant;
import com.bwt.mypawnshop.databinding.FragmentHomeBinding;
import com.bwt.mypawnshop.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner spinner =root.findViewById(R.id.spinner2);
        Spinner interestSpinner = root.findViewById(R.id.interestSpinner);
        final TextView textView = root.findViewById(R.id.text_home);
         EditText total_eligible_amount_price = root.findViewById(R.id.total_eligible_amount);
        EditText interestAmount = root.findViewById(R.id.interestAmount);
        EditText processingChargesText = root.findViewById(R.id.processingChargesText);
        EditText totalLoanAmountText = root.findViewById(R.id.Total_Loan_Amount_text);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(8);
        arrayList.add(10);
        arrayList.add(12);
        arrayList.add(18);
        arrayList.add(24);
        arrayList.add(32);
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedGram;
                selectedGram = (int) parent.getItemAtPosition(position);
                float calculatedGram = (float)selectedGram* ApplicationConstant.OWNER_GOLD_PRICE_PER_GRAM;
                total_eligible_amount_price.setText(String.valueOf(calculatedGram));
                processingChargesText.setText("2%(0.02)");
                float eligibleAmount = Float.parseFloat(total_eligible_amount_price.getText().toString());
                float tempCharges =eligibleAmount* ApplicationConstant.PROCESSING_CHARGES;
                float total_loan_amount = eligibleAmount - tempCharges;
                totalLoanAmountText.setText(String.valueOf(total_loan_amount));
                //Toast.makeText(parent.getContext(), "Selected: " + selectedGram, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });


        ArrayList<String> InterestArrayList = new ArrayList<>();
        InterestArrayList.add("Standard (2%)");
        InterestArrayList.add("Deluxe (5%)");
        InterestArrayList.add("Premimum (10%)");
        ArrayAdapter<String> arrayInterestAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, InterestArrayList);
        arrayInterestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestSpinner.setAdapter(arrayInterestAdapter);

        interestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedInterestType;
                selectedInterestType = parent.getItemAtPosition(position).toString();
                if(selectedInterestType.equals("Standard (2%)")){
                    float eligibleAmount = Float.parseFloat(total_eligible_amount_price.getText().toString());
                    float interestAmountPerMonth = (float) (eligibleAmount*0.02);
                    interestAmount.setText(String.valueOf(interestAmountPerMonth));
                    processingChargesText.setText("2%(0.02)");

                }else if(selectedInterestType.equals("Deluxe (5%)")){
                    float eligibleAmount = Float.parseFloat(total_eligible_amount_price.getText().toString());
                    float interestAmountPerMonth = (float) (eligibleAmount*0.05);
                    interestAmount.setText(String.valueOf(interestAmountPerMonth));
                    processingChargesText.setText("2%(0.02)");

                }else if(selectedInterestType.equals("Premimum (10%)")){
                    float eligibleAmount = Float.parseFloat(total_eligible_amount_price.getText().toString());
                    float interestAmountPerMonth = (float) (eligibleAmount*0.1);
                    interestAmount.setText(String.valueOf(interestAmountPerMonth));
                    processingChargesText.setText("2%(0.02)");
                }else{
                    // do nothing
                }
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        //interestSpinner
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("PRICE PER GRAM :"+(int) ApplicationConstant.OWNER_GOLD_PRICE_PER_GRAM);
            }
        });
        return root;
    }
}