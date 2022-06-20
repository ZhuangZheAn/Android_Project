package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

public class DeleteActivity extends AppCompatActivity {

    private String before_date;
    private String before_year_string;
    private String before_month_string;
    private String before_day_string;
    private String after_date;
    private String after_year_string;
    private String after_month_string;
    private String after_day_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = findViewById(R.id.toolbarDel);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        before_date = "";
        after_date = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**itemId為稍後判斷點擊事件要用的*/
        menu.add(0,0,0,"").setIcon(R.drawable.ic_check).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("提醒");
                    builder.setMessage("金額不可留空");
                    builder.setCancelable(true);
                    builder.setPositiveButton(
                            "我知道了",
                            (dialog, id) -> dialog.cancel());
                    AlertDialog alert = builder.create();
                    alert.show();

                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    public void ClickDate(View view) {
        DatePickerFragment Date = new DatePickerFragment();
        Date.show(getSupportFragmentManager(),"datePicker");
    }

    @SuppressLint("DefaultLocale")
    public void processDatePickerResult(int year, int month, int day){
        // add something here
    }
}