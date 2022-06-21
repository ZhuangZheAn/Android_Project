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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class NewActivity extends AppCompatActivity{

    private TextView mTextviewTime;
    private EditText mCostMTV;
    private EditText mExMTV;
    private String month_string;
    private String day_string;
    private String year_string;
    private String hour_string;
    private String minute_string;
    private String second_string;
    private String timeMessage;
    private String expenseOrIncome;
    /*Output Keys*/
    private static final String NEW_KEY = "New";
    private static final String ACT_KEY = "Activity";
    private static final String DEL_KEY = "delete_position";

    private static final String SPLIT_CHAR2 = "#%";
    private static final String EXPENSE = "expense";
    private static final String INCOME = "income";
    private static final int REQUEST_COST_VOICE = 0;
    private static final int REQUEST_EX_VOICE = 1;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Toolbar toolbar = findViewById(R.id.Second_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mCostMTV = findViewById(R.id.costMTV);
        mExMTV = findViewById(R.id.exMTV);
        mTextviewTime = findViewById(R.id.tvTime);
        GetNowTime();
        Intent intent = getIntent();
        String tmp = intent.getStringExtra("tmp");
        if(tmp != null){
            if(Objects.equals(tmp, "back")){
                Intent intent1 = new Intent(NewActivity.this,MainActivity.class);
                startActivity(intent1);
            }
            else if(tmp.substring(0,6).equals("delete")){
                Intent intent1 = new Intent(NewActivity.this,MainActivity.class);
                intent1.putExtra(DEL_KEY, tmp.substring(6));
                intent1.putExtra(ACT_KEY,"WordListAdapter");
                startActivity(intent1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,0,0,"").setIcon(R.drawable.ic_check).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
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
                    intent.putExtra(NEW_KEY, extra);
                    intent.putExtra(ACT_KEY, "NewActivity");
                    startActivity(intent);
                }
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    //取得現在時間

    public void ClickNow(View view) {
        GetNowTime();
        makeToast("已更新時間為\n" + timeMessage);
    }

    //選擇時間

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

    // radio group button

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

    //語音
    public void listenerOnClick(View view) {
        displaySpeechRecognizer(REQUEST_COST_VOICE);
    }

    public void listenerOnClick2(View view) {
        displaySpeechRecognizer(REQUEST_EX_VOICE);
    }

    private void displaySpeechRecognizer(int requset_code) {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        if(activities.size() != 0){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            startActivityForResult(intent, requset_code);
        }
        else{
            makeToast("你無法使用語音");
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

    //quick make toast

    public void makeToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}