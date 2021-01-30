package com.jwhh.jim.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

public class NoteBackupIntentJobService extends JobIntentService {

    public static final String EXTRA_COURSE_ID = "com.jwhh.jim.notekeeper.extra.COURSE_ID";
    public static final int JOB_ID = 1000;

   final Handler mHandler = new Handler(Looper.getMainLooper());
    static void enqueueWork(Context context, Intent work){
        enqueueWork(context, NoteBackupIntentJobService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        if (intent != null){
            String backupCourseId = intent.getStringExtra(EXTRA_COURSE_ID);
            NoteBackup.doBackup(this, backupCourseId);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showToast("Finished backing up");
    }

    //Helper for showing toast
    void showToast(final CharSequence text){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
               Toast.makeText(NoteBackupIntentJobService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

}