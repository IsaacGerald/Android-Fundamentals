package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyBoundService extends Service {
    private static final String TAG = "MyBoundService";
    private MyLocalBinder myLocalBinder = new MyLocalBinder();
    public class MyLocalBinder extends Binder{
          public MyBoundService getService(){
              return MyBoundService.this;
          }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + Thread.currentThread().getName());
        return myLocalBinder;
    }


    public int add(int a, int b){

        return a + b;
    }
    public int subtract(int a, int b){

        return a - b;
    }
    public int multiply(int a, int b){

        return a * b;
    }
    public float divide(int a, int b){

        return (float)a /  (float)b;
    }

}
