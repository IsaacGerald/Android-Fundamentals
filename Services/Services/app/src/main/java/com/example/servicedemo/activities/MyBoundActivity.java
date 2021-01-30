package com.example.servicedemo.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.servicedemo.services.MyBoundService;
import com.example.servicedemo.R;

public class MyBoundActivity extends AppCompatActivity {

    boolean isBound = false;
    private MyBoundService myBoundService;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MyBoundService.MyLocalBinder myLocalBinder = (MyBoundService.MyLocalBinder) iBinder;
            myBoundService = myLocalBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;

        }
    };

    private TextView mTxtResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound){
            unbindService(mConnection);
            isBound = false;
        }
    }

    public void onClickEvent(View view) {
        EditText etNumOne = findViewById(R.id.firstEditText);
        EditText etNumTwo = findViewById(R.id.secondEditText);
        mTxtResult = findViewById(R.id.txtResult);

        int mNumOne = Integer.valueOf(etNumOne.getText().toString());
         int mNumTwo = Integer.valueOf(etNumTwo.getText().toString());
        String result = "";
        if (isBound){
            switch (view.getId()){
                case R.id.btnAdd:
                    result = String.valueOf(myBoundService.add(mNumOne, mNumTwo));
                    break;
                case R.id.btnSub:
                    result = String.valueOf(myBoundService.subtract(mNumOne, mNumTwo));
                    break;
                case R.id.btnMultiply:
                    result = String.valueOf(myBoundService.multiply(mNumOne, mNumTwo));
                    break;
                case R.id.btnDivide:
                    result = String.valueOf(myBoundService.divide(mNumOne, mNumTwo));
                    break;
            }
            mTxtResult.setText(result);
        }

    }
}
