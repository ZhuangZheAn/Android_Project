package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    /*Input Keys*/
    private static final String DATA = "data";
    private static final String POSITION = "position";
    /*Output Keys*/
    private static final String NEW_KEY = "New";
    private static final String POS_KEY = "Pos";

    private static final String EXPENSE = "expense";
    private static final String INCOME = "income";
    private static final int REQUEST_COST_VOICE = 0;
    private static final int REQUEST_EX_VOICE = 1;
    private static final String SPLIT_CHAR2 = "#%";
    private TextView mDate;
    private TextView mBalance;
    private TextView mType;
    private TextView mEx;

    private TextView mTextviewTime;
    private EditText mCostMTV;
    private EditText mExMTV;
    private String timeMessage;
    private String expenseOrIncome;

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

    public void ClickRevise(View view) {
    }

    public void ClickApply(View view) {
        String cost = mCostMTV.getText().toString();
        String ex = mExMTV.getText().toString();
        if(Objects.equals(cost, "")){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提醒");
            builder.setMessage("金額不可留空");
            builder.setCancelable(true);
            builder.setPositiveButton(
                    "我知道了",
                    (dialog, id) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
        }
        else{
            if(expenseOrIncome == null) expenseOrIncome = EXPENSE;
            if(Objects.equals(ex, "")) ex = "no message";
            String extra = timeMessage + SPLIT_CHAR2 + expenseOrIncome + SPLIT_CHAR2 + cost + SPLIT_CHAR2 + ex;
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(NEW_KEY,extra);
            intent.putExtra(POS_KEY,extra);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COST_VOICE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Toast.makeText(this,spokenText,Toast.LENGTH_SHORT).show();
            mCostMTV.setText(spokenText.replaceAll("[^0-9]",""));
        }
        else if (requestCode == REQUEST_EX_VOICE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            Toast.makeText(this,spokenText,Toast.LENGTH_SHORT).show();
            mExMTV.setText(spokenText);
        }
    }

    public void ClickDate(View view) {
        TimePickerFragment Time = new TimePickerFragment();
        Time.show(getSupportFragmentManager(),"timePicker");
        DatePickerFragment Date = new DatePickerFragment();
        Date.show(getSupportFragmentManager(),"datePicker");
    }


    public void makeToast(String msg){ Toast.makeText(this, msg, Toast.LENGTH_SHORT).show(); }
    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.expenseRB:
                if (checked){
                    expenseOrIncome = EXPENSE;
                    makeToast(getString(R.string.expenseRB_toast));
                }
                break;
            case R.id.incomeRB:
                if (checked){
                    expenseOrIncome = INCOME;
                    makeToast(getString(R.string.incomeRB_toast));
                }
                break;
            default:
                break;
        }
    }
}