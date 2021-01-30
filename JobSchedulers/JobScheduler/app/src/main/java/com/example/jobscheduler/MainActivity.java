package com.example.jobscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "notification_channel_id";
    public static final int JOB_ID = 0;
    private NotificationManager mNotificationManager;
   private JobScheduler mScheduler;
    private SwitchCompat mDeviceIdleSwitch;
    private SwitchCompat mDeviceChargingSwitch;
    private SeekBar mSeekBar;
    private TextView mSeekBarLabel;
    private TextView mSeekBarProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mSeekBar = findViewById(R.id.seekBar);
        mSeekBarLabel = findViewById(R.id.seekBarLabel1);
        mSeekBarProgress = findViewById(R.id.seekBarProgress);

         ;

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress > 0){
                    mSeekBarProgress.setText(progress + "s");
                }else {
                    mSeekBarProgress.setText(R.string.seekBar_notSet);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void cancelJob(View view) {

        if (mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void scheduleJob(View view) {

        mScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkId = networkOptions.getCheckedRadioButtonId();
        int selectedNetworkOptions = JobInfo.NETWORK_TYPE_NONE;

        switch (selectedNetworkId){
            case R.id.noNetwork:
                selectedNetworkOptions = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOptions = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOptions = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;


        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        boolean isConstraintSet = (selectedNetworkOptions != JobInfo.NETWORK_TYPE_NONE) ||
                mDeviceChargingSwitch.isChecked() || mDeviceIdleSwitch.isChecked() || seekBarSet;
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOptions)
                .setRequiresCharging(mDeviceChargingSwitch.isChecked())
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked());
        if (seekBarSet){
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }

        if (isConstraintSet) {
            //Schedule the job and notify the user
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " +
                    "the constraints are met.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Please set at least one constraint",
                    Toast.LENGTH_SHORT).show();
        }



    }
}