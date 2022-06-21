package com.example.android.recyclerview;

import static android.content.Context.MODE_PRIVATE;

import static java.util.Arrays.sort;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class ListAllFragment extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public final LinkedList<String> mWordList = new LinkedList<>();
    public final LinkedList<Integer> mPositionList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private TextView money;
    /*Keys*/
    private static final String DATASIZE_KEY = "DataSize";
    private static final String DATAS_KEY = "Datas";
    private static final String ACT_KEY = "Activity";
    private static final String NEW_KEY = "New";
    private static final String POS_KEY = "Pos";
    private static final String DEL_KEY = "delete_position";
    private static final String SPLIT_CHAR = "!@";
    private static final String SPLIT_CHAR2 = "#%";
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.recyclerview";
    private String datas;
    private Integer data_size;

    public ListAllFragment() {
        // Required empty public constructor
    }

    public static ListAllFragment newInstance(String param1, String param2) {
        ListAllFragment fragment = new ListAllFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_list_all, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.RV);
        mAdapter = new WordListAdapter(view.getContext(), mWordList, mPositionList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        Intent intent = this.getActivity().getIntent();
        String req = intent.getStringExtra(NEW_KEY);
        String cb_activity = intent.getStringExtra(ACT_KEY);

        data_size = mPreferences.getInt(DATASIZE_KEY,0);
        datas = mPreferences.getString(DATAS_KEY,"");
        String[] arr;
        if(cb_activity == null || cb_activity == ""){
            if(data_size != 0){
                arr = datas.split(SPLIT_CHAR);
                sort(arr, Collections.reverseOrder());
                for(int i = 0; i < data_size; i++){
                    mWordList.addLast(arr[i]);
                    mPositionList.addLast(i);
                }
            }
        } else{
            data_size = mPreferences.getInt(DATASIZE_KEY,0);
            switch (cb_activity){
                case "NewActivity":
                    data_size += 1;
                    arr = new String[data_size];
                    if(datas == ""){
                        datas = req;
                        arr[0] = req;
                    }
                    else{
                        datas += SPLIT_CHAR + req;
                        arr = datas.split(SPLIT_CHAR);
                        sort(arr);
                        Collections.reverse(Arrays.asList(arr));
                    }
                    preferencesEditor.putInt(DATASIZE_KEY, data_size);
                    preferencesEditor.putString(DATAS_KEY, datas);
                    preferencesEditor.apply();

                    for(int i = 0; i < data_size; i++){
                        mWordList.addLast(arr[i]);
                        mPositionList.addLast(i);
                    }
                    break;
                case "DetailActivity":
                    int position = intent.getIntExtra(POS_KEY,0);
                    arr = datas.split(SPLIT_CHAR);
                    arr[data_size - position - 1] = req;
                    sort(arr);
                    datas = arr[0];
                    for(int i = 1; i < data_size; i++){
                        datas += SPLIT_CHAR + arr[i];
                    }
                    preferencesEditor.putString(DATAS_KEY, datas);
                    preferencesEditor.apply();
                    Collections.reverse(Arrays.asList(arr));
                    for(int i = 0; i < data_size; i++){
                        mWordList.addLast(arr[i]);
                        mPositionList.addLast(i);
                    }
                    break;
                case "WordListAdapter":
                    Integer del_pos = Integer.parseInt(intent.getStringExtra(DEL_KEY));
                    arr = datas.split(SPLIT_CHAR);
                    arr = removeElement(arr,data_size - del_pos - 1);
                    sort(arr);
                    data_size -= 1;
                    if(data_size == 0){
                        preferencesEditor.putInt(DATASIZE_KEY, data_size);
                        preferencesEditor.putString(DATAS_KEY, "");
                        preferencesEditor.apply();
                    }
                    else{
                        datas = arr[0];
                        for(int i = 1; i < data_size; i++){
                            datas += SPLIT_CHAR + arr[i];
                        }
                        preferencesEditor.putInt(DATASIZE_KEY, data_size);
                        preferencesEditor.putString(DATAS_KEY, datas);
                        preferencesEditor.apply();
                        Collections.reverse(Arrays.asList(arr));
                        for(int i = 0; i < data_size; i++){
                            mWordList.addLast(arr[i]);
                            mPositionList.addLast(i);
                        }
                    }
                    break;
                default:
                    Toast.makeText(view.getContext(),"UNDEFINE ACTIVITY",Toast.LENGTH_LONG).show();
                    break;
            }
            intent.putExtra(ACT_KEY,"");
        }
        if(data_size == 0){
            view.findViewById(R.id.hintTextView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.total_money).setVisibility(View.INVISIBLE);
        }
        else{
            view.findViewById(R.id.hintTextView).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.total_money).setVisibility(View.VISIBLE);
        }
        long money_long = 0;
        arr = datas.split(SPLIT_CHAR);
        for(int i = 0; i < data_size; i++){
            if (Objects.equals(arr[i].split(SPLIT_CHAR2)[1], "expense")){
                money_long -= Long.parseLong(arr[i].split(SPLIT_CHAR2)[2]);
            }else{
                money_long += Long.parseLong(arr[i].split(SPLIT_CHAR2)[2]);
            }
        }
        money = view.findViewById(R.id.total_money);
        money.setText("總金額      " + Long.toString(money_long) + " $");
    }
    /*array delete element by index*/
    public static String[] removeElement(String[] arr, int index ){
        String[] arrDestination = new String[arr.length - 1];
        int remainingElements = arr.length - ( index + 1 );
        System.arraycopy(arr, 0, arrDestination, 0, index);
        System.arraycopy(arr, index + 1, arrDestination, index, remainingElements);
        return arrDestination;
    }
}