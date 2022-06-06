package com.example.android.recyclerview;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity{

    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.http_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    public void ClickFinish(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void ClickNow(View view) {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(Calendar.getInstance().getTime());

        Toast.makeText(this, timeStamp,Toast.LENGTH_SHORT).show();
    }

    public void ClickDate(View view) {
        DatePickerFragment Date = new DatePickerFragment();
        Date.show(getSupportFragmentManager(),"datePicker");
    }

    public void ClickTime(View view) {
        TimePickerFragment Time = new TimePickerFragment();
        Time.show(getSupportFragmentManager(),"timePicker");
    }

    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1); // 月份使從0開始的，要加1
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "/" + month_string + "/" + day_string);//響應信息
        Toast.makeText(this, dateMessage, Toast.LENGTH_SHORT).show(); // 響應方法
    }

    public void processTimePickerResult(int hour, int minute){
        String hour_string = Integer.toString(hour);
        String minute_string = Integer.toString(minute);
        String timeMessage = (hour_string + ":" + minute_string);//響應信息
        Toast.makeText(this, timeMessage, Toast.LENGTH_SHORT).show(); // 響應方法
    }

}