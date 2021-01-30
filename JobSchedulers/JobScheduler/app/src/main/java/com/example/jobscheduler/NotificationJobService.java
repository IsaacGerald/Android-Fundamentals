package com.example.jobscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {

    public static final int NOTIFICATION_ID = 0;
    public static final String NOTIFICATION_CHANNEL_ID = "notification_channel_id";
    private  NotificationManager mNotificationManager;
    private MyAsyncTask mMyAsyncTask;

    @Override
    public boolean onStartJob(JobParameters params) {

//        createNotificationChannel();
//        sendNotification();
        mMyAsyncTask = new MyAsyncTask(this);
        mMyAsyncTask.execute(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        if (mMyAsyncTask != null){
            mMyAsyncTask.cancel(true);
            Toast.makeText(this, "Job Interrupted", Toast.LENGTH_SHORT).show();
        }

        return true;

    }

    public void sendNotification(){
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job ran to completion!")
                .setContentIntent(notifyPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    void createNotificationChannel(){
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Notification job service", NotificationManager.IMPORTANCE_HIGH );
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setDescription("Notification from job service");

            mNotificationManager.createNotificationChannel(notificationChannel);
        }


    }

    static class MyAsyncTask extends AsyncTask<JobParameters, Void, Boolean>{
        private JobParameters mJobParameters;
        private JobService mJobService;

        MyAsyncTask(JobService jobService) {
            mJobService = jobService;
        }

        @Override
        protected Boolean doInBackground(JobParameters... jobParameters) {

            try{
                mJobParameters = jobParameters[0];
                Thread.sleep(10000);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success){
                Toast.makeText(mJobService, "Job finished", Toast.LENGTH_SHORT).show();
            }
            mJobService.jobFinished(mJobParameters, !success);
        }
    }






}
