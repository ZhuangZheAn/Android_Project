/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.Objects;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    private final LinkedList<Integer> mRealPositionList;
    private final LayoutInflater mInflater;

    private static final String SPLIT_CHAR2 = "#%";
    private static final String EXPENSE = "expense";
    private static final String INCOME = "income";
    private static final String DATA = "data";
    private static final String POSITION = "position";

    long money;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public final TextView wordItemView;
        public final TextView mbalance;
        public final TextView mtime;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            mbalance = itemView.findViewById(R.id.balance);
            mtime = itemView.findViewById(R.id.wordlistTime);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            Integer realPosition = mRealPositionList.get(mPosition);
            // Change the word in the mWordList.
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DATA,element);
            intent.putExtra(POSITION,realPosition);
            view.getContext().startActivity(intent);
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View view) {
            int mPosition = getLayoutPosition();
            String element = mWordList.get(mPosition);
            Integer realPosition = mRealPositionList.get(mPosition);
            PopupMenu popupMenu = new PopupMenu(view.getContext(), itemView);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    CharSequence title = menuItem.getTitle();
                    if (title.equals("編輯")) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra(DATA, element);
                        intent.putExtra(POSITION, mPosition);
                        view.getContext().startActivity(intent);
                        mAdapter.notifyDataSetChanged();
                    }
                    else if (title.equals("刪除")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("警告!!");
                        builder.setMessage("這個動作會刪除目前選擇的資料");
                        builder.setCancelable(true);
                        builder.setPositiveButton(
                                "取消",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        builder.setNegativeButton(
                                "確定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(view.getContext(),NewActivity.class);
                                        intent.putExtra("tmp","delete" + realPosition);
                                        view.getContext().startActivity(intent);
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    return true;
                }
            });
            // Showing the popup menu
            popupMenu.show();
            return false;
        }
    }
    public WordListAdapter(Context context, LinkedList<String> wordList, LinkedList<Integer> realPosition) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
        this.mRealPositionList = realPosition;
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(
                R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @SuppressLint({"SetTextI18n", "ObsoleteSdkInt"})
    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder,
                                 int position) {
        holder.mbalance.bringToFront();
        String mCurrent = mWordList.get(position);
        String[] arr = mCurrent.split(SPLIT_CHAR2);
        String[] unit = {"","K","M","B","T"};
        long balance = Long.parseLong(arr[2]);
        String balance_with_unit = "";
        for(int i = 4; i >= 0; i--){
            if(balance >= Math.pow(10,i*3)){
                BigDecimal bd = new BigDecimal(balance / Math.pow(10,i*3)).setScale(1, RoundingMode.DOWN);
                balance_with_unit = bd.doubleValue() + unit[i] + " $";
                if(i <= 1) balance_with_unit = balance + " $";
                break;
            }
        }
        if(Objects.equals(arr[1], EXPENSE)){
            holder.mbalance.setText("- " + balance_with_unit);
            holder.mbalance.setTextColor(Color.BLACK);
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.mbalance.setBackgroundDrawable(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.expense_background) );
            } else {
                holder.mbalance.setBackground(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.expense_background));
            }
            money -= balance;
        }
        else{
            holder.mbalance.setText("+ " + balance_with_unit);
            holder.mbalance.setTextColor(Color.WHITE);
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.mbalance.setBackgroundDrawable(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.income_background) );
            } else {
                holder.mbalance.setBackground(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.income_background));
            }
        }
        holder.mtime.setText(arr[0]);
        holder.wordItemView.setText(" " + arr[3]);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
