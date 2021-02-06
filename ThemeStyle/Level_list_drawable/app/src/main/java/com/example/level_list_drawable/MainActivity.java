package com.example.level_list_drawable;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private LevelListDrawable mLevelListDrawable;
    private ImageView mBatteryImage;
    private int mCharging;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.text);
        mBatteryImage = findViewById(R.id.battery);

    }

    public void decreaseLevel(View view) {
        if (mCharging < 1){
            mCharging = 0;
        }else {
            mCharging--;
        }
       result(mCharging);


    }

    public void increaseLevel(View view) {
       if (mCharging > 9){
           mCharging = 10;
       }else {
           mCharging++;
       }
        result(mCharging);


    }
    public void result(int charge){
        mBatteryImage.setImageLevel(charge);
        mTextView.setText(String.valueOf(charge));
    }

}