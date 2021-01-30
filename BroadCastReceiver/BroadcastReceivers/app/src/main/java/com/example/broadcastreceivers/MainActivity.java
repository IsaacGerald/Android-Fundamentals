package com.example.broadcastreceivers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    public static final String ACTION_RANDOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_RANDOM_BROADCAST";
    private CustomReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReceiver = new CustomReceiver();

   //System Broadcast
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);


        this.registerReceiver(mReceiver, filter );

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver,
                        new IntentFilter(ACTION_CUSTOM_BROADCAST));

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver,
                        new IntentFilter(ACTION_RANDOM_BROADCAST));
        
    }

    @Override
    protected void onDestroy() {

        this.unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view) {
        //Local BroadCast
        Intent customIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        LocalBroadcastManager.getInstance(this).sendBroadcast(customIntent);


    }

    public void sendCustomBroadcastRandomNumber(View view) {
        Intent randomCustomIntent = new Intent(ACTION_RANDOM_BROADCAST);
        int randomNumber = (int) ((Math.random() *(20 - 1)) + 1);
        randomCustomIntent.putExtra("random_number", randomNumber);
        LocalBroadcastManager.getInstance(this).sendBroadcast(randomCustomIntent);
    }
}