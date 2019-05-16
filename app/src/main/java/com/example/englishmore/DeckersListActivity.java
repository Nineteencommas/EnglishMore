package com.example.englishmore;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class DeckersListActivity extends AppCompatActivity {
    Integer[] mastered={20,30,50,20,0,23};
    Integer[] total = {50,50,50,50,50,50};
    DeckerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_deckers_list);
        RecyclerView mRecyclerView = findViewById(R.id.list);
        adapter = new DeckerAdapter(mastered,total);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}