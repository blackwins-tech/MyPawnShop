package com.bwt.mypawnshop.ui.estimation;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.bwt.mypawnshop.databinding.FragmentEstimationBinding;


public class EstimationFragment extends Fragment {

    private EstimationViewModel estimationViewModel;
    private FragmentEstimationBinding binding;

    public static EstimationFragment newInstance() {
        return new EstimationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.estimation_fragment, container, false);
        estimationViewModel =
                new ViewModelProvider(this).get(EstimationViewModel.class);

        binding = FragmentEstimationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEstimation;
        estimationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        estimationViewModel = new ViewModelProvider(this).get(EstimationViewModel.class);
        // TODO: Use the ViewModel
    }
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}