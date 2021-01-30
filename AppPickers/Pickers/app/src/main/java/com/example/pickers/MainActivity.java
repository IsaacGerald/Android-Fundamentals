package com.example.pickers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*---------------------Embedded date picker ------------------------------------------------*/
        mDatePicker = findViewById(R.id.datePicker);
        Calendar calendar = Calendar.getInstance();
        mDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
    }

    /*-------------------Dialog Fragment datePicker ---------------------------------------------*/
    public void showDatePicker(View view) {
        DatePickerFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), getString(R.string.datePicker));

    }

    public void processDatePickerResult(int year, int month, int dayOfMonth) {

        String year_string = Integer.toString(year);
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(dayOfMonth);

        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        Toast.makeText(this, dateMessage, Toast.LENGTH_SHORT).show();

    }

    /*-----------------------------Embedded datePicker-------------------------------------------*/
    public void confirmEmbeddedDate(View view) {

        int month = mDatePicker.getMonth() + 1;
        int year = mDatePicker.getYear();
        int day = mDatePicker.getDayOfMonth();

        String monthString = Integer.toString(month);
        String yearString = Integer.toString(year);
        String dayString = Integer.toString(day);

        String dateMessage = (dayString + "/" + monthString + "/" + yearString);
        Toast.makeText(this, dateMessage, Toast.LENGTH_SHORT).show();
    }
/*----------------------------Dialog Fragment Time Picker-------------------------------------------*/
    public void showTimePicker(View view) {
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), getString(R.string.timePicker));
    }

    public void processTimePickerResult(int hour, int minute){

        String hourString = Integer.toString(hour);
        String minuteString = Integer.toString(minute);

        String timeMessage = (hourString + " : " + minuteString);
        Toast.makeText(this, timeMessage, Toast.LENGTH_SHORT).show();

    }
}