package com.example.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.broadcastreceivers.MainActivity.ACTION_CUSTOM_BROADCAST;
import static com.example.broadcastreceivers.MainActivity.ACTION_RANDOM_BROADCAST;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String intentAction = intent.getAction();
        String toastMessage = "Unknown intent action";
        if (intentAction != null){
            switch (intentAction){
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power is connected";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power is disconnected";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast received";
                    break;
                case Intent.ACTION_HEADSET_PLUG:
                    Boolean isPluggedIn = (intent.getIntExtra("state", 0) != 0 );
                    if (isPluggedIn){
                        toastMessage = "Headset connected";
                    }else {
                        toastMessage = "Headset disconnected";
                    }
                    break;
                case ACTION_RANDOM_BROADCAST:
                    int number = intent.getIntExtra("random_number", 1);
                    int result = (int) Math.pow(number, 2);
                    toastMessage = "Power of  random number " + number + " is " + result;


            }
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

    }
}