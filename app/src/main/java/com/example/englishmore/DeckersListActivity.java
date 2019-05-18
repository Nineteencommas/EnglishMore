package com.example.englishmore;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class DeckersListActivity extends AppCompatActivity {
    ArrayList<Integer> mastered = new ArrayList<>();
    ArrayList<Integer> total = new ArrayList<>();
    DeckerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mastered.add(1);
        total.add(1);
        mastered.add(2);
        total.add(2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckers_list);
        RecyclerView mRecyclerView = findViewById(R.id.deckerRecycle);
        adapter = new DeckerAdapter(mastered,total,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }

    public void setAllProgress()
    {

    }

    public void setOneProgress()
    {

    }
}