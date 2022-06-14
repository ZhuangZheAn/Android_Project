package com.example.android.recyclerview;

import static java.util.Arrays.sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // Tag for the intent extra.
    public static final String EXTRA_MESSAGE = "com.example.android.droidcafeoptions.extra.MESSAGE";


    /*test*/
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private static final String DATASIZE_KEY = "DataSize";
    private static final String DATAS_KEY = "Datas";
    /*Input Keys*/
    private static final String ACT_KEY = "Activity";
    private static final String NEW_KEY = "New";
    private static final String POS_KEY = "Pos";

    private static final String SPLIT_CHAR = "!@";
    private static final String SPLIT_CHAR2 = "#%";

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.recyclerview";
    private String datas;
    private Integer data_size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra(EXTRA_MESSAGE, "mOrderMessage");
                startActivity(intent);

            }
        });

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        Intent intent = getIntent();
        String req = intent.getStringExtra(MainActivity.NEW_KEY);

        data_size = mPreferences.getInt(DATASIZE_KEY,0);
        datas = mPreferences.getString(DATAS_KEY,"");
        String callBackActivity = intent.getStringExtra(MainActivity.ACT_KEY);


        if(Objects.equals(callBackActivity, "SecondActivity")){
            data_size += 1;
            String[] arr = new String[data_size];
            if(datas == ""){
                datas = req;
                arr[0] = req;
            }
            else{
                datas += SPLIT_CHAR + req;
                arr = datas.split(SPLIT_CHAR);

                sort(arr, Collections.reverseOrder());
            }
            for(int i = 0; i < data_size; i++){
                mWordList.addLast(arr[i]);
            }
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt(DATASIZE_KEY, data_size);
            preferencesEditor.putString(DATAS_KEY, datas);
            preferencesEditor.apply();
        }
        else if (Objects.equals(callBackActivity, "DetailActivity")){
            int position = Integer.parseInt(intent.getStringExtra(POS_KEY));
            String[] arr = datas.split(SPLIT_CHAR);
            arr[position] = req;
            sort(arr, Collections.reverseOrder());
            for(int i = 0; i < data_size; i++){
                mWordList.addLast(arr[i]);
            }
            SharedPreferences.Editor preferencesEditor = mPreferences.edit();
            preferencesEditor.putInt(DATASIZE_KEY, data_size);
            preferencesEditor.putString(DATAS_KEY, datas);
            preferencesEditor.apply();
        }
        else{
            if(data_size != 0){
                String[] arr = datas.split(SPLIT_CHAR);
                sort(arr, Collections.reverseOrder());
                for(int i = 0; i < data_size; i++){
                    mWordList.addLast(arr[i]);
                }
            }
        }



        //makeToast(Integer.toString(data_size);


    }

    public void reset(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告!!");
        builder.setMessage("這個動作會刪除所有已儲存的資料");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        makeToast("取消操作");
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "確定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
                        preferencesEditor.clear();
                        preferencesEditor.apply();
                        mWordList.clear();
                        mRecyclerView.setAdapter(mAdapter);
                        makeToast("已刪除所有資料");
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void makeToast(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
