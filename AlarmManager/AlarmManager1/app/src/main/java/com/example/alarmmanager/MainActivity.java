package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public static final long INTERVAL_ONE_MINUTE = 1 * 60 * 1000;
    public final static Double EXACT_TIME = 11.11;
    private NotificationManager mNotifyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        long repeatInterval = INTERVAL_ONE_MINUTE;
        long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
        long triggerTimeAlarm = 32796000;
        long time;

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 11);
        c.set(Calendar.MINUTE, 11);

        Calendar now = Calendar.getInstance();
        if (!now.before(c)) {
            c.add(Calendar.DATE, 1);
        }
        time = c.getTimeInMillis();

        ToggleButton toggleBtn = (ToggleButton) findViewById(R.id.alarmToggle);
        Button alarmBtn = (Button)findViewById(R.id.nextAlarm);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        boolean alarmUp =(PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null );
        toggleBtn.setChecked(alarmUp);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(
                this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                toggleBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if (isChecked){
                    if (alarmManager != null){
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
                            alarmManager.set(AlarmManager.RTC_WAKEUP, time, notifyPendingIntent);
                        }

                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, notifyPendingIntent);

//                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime , repeatInterval,
//                                notifyPendingIntent);

                    }
                    toastMessage = getString(R.string.stand_up_alarm_on);
                }else {
                    if (alarmManager != null){
                        alarmManager.cancel(notifyPendingIntent);
                    }
                    mNotifyManager.cancelAll();
                    toastMessage =getString(R.string.stand_up_alarm_off);
                }

                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alarmManager != null){
                    AlarmManager.AlarmClockInfo alarmClockInfo = alarmManager.getNextAlarmClock();
                    if (alarmClockInfo != null){
                        long nextAlarmTime = alarmClockInfo.getTriggerTime();
                        Date nextAlarmDate = new Date(nextAlarmTime);
                        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm", Locale.getDefault());
                       String date = df.format(nextAlarmDate);

                        Toast.makeText(MainActivity.this, "Alarm clock is set to " + date, Toast.LENGTH_SHORT).show();
                        //format alarm time

                    }else {
                        Toast.makeText(MainActivity.this, "Alarm clock not set", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });





        createNotificationChannel();
    }


    private void createNotificationChannel(){
        mNotifyManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Stand uo notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }

    }


}