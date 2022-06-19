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
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListIncomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListIncomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public final LinkedList<String> mWordList = new LinkedList<>();
    public final LinkedList<Integer> mPositionList = new LinkedList<>();
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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListIncomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListIncomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListIncomeFragment newInstance(String param1, String param2) {
        ListIncomeFragment fragment = new ListIncomeFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_list_income, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        data_size = mPreferences.getInt(DATASIZE_KEY,0);
        datas = mPreferences.getString(DATAS_KEY,"");
        String[] arr;
        String type;
        if(data_size != 0){
            arr = datas.split(SPLIT_CHAR);
            sort(arr, Collections.reverseOrder());
            for(int i = 0; i < data_size; i++){
                type = arr[i].split(SPLIT_CHAR2)[1];
                if(Objects.equals(type, "income")){
                    mWordList.addLast(arr[i]);
                    mPositionList.addLast(i);
                }
            }
        }
        mRecyclerView = view.findViewById(R.id.RV);
        mAdapter = new WordListAdapter(view.getContext(), mWordList, mPositionList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}