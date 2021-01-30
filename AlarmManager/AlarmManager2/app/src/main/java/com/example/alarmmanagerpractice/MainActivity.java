package com.example.alarmmanagerpractice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final int NOTIFICATION_ID = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotificationManager;
    private AlarmManager mAlarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        ToggleButton toggleButton = findViewById(R.id.alarmToggle);
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        Boolean isAlarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        toggleButton.setChecked(isAlarmUp);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Calendar triggerCal = Calendar.getInstance();
                Calendar currentCal =Calendar.getInstance();;
                triggerCal.set(Calendar.HOUR_OF_DAY, 10);
                triggerCal.set(Calendar.MINUTE, 56);
                triggerCal.set(Calendar.SECOND, 0);
                long triggerTime = triggerCal.getTimeInMillis();
                long currentTime = currentCal.getTimeInMillis();



                if (isChecked){
                    Toast.makeText(MainActivity.this, "Alarm set", Toast.LENGTH_SHORT).show();
                    if (triggerTime > currentTime){
                        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,
                                8640000, notifyPendingIntent);
                    }else {
                        triggerCal.add(Calendar.DAY_OF_MONTH, 1);
                        triggerTime = triggerCal.getTimeInMillis();
                        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,
                                86400000, notifyPendingIntent);
                    }

                }else {
                    mAlarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();
                    Toast.makeText(MainActivity.this, "Alarm disabled", Toast.LENGTH_SHORT).show();

                }
            }
        });


        createNotificationChannel();
    }


    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Make a wish", NotificationManager.IMPORTANCE_HIGH );
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Time to make a wish");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }



    }
}