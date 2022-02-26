package com.bwt.mypawnshop.ui.estimation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EstimationViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public EstimationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Estimation fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}