package com.jwhh.jim.notekeeper;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteUploaderJobService extends JobService {
    private static final String TAG = NoteUploaderJobService.class.getSimpleName();

    public static final String EXTRA_DATA_URI = "com.jwhh.jim.notekeeper.extras.DATA_URI";
    private NoteUploader mNoteUploader;

    public NoteUploaderJobService() {

    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        mNoteUploader = new NoteUploader(this);
        Handler handler = new Handler(Looper.getMainLooper());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //Do background work
                Log.d(TAG, "run: Thread " + Thread.currentThread() );
                JobParameters jobParameters = params;
                String stringDataUri = jobParameters.getExtras().getString(EXTRA_DATA_URI);
                Uri dataUri = Uri.parse(stringDataUri);
                mNoteUploader.doUpload(dataUri);

                if (!mNoteUploader.isCanceled()){
                    jobFinished(jobParameters, false);
                }

            }
        });


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mNoteUploader.cancel();
        return true;
    }


}