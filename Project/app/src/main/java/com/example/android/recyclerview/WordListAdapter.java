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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Objects;

public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    private final LayoutInflater mInflater;
    private static final String SPLIT_CHAR2 = "#%";

    private static final String EXPENSE = "expense";
    private static final String INCOME = "income";
    private static final String DATA = "data";

    class WordViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        public final TextView wordItemView;
        public final TextView mbalance;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            mbalance = itemView.findViewById(R.id.balance);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.

            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            intent.putExtra(DATA,element);
            view.getContext().startActivity(intent);

            mAdapter.notifyDataSetChanged();
        }
    }

    public WordListAdapter(Context context, LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.wordlist_item, parent, false);
        return new WordViewHolder(mItemView, this);
    }

    @SuppressLint({"SetTextI18n", "ObsoleteSdkInt"})
    @Override
    public void onBindViewHolder(WordListAdapter.WordViewHolder holder,
                                 int position) {
        String mCurrent = mWordList.get(position);
        String[] arr = mCurrent.split(SPLIT_CHAR2);

        if(Objects.equals(arr[1], EXPENSE)){
            holder.mbalance.setText("支出 " + arr[2] + " $");
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.mbalance.setBackgroundDrawable(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.expense_background) );
            } else {
                holder.mbalance.setBackground(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.expense_background));
            }
        }
        else{
            holder.mbalance.setText("收入 " + arr[2] + " $");
            if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.mbalance.setBackgroundDrawable(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.income_background) );
            } else {
                holder.mbalance.setBackground(ContextCompat.getDrawable(holder.mbalance.getContext(), R.drawable.income_background));
            }
        }
        holder.wordItemView.setText(" " + arr[3]);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }
}
