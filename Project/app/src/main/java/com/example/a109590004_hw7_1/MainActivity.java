package com.example.a109590004_hw7_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private final LinkedList<String> mWordList2 = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Put initial data into the word list.
        mWordList.addLast("牛肉麵");
        mWordList2.addLast("牛肉麵是泛指各種以燉煮過的牛肉為主要配料的湯麵食，其根源難以追溯，但與近代牛肉麵調理方式較為接近的麵食");
        mWordList.addLast("番茄炒蛋");
        mWordList2.addLast("番茄炒蛋，是東方文化常見的一道菜餚。因為它的原材料易於搜集，製作步驟也較為簡單");
        mWordList.addLast("義大利麵");
        mWordList2.addLast("義大利麵，泛指所有源自義大利的麵食。在義大利，一般會用「Pasta」來稱呼各種由麵粉及水、有時或會加入雞蛋");
        mWordList.addLast("蘿蔔貢丸湯");
        mWordList2.addLast("摃丸俗寫白字貢丸，為豬肉製做的一種肉丸，在台灣、香港、澳門、中國大陸南部的福建，廣東等地都有此食品。台灣以新竹出產的最為知名，廣東和香港等地亦非常普遍。");


        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new WordListAdapter(this, mWordList,mWordList2);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}