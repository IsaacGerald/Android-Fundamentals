package com.example.servicedemo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.servicedemo.services.MyIntentService;
import com.example.servicedemo.services.MyStartedService;
import com.example.servicedemo.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTxtStartServiceResult;
    private TextView mTxtIntentServiceResult;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtStartServiceResult = findViewById(R.id.txtStartServiceResult);
        mTxtIntentServiceResult = findViewById(R.id.txtIntentServiceResult);
    }

    public void startStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        intent.putExtra("SleepTime", 10);
        startService(intent);
    }

    public void stopStartedService(View view) {
        Intent intent = new Intent(MainActivity.this, MyStartedService.class);
        stopService(intent);
    }

    public void startIntentService(View view) {
        MyResultReceiver resultReceiver = new MyResultReceiver(null);

        Intent intentService = new Intent(MainActivity.this, MyIntentService.class);
        intentService.putExtra("SleepTime", 10);
        intentService.putExtra("receiver", resultReceiver);
        startService(intentService);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.service.to.activity");
        registerReceiver(myStartedServiceReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myStartedServiceReceiver);
    }

    private BroadcastReceiver myStartedServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           String result = intent.getStringExtra("startServiceResult");
           mTxtStartServiceResult.setText(result);
        }
    };

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, MyBoundActivity.class);
        startActivity(intent);
    }

    public void LaunchMessageActivity(View view) {
        Intent intent = new Intent(this, MessengerActivity.class);
        startActivity(intent);
    }

    //To Receive the data back from MyIntentService.java using ResultReceiver
    private  class MyResultReceiver extends ResultReceiver{
        private static final String TAG = "MyResultReceiver";

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.i(TAG, "onReceiveResult: " + Thread.currentThread().getName());
            if (resultCode == 16 && resultData != null){
                final String result = resultData.getString("resultIntentService");
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "run: " + Thread.currentThread().getName());
                        mTxtIntentServiceResult.setText(result);
                    }
                });
            }
        }
    }
}