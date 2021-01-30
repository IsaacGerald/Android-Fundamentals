package com.example.mockingrepository.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

public class Repository {
    private static final String TAG = "Repository";

    public MutableLiveData<String> mLiveData = new MutableLiveData<>();

    public String getData(){
        return "This is Data";
    }

    public void getValues(String a, String b){

        Log.d(TAG, "getValues: " + a + " " + b);
        
    }

    public MutableLiveData<String> getLiveData(String name, String pass) {
        mLiveData.setValue("Name is " + name + " Pass is " + pass);
        return mLiveData;
    }

}
