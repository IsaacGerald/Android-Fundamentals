package com.example.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {
    private static final String TAG = "CustomReceiver";
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
//    private Boolean Headset_plugged_in = false;
    public static final String RANDOM_INT = "com.example.broadcastreceivers.extras.random_int";
    @Override
    public void onReceive(Context context, Intent intent) {
         String intentAction = intent.getAction();
         if (intentAction != null){
             String toastMessage = "Unknown intent action";
             switch (intentAction){
                 case Intent.ACTION_POWER_CONNECTED:
                     toastMessage = "Power connected";
                     break;
                 case Intent.ACTION_POWER_DISCONNECTED:
                     toastMessage = "Power disconnected";
                     break;
                 case ACTION_CUSTOM_BROADCAST:
                     int random_num = intent.getIntExtra(RANDOM_INT, 0);
                     Log.d(TAG, "onReceive: " + random_num);
                     int pow = (int)Math.pow(random_num, 2);
                     toastMessage = "Custom broadcast Received,\nsquare of the random number is " + pow ;
                     break;
                 case Intent.ACTION_HEADSET_PLUG:
                         toastMessage = "Headset not plugged in";
                     break;

             }
             Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
         }
    }
}
