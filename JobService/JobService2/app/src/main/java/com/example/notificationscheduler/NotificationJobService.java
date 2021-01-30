package com.example.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class NotificationJobService  extends JobService {
    private static final String TAG = "NotificationJobService";
    private static final String CHANNEL_PRIMARY_ID = "primary_notification_channel";
    NotificationManager mNotificationManager;
//    private MyAsyncTask mMyAsyncTask;
    @Override
    public boolean onStartJob(JobParameters params) {

/*------------------------------Notification JobService-----------------------------------------*/
//        createNotificationChannel();
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent schedulerPendingIntent = PendingIntent.getActivity(
//                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_PRIMARY_ID);
//        notification.setContentTitle("Job Service");
//        notification.setContentText("Your job ran to completion!");
//        notification.setSmallIcon(R.drawable.ic_job_running);
//        notification.setContentIntent(schedulerPendingIntent);
//        notification.setPriority(NotificationCompat.PRIORITY_HIGH);
//        notification.setDefaults(NotificationCompat.DEFAULT_ALL);
//        notification.setAutoCancel(true);
//
//        mNotificationManager.notify(0, notification.build());


/*--------------------------------------Coding Challenge Async Task-------------------------------*/
//        mMyAsyncTask = new MyAsyncTask(this);
//        mMyAsyncTask.execute(params);
//        Log.d(TAG, "onStartJob: AsyncTask is started ");


/*--------------------------------------------Homework Download Schedule-----------------------------------------*/
        createNotificationChannel();
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent schedulerPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_PRIMARY_ID);
        notification.setContentTitle("Performing Work");
        notification.setContentText("Download in progress");
        notification.setSmallIcon(R.drawable.ic_android);
        notification.setContentIntent(schedulerPendingIntent);
        notification.setPriority(NotificationCompat.PRIORITY_HIGH);
        notification.setDefaults(NotificationCompat.DEFAULT_ALL);
        notification.setAutoCancel(true);

        mNotificationManager.notify(0, notification.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {

/*-----------------------------------Coding Challenge Async Task------------------------------*/
//        if (mMyAsyncTask != null){
//            mMyAsyncTask.cancel(true);
//            Toast.makeText(this, "Job Interrupted", Toast.LENGTH_SHORT).show();
//        }
//        return true;
        return false;
    }


    private void createNotificationChannel(){
      mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
          NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_PRIMARY_ID,
                  "Job Service Notification", NotificationManager.IMPORTANCE_HIGH);
          notificationChannel.setLightColor(Color.RED);
          notificationChannel.setDescription("Notification from JobService");
          notificationChannel.enableLights(true);
          notificationChannel.enableVibration(true);
          mNotificationManager.createNotificationChannel(notificationChannel);
      }


    }

//    public class MyAsyncTask extends AsyncTask<JobParameters, Void, Boolean>{
//
//        JobParameters mJobParameters;
//        private  JobService mJobService;
//        public MyAsyncTask(JobService jobService) {
//            mJobService = jobService;
//        }
//
//
//
//        @Override
//        protected Boolean doInBackground(JobParameters... jobParameters) {
//
//            try {
//                mJobParameters = jobParameters[0];
//                Thread.sleep(10 * 1000);
//                return true;
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                return false;
//            }
//
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//            if (success){
//                Toast.makeText(mJobService, "Job finished", Toast.LENGTH_SHORT).show();
//            }
//            mJobService.jobFinished(mJobParameters, !success);
//
//
//        }
//    }
}
