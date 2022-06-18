package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    /*Input Keys*/
    private static final String DATA = "data";
    private static final String POSITION = "position";
    /*Output Keys*/
    private static final String NEW_KEY = "New";
    private static final String POS_KEY = "Pos";
    private static final String ACT_KEY = "Activity";

    private static final String EXPENSE = "expense";
    private static final String INCOME = "income";
    private static final int REQUEST_COST_VOICE = 0;
    private static final int REQUEST_EX_VOICE = 1;
    private static final String SPLIT_CHAR2 = "#%";

    private TextView mDate;
    private TextView mBalance;
    private TextView mType;
    private TextView mEx;
    private TextView mCostTV;
    private TextView mExTV;

    private Button mDateButton;
    private TextView mTextviewTime;
    private RadioGroup mRadioGroup;
    private EditText mCostMTV;
    private EditText mExMTV;
    private Button mFinishButton;
    private Button mCancelButton;

    private String timeMessage;
    private String expenseOrIncome;
    private String data;

    private String month_string;
    private String day_string;
    private String year_string;
    private String hour_string;
    private String minute_string;
    private String second_string;

    private boolean flag;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mDate = findViewById(R.id.dateTV);
        mBalance = findViewById(R.id.balanceTV);
        mType = findViewById(R.id.typeTV);
        mEx = findViewById(R.id.exTV);
        mCostTV = findViewById(R.id.textViewCost);
        mExTV = findViewById(R.id.textViewEx);
        Toolbar toolbar = findViewById(R.id.Detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mDateButton = findViewById(R.id.detail_Date_button);
        mTextviewTime = findViewById(R.id.detail_tvTime);
        mRadioGroup = findViewById(R.id.detail_radioGroup);
        mCostMTV = findViewById(R.id.detail_costMTV);
        mExMTV = findViewById(R.id.detail_exMTV);
        mFinishButton = findViewById(R.id.detail_finish_button);
        mCancelButton = findViewById(R.id.detail_cancel_button);
        flag = true;
        Intent intent = getIntent();
        data = intent.getStringExtra(DATA);
        String[] arr = data.split(SPLIT_CHAR2);
        mDate.setText(arr[0]);
        String type = getResources().getString(R.string.typeTV);
        if(Objects.equals(arr[1], "expense")) type += getResources().getString(R.string.expense);
        else if(Objects.equals(arr[1], "income")) type += getResources().getString(R.string.income);
        mType.setText(type);
        mBalance.setText(getResources().getString(R.string.costTV) + arr[2]);
        mEx.setText(getResources().getString(R.string.ex) + arr[3]);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuInflater myMenuInflater = getMenuInflater();
        if(flag)
        {
            myMenuInflater.inflate(R.menu.detail_menu1, menu);
        } else {
            myMenuInflater.inflate(R.menu.detail_menu2, menu); // here you show the other menu
        }
        invalidateOptionsMenu();
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.revise:
                flag = false;
                String[] arr = data.split(SPLIT_CHAR2);
                mDate.setVisibility(View.INVISIBLE);
                mType.setVisibility(View.INVISIBLE);
                mBalance.setVisibility(View.INVISIBLE);
                mEx.setVisibility(View.INVISIBLE);
                mCostTV.setVisibility(View.VISIBLE);
                mExTV.setVisibility(View.VISIBLE);
                mDateButton.setVisibility(View.VISIBLE);
                mTextviewTime.setVisibility(View.VISIBLE);
                mTextviewTime.setText(arr[0]);
                timeMessage = arr[0];
                mRadioGroup.setVisibility(View.VISIBLE);
                mCostMTV.setVisibility(View.VISIBLE);
                mCostMTV.setText(arr[2]);
                mExMTV.setVisibility(View.VISIBLE);
                mExMTV.setText(arr[3]);
                mFinishButton.setVisibility(View.VISIBLE);
                mCancelButton.setVisibility(View.VISIBLE);
                return true;
            case R.id.check:
                flag = true;
                Intent req = getIntent();
                int position = req.getIntExtra(POSITION,0);
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
                    intent.putExtra(POS_KEY,position);
                    intent.putExtra(ACT_KEY,"DetailActivity");
                    startActivity(intent);
                }
                return true;
            case R.id.cancel:
                flag = true;
                mDate.setVisibility(View.VISIBLE);
                mType.setVisibility(View.VISIBLE);
                mBalance.setVisibility(View.VISIBLE);
                mEx.setVisibility(View.VISIBLE);
                mCostTV.setVisibility(View.INVISIBLE);
                mExTV.setVisibility(View.INVISIBLE);
                mDateButton.setVisibility(View.INVISIBLE);
                mTextviewTime.setVisibility(View.INVISIBLE);
                mRadioGroup.setVisibility(View.INVISIBLE);
                mCostMTV.setVisibility(View.INVISIBLE);
                mExMTV.setVisibility(View.INVISIBLE);
                mFinishButton.setVisibility(View.INVISIBLE);
                mCancelButton.setVisibility(View.INVISIBLE);
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }


    public void ClickCancel(View view) {

    }

    public void ClickApply(View view) {

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

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.detail_expenseRB:
                if (checked){
                    expenseOrIncome = EXPENSE;
                    makeToast(getString(R.string.expenseRB_toast));
                }
                break;
            case R.id.detail_incomeRB:
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