package com.example.pickers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);



        return new TimePickerDialog(getActivity(), this, hour, minute, false );
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
     MainActivity mainActivity = (MainActivity)getActivity();
     if (mainActivity != null){
         mainActivity.processTimePickerResult(hourOfDay, minute);
     }

    }
}
