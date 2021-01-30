package com.jwhh.jim.notekeeper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NoteUploaderWork extends Worker {
    private static final String TAG = NoteUploaderWork.class.getSimpleName();
    public static final String EXTRA_DATA_URI = "com.jwhh.jim.notekeeper.extras.DATA_URI";
    private WorkerParameters mParameters;
    private NoteUploader mNoteUploader;


    public NoteUploaderWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: thread " + Thread.currentThread());
        Context mContext = getApplicationContext();
        mNoteUploader = new NoteUploader(mContext);

        try{
            String stringDataUri = getInputData().getString(EXTRA_DATA_URI);
            Uri dataUri = Uri.parse(stringDataUri);
            mNoteUploader.doUpload(dataUri);

            return Result.success();

        } catch (Exception e) {
            Log.d(TAG, "doWork: Error uploading work" + e.getMessage());
            return Result.failure();
        }

    }
}
