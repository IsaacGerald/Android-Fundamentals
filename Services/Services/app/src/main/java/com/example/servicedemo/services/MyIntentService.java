package com.example.servicedemo.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
    private static final String TAG = MyIntentService.class.getSimpleName();
    public MyIntentService() {
        super("MyWorker Thread");
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: " + Thread.currentThread().getName());
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: " + Thread.currentThread().getName());
       int sleepTime = intent.getIntExtra("SleepTime", 1);
        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
       int ctr = 1;

        //Dummy long operation
        while (ctr <= sleepTime){
            Log.i(TAG, "onHandleIntent: Counter is now " + ctr);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctr++;
        }

        Bundle bundle = new Bundle();
        bundle.putString("resultIntentService", "Counter stopped at " + ctr + " seconds");

        resultReceiver.send(16, bundle );


    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: " + Thread.currentThread().getName());
        super.onDestroy();
    }
}
