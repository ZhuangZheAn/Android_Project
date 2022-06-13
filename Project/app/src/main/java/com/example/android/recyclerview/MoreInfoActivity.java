package com.example.android.recyclerview;

import static java.util.Arrays.sort;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

public class MoreInfoActivity extends AppCompatActivity {
    private TextView mTextViewTime;
    private TextView mTextViewBalance;
    private TextView mTextViewMoney;
    private TextView mTextview;
    private static final String SPLIT_CHAR = "#%";
    private static final String REQ_KEY = "New";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        mTextViewTime = findViewById(R.id.textViewTime);
        mTextViewBalance = findViewById(R.id.textViewBalance);
        mTextViewMoney = findViewById(R.id.textViewMoney);
        mTextview = findViewById(R.id.textView);
        Intent intent = getIntent();
        String req = intent.getStringExtra(MoreInfoActivity.REQ_KEY);

        if(req != null){
            String[] arr = req.split(SPLIT_CHAR);
            mTextViewTime.setText(arr[0]);
            mTextViewBalance.setText(arr[1]);
            mTextViewMoney.setText(arr[2]);
            if (arr[3]!=null) mTextview.setText(arr[3]);
        }
    }
}