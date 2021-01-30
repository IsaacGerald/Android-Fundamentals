package com.jwhh.jim.notekeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NoteBackUpReceiverNotification extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NoteBackupIntentJobService.enqueueWork(context, intent);

    }
}