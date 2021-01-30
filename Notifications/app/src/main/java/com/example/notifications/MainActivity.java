package com.example.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String PRIMARY_CHANNEL_ID  = "primary_notification_channel";
    private static final int NOTIFICATION_ID  = 0;
    private static final String ACTION_UPDATE_NOTIFICATION  = "com.example.notifications.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION = "com.example.notifications.ACTION_CANCEL_NOTIFICATION";
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private NotificationManager mNotifyManager;
    private Button mNotifyMe;
    private Button mUpdate;
    private Button mCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotifyMe = (Button)findViewById(R.id.notify_me);
        mUpdate = (Button)findViewById(R.id.update_me);
        mCancel = (Button)findViewById(R.id.cancel_me);

        setNotificationButtonState(true, false, false);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_UPDATE_NOTIFICATION);
        filter.addAction(ACTION_CANCEL_NOTIFICATION);

        registerReceiver(mReceiver, filter);
        ;



        mNotifyMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });


        createNotificationChannel();
    }

    void setNotificationButtonState(Boolean isNotifyEnabled, Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled){
        mNotifyMe.setEnabled(isNotifyEnabled);
        mUpdate.setEnabled(isUpdateEnabled);
        mCancel.setEnabled(isCancelEnabled);
    }

    private void cancelNotification() {
        setNotificationButtonState(true, false, false);

        mNotifyManager.cancel(NOTIFICATION_ID);
    }

    private void updateNotification() {
        setNotificationButtonState(false, false, true );
//        Bitmap androidImage = BitmapFactory
//                .decodeResource(getResources(), R.drawable.mascot_1);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        Intent cancelIntent = new Intent((ACTION_CANCEL_NOTIFICATION));
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);
        notifyBuilder.setDeleteIntent(cancelPendingIntent);
//        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//        .bigPicture(androidImage)
//        .setBigContentTitle("Notification updated"));
        notifyBuilder.setStyle(new NotificationCompat.InboxStyle()
        .addLine("Here is the first one")
        .addLine("This is the second one")
        .addLine("Yaay last one")
        .setBigContentTitle("Sanitizer")
        .setSummaryText("The new 98% alcohol based sanitizer"));

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    private void sendNotification() {
        setNotificationButtonState(false, true, true);
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.addAction(R.drawable.ic_information, "LEARN MORE", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    }

    private void createNotificationChannel(){
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //create notification channel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Amara Notification", NotificationManager
            .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Amara");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        //Setting pending intent
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Building notification
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
        .setContentTitle("Antibacterial sanitizer!")
                .setContentText("Contains Lemon Musk and alcohol")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver(){

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String actionIntent = intent.getAction();

            if (actionIntent != null ){
                switch (actionIntent){
                    case ACTION_UPDATE_NOTIFICATION:
                        updateNotification();
                        break;
                    case ACTION_CANCEL_NOTIFICATION:
                        cancelNotification();
                        break;
                }
            }

        }
    }

}

