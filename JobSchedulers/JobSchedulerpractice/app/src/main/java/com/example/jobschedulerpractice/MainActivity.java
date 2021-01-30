package com.example.jobschedulerpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintSet;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public static final int JOB_ID = 0;
    private JobScheduler mScheduler;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

    }
    public void cancelJob(View view){
        if (mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Cancelled Job", Toast.LENGTH_SHORT).show();
        }
    }

    public void scheduleDownload(View view) {
        ComponentName serviceName = new ComponentName(getPackageName(),
                DownloadJobService.class.getName());
        long flexMillis = 5 * 60 * 1000;
        long intervalMillis = 24 * 60 * 60 * 1000;
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setPeriodic(intervalMillis, flexMillis);

        JobInfo jobInfo = builder.build();
        mScheduler.schedule(jobInfo);
        Toast.makeText(this, "Job scheduled, Job will run when constraints are met", Toast.LENGTH_SHORT).show();
       // mTextView.setText(R.string.constraints_message);

    }
}