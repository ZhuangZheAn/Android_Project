package com.example.android.recyclerview;

import static android.content.Context.MODE_PRIVATE;
import static java.util.Arrays.sort;

import android.annotation.SuppressLint;
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

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class ListExpenseFragment extends Fragment {

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
    private static final String SPLIT_CHAR = "!@";
    private static final String SPLIT_CHAR2 = "#%";

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.android.recyclerview";
    private String datas;
    private Integer data_size;


    public ListExpenseFragment() {
        // Required empty public constructor
    }

    public static ListExpenseFragment newInstance(String param1, String param2) {
        ListExpenseFragment fragment = new ListExpenseFragment();
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
        return inflater.inflate(R.layout.tab_list_expense, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        data_size = mPreferences.getInt(DATASIZE_KEY, 0);
        datas = mPreferences.getString(DATAS_KEY, "");
        String[] arr;
        String type;
        if (data_size != 0) {
            arr = datas.split(SPLIT_CHAR);
            sort(arr, Collections.reverseOrder());
            for (int i = 0; i < data_size; i++) {
                type = arr[i].split(SPLIT_CHAR2)[1];
                if (Objects.equals(type, "expense")) {
                    mWordList.addLast(arr[i]);
                    mPositionList.addLast(i);
                }
            }
            mRecyclerView = view.findViewById(R.id.RV);
            mAdapter = new WordListAdapter(view.getContext(), mWordList, mPositionList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        if (data_size == 0) {
            view.findViewById(R.id.hintExpense).setVisibility(View.VISIBLE);
            view.findViewById(R.id.expense_money).setVisibility(View.INVISIBLE);
        } else {
            view.findViewById(R.id.hintExpense).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.expense_money).setVisibility(View.VISIBLE);
        }
        long money_long = 0;
        arr = datas.split(SPLIT_CHAR);
        for (int i = 0; i < data_size; i++) {
            if (Objects.equals(arr[i].split(SPLIT_CHAR2)[1], "expense")) {
                money_long += Long.parseLong(arr[i].split(SPLIT_CHAR2)[2]);
            }
        }
        DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0");
        money = view.findViewById(R.id.expense_money);
        money.setText("  總支出      $ " + fmt.format(money_long));
    }
}