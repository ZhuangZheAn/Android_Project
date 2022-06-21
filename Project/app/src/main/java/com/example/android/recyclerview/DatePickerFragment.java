package com.example.android.recyclerview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c;
        int year = 0;
        int month = 0;
        int day = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }
        else{
            Toast.makeText(getContext(),"can't open calander",Toast.LENGTH_LONG).show();
        }
        return new DatePickerDialog(getActivity(),this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker,int year , int month, int day){
        try{
            DetailActivity activity = (DetailActivity) getActivity();
            activity.processDatePickerResult(year, month, day);
        }catch(Exception e){
            NewActivity activity = (NewActivity) getActivity();
            activity.processDatePickerResult(year, month, day);
        }
    }
}