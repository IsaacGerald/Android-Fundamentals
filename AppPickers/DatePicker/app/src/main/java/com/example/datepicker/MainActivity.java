package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDatePicker(View view) {
        DialogFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(), getString(R.string.datePicker));
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month + 1);
        String year_string = Integer.toString(year);
        String day_string = Integer.toString(day);
        String date_message = (" " + month_string + " /" + day_string + " /" + year_string);

        Toast.makeText(this, getString(R.string.date) + date_message, Toast.LENGTH_SHORT).show();
    }
    public void processTimePickerResult(int hour, int minutes){
        String hour_string = Integer.toString(hour);
        String minutes_string = Integer.toString(minutes);
        String time_message = (" " + hour_string + ":" + minutes_string);

        Toast.makeText(this, getString(R.string.time_message) + time_message, Toast.LENGTH_SHORT).show();
    }

    public void showTimePicker(View view) {
         DialogFragment dialogFragment = new TimePickerFragment();
         dialogFragment.show(getSupportFragmentManager(), getString(R.string.timePicker));

    }
}