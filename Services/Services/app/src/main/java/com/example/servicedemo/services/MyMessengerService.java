package com.example.servicedemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyMessengerService extends Service {

    private class IncomingHandler  extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 43:
                    Bundle bundle = msg.getData();
                    int num1 = bundle.getInt("numOne", 0);
                    int num2 = bundle.getInt("numTwo", 0);
                    
                   int result = add(num1, num2);
                   Toast.makeText(MyMessengerService.this, "Result: " + result , Toast.LENGTH_SHORT).show();

                  //Send data back to the activity
                   Messenger incomingMessenger = msg.replyTo;

                   Message msgToActivity = Message.obtain(null, 50);
                   Bundle bundleToActivity = new Bundle();
                   bundleToActivity.putInt("result", result);

                   msgToActivity.setData(bundleToActivity);
                    try {
                        incomingMessenger.send(msgToActivity);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    Messenger mMessenger = new Messenger(new IncomingHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    public int add(int a, int b){
        return a + b;
    }
}
