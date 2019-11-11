package com.example.isdrtest2;



import androidx.lifecycle.MutableLiveData;

import java.util.List;
public class ViewModel extends androidx.lifecycle.ViewModel {

    private MutableLiveData<List<DaoPytZazPytPraw>> currentName;

    public MutableLiveData<List<DaoPytZazPytPraw>> getCurrentName() {
        if (currentName == null) {
            currentName = new MutableLiveData<>();
        }
        return currentName;


}}
