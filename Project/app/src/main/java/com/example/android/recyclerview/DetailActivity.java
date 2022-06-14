package com.example.android.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String DATA = "data";
    private static final String SPLIT_CHAR2 = "#%";
    private static final String POSITION = "position";
    private TextView mDate;
    private TextView mBalance;
    private TextView mType;
    private TextView mEx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDate = findViewById(R.id.dateTV);
        mBalance = findViewById(R.id.balanceTV);
        mType = findViewById(R.id.typeTV);
        mEx = findViewById(R.id.exTV);
        Intent intent = getIntent();
        String element = intent.getStringExtra(DATA);
        String position = intent.getStringExtra(POSITION);
        String[] arr = element.split(SPLIT_CHAR2);
        mDate.setText(arr[0]);
        mType.setText(arr[1]);
        mBalance.setText(arr[2]);
        mEx.setText(arr[3]);
    }
}