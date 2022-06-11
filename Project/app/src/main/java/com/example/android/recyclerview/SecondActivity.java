package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SecondActivity extends AppCompatActivity{

    private Spinner mSpinner;
    private TextView mTextviewTime;
    private EditText mPurposeMTV;
    private EditText mCostMTV;
    private EditText mExMTV;
    private String month_string;
    private String day_string;
    private String year_string;
    private String hour_string;
    private String minute_string;
    private String second_string;
    private String timeMessage;
    private static final String NEW_KEY = "New";
    private static final String SPLIT_CHAR2 = "#$";

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mPurposeMTV = findViewById(R.id.purposeMTV);
        mCostMTV = findViewById(R.id.costMTV);
        mExMTV = findViewById(R.id.exMTV);
        mTextviewTime = findViewById(R.id.tvTime);
        GetNowTime();
        mSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.http_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    public void ClickFinish(View view) {
        String purpose = mPurposeMTV.getText().toString();
        String cost = mCostMTV.getText().toString();
        String ex = mExMTV.getText().toString();
        if(Objects.equals(purpose, "") || Objects.equals(cost, "")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提醒");
            builder.setMessage("目的與金額不可留空");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "我知道了",
                    (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            String extra = timeMessage + "\n" + purpose + SPLIT_CHAR2 + cost + SPLIT_CHAR2 + ex;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NEW_KEY,extra);
            startActivity(intent);
        }
    }

    public void ClickNow(View view) {
        GetNowTime();
        makeToast("已更新時間為\n" + timeMessage);
    }

    public void ClickDate(View view) {
        TimePickerFragment Time = new TimePickerFragment();
        Time.show(getSupportFragmentManager(),"timePicker");
        DatePickerFragment Date = new DatePickerFragment();
        Date.show(getSupportFragmentManager(),"datePicker");
    }

    @SuppressLint("DefaultLocale")
    public void processDatePickerResult(int year, int month, int day){
        month_string = String.format("%02d", month + 1); // 月份使從0開始的，要加1
        day_string = String.format("%02d", day);
        year_string = String.format("%04d", year);
    }

    @SuppressLint("DefaultLocale")
    public void processTimePickerResult(int hour, int minute){
        hour_string = String.format("%02d",hour);
        minute_string = String.format("%02d", minute);
        second_string = "00";
        timeMessage = year_string + "/" + month_string + "/" + day_string + " "
                + hour_string + ":" + minute_string + ":" + second_string;
        mTextviewTime.setText(timeMessage);
        makeToast("已更新時間為\n" + timeMessage);
    }

    @SuppressLint("SimpleDateFormat")
    public void GetNowTime(){
        year_string = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());
        month_string = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        day_string = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        hour_string = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        minute_string = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
        second_string = new SimpleDateFormat("ss").format(Calendar.getInstance().getTime());
        timeMessage = year_string + "/" + month_string + "/" + day_string + " "
                + hour_string + ":" + minute_string + ":" + second_string;
        mTextviewTime.setText(timeMessage);
    }

    public void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}