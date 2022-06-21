package com.example.android.recyclerview;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;
import android.widget.Toast;


public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;
        int hour = 0;
        int minute = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
        }
        else{
            Toast.makeText(getContext(),"can't open calander",Toast.LENGTH_LONG).show();
        }
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker TimePicker, int year , int month){
        try{
            DetailActivity detailActivity = (DetailActivity) getActivity();
            detailActivity.processTimePickerResult(year, month);
        }catch (Exception e){
            NewActivity detailActivity = (NewActivity) getActivity();
            detailActivity.processTimePickerResult(year, month);
        }
    }
}
