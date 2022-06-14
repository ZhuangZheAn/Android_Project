package com.example.android.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    private static final String DATA = "data";
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

    }
}