package com.example.android.recyclerview;

import static android.content.Context.MODE_PRIVATE;

import static java.util.Arrays.sort;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListAllFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListAllFragment extends Fragment{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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



    public ListAllFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListAllFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        mAdapter = new WordListAdapter(view.getContext(), mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        Intent intent = this.getActivity().getIntent();
        String req = intent.getStringExtra(NEW_KEY);
        String cb_activity = intent.getStringExtra(ACT_KEY);
        int position = intent.getIntExtra(POS_KEY,0);

        data_size = mPreferences.getInt(DATASIZE_KEY,0);
        datas = mPreferences.getString(DATAS_KEY,"");

        int firstTimeOnCreate = mPreferences.getInt(getResources().getString(R.string.firstTimeOnCreate),0);
        String[] arr;
        if(cb_activity == null || cb_activity == ""){
            if(data_size != 0){
                arr = datas.split(SPLIT_CHAR);
                sort(arr, Collections.reverseOrder());
                for(int i = 0; i < data_size; i++){
                    mWordList.addLast(arr[i]);
                }
            }
        } else{
            Toast.makeText(view.getContext(),"ACT:" + cb_activity,Toast.LENGTH_LONG).show();
            data_size = mPreferences.getInt(DATASIZE_KEY,0);
            switch (cb_activity){
                case "SecondActivity":
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
                    for(int i = 0; i < data_size; i++){
                        mWordList.addLast(arr[i]);
                    }

                    preferencesEditor.putInt(DATASIZE_KEY, data_size);
                    preferencesEditor.putString(DATAS_KEY, datas);
                    preferencesEditor.apply();
                    break;
                case "DetailActivity":
                    arr = datas.split(SPLIT_CHAR);
                    arr[arr.length - position - 1] = req;
                    sort(arr);
                    Collections.reverse(Arrays.asList(arr));


                    for(int i = 0; i < data_size; i++){
                        mWordList.addLast(arr[i]);
                        if(i == 0) datas = arr[i];
                        else datas += SPLIT_CHAR + arr[i];
                    }


                    preferencesEditor.putString(DATAS_KEY, datas);
                    preferencesEditor.apply();
                    break;
                default:
                    Toast.makeText(view.getContext(),"UNDEFINE ACTIVITY",Toast.LENGTH_LONG);
                    break;
            }
            intent.putExtra(ACT_KEY,"");
        }

    }
}
//    mRecyclerView = (RecyclerView) getView().findViewById(R.id.RV);
//
//