package com.gyz.androidsamples.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.gyz.androidsamples.R;

import java.util.Calendar;

/**
 * Created by guoyizhe on 16/8/1.
 * 邮箱:gyzboy@126.com
 */
public class ASDataDialog extends Activity {
    private TextView mDateDisplay;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    static final int TIME_12_DIALOG_ID = 0;
    static final int TIME_24_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_datadialog);

        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);

        setDialogOnClickListener(R.id.pickDate, DATE_DIALOG_ID);
        setDialogOnClickListener(R.id.pickTime12, TIME_12_DIALOG_ID);
        setDialogOnClickListener(R.id.pickTime24, TIME_24_DIALOG_ID);

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        updateDisplay();
    }

    private void setDialogOnClickListener(int buttonId, final int dialogId) {
        Button b = (Button) findViewById(buttonId);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(dialogId);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_12_DIALOG_ID:
            case TIME_24_DIALOG_ID:
                return new TimePickerDialog(this,
                        mTimeSetListener, mHour, mMinute, id == TIME_24_DIALOG_ID);
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case TIME_12_DIALOG_ID:
            case TIME_24_DIALOG_ID:
                ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
                break;
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay).append(" ")
                        .append(pad(mHour)).append(":")
                        .append(pad(mMinute)));
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateDisplay();
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
