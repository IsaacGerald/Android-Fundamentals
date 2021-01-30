package com.example.servicedemo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servicedemo.R;
import com.example.servicedemo.services.MyMessengerService;

public class MessengerActivity extends AppCompatActivity {
    private static final String TAG = "MessengerActivity";
    boolean isBound = false;
    private TextView mTxvResult;
    private Messenger mService = null;

    private class IncomingResponseHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msgFromService) {

            switch (msgFromService.what){
                case 50:
                    Bundle bundle = msgFromService.getData();
                    int result = bundle.getInt("result");
                    mTxvResult.setText("Result: " + result);
                    break;
                default:
                    super.handleMessage(msgFromService);
            }
        }
    }

    private Messenger incomingMessenger = new Messenger(new IncomingResponseHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: " + Thread.currentThread().getName());
            isBound = true;
            mService = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + Thread.currentThread().getName());
            isBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messanger);
        mTxvResult = findViewById(R.id.textMessageResult);
    }

    public void performAddOperation(View view) {
        EditText etFirstNo = findViewById(R.id.etFirstNum);
        EditText etSecondNo = findViewById(R.id.etSecondNum);

        int firstNum = Integer.parseInt(etFirstNo.getText().toString());
        int secondNum = Integer.parseInt(etSecondNo.getText().toString());

        Message msgToService = Message.obtain(null, 43);
        msgToService.replyTo = incomingMessenger;
        Bundle bundle = new Bundle();
        bundle.putInt("numOne", firstNum);
        bundle.putInt("numTwo", secondNum);

        msgToService.setData(bundle);

        try {
            mService.send(msgToService);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    public void bindService(View view) {
        Intent intent = new Intent(this,MyMessengerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

    }

    public void unbindService(View view) {
        if (isBound){
            unbindService(mConnection);
            isBound = false;
        }
    }
}