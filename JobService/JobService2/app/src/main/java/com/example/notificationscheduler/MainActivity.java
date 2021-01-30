package com.example.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {

    private RadioGroup mNetworkOptions;
    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;
    private static final int DOWNLOAD_JOB_ID = 1;
    private SwitchMaterial mDeviceIdleSwitch;
    private SwitchMaterial mDeviceChargingSwitch;
    private SeekBar mSeekBar;
    private TextView mSeekProgressBar;
    private int mSeekBarInteger;
    private boolean mSeekBarSet;
    private Button mDownload;
    private SwitchMaterial mChargingSwitch;
    private SwitchMaterial mWifiSwitch;
    private SwitchMaterial mIdleSwitch;
    private int mSelectedNetwork;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);
        mSeekBar = findViewById(R.id.seekBar);
        mSeekProgressBar = findViewById(R.id.seekBarProgress);
        mNetworkOptions = findViewById(R.id.network_options);


        mDownload = findViewById(R.id.downloadBtn);
        mChargingSwitch = findViewById(R.id.deviceCharging);
        mWifiSwitch = findViewById(R.id.connectWifi);
        mIdleSwitch = findViewById(R.id.phoneIdle);

        mSeekBarInteger = mSeekBar.getProgress();
        mSeekBarSet = mSeekBarInteger > 0;

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (i > 0){
                    mSeekProgressBar.setText(i + " s");
                }else {
                    mSeekProgressBar.setText("Not Set");
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

    public void scheduleJob(View view) {
        mScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int selectedNetworkId = mNetworkOptions.getCheckedRadioButtonId();
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        switch (selectedNetworkId){
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case  R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked())
                .setRequiresCharging(mDeviceChargingSwitch.isChecked());
        if (mSeekBarSet){
            builder.setOverrideDeadline(mSeekBarInteger * 1000);
        }

        boolean constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE) ||
                mDeviceIdleSwitch.isChecked() || mDeviceChargingSwitch.isChecked() || mSeekBarSet;
        if (constraintSet){
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " +
                    "the constraints are met", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }






    }

    public void cancelJob(View view) {
        if (mScheduler != null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Cancelled all", Toast.LENGTH_SHORT).show();
        }
    }

    public void scheduleDownloadJob(View view) {
        mScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        if (mWifiSwitch.isChecked()){
            mSelectedNetwork = JobInfo.NETWORK_TYPE_UNMETERED;
        }
        boolean setConstraint = mChargingSwitch.isChecked() || mWifiSwitch.isChecked() ||
                mIdleSwitch.isChecked();
        ComponentName componentName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        long flexMillis = 24 * 60 * 60 * 1000;
        JobInfo.Builder builder = new JobInfo.Builder(DOWNLOAD_JOB_ID, componentName)
                .setRequiresDeviceIdle(mIdleSwitch.isChecked())
                .setRequiresCharging(mChargingSwitch.isChecked())
                .setRequiredNetworkType(mSelectedNetwork)
                .setPeriodic(24 * 3600 * 1000, flexMillis);

        if (setConstraint){
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " +
                    "the constraints are met", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }
    }
}