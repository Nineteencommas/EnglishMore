package com.example.englishmore;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
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

    public void setOneProgress(int index, Integer progress)
    {


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mastered.set(0,data.getExtras().getInt("progress"));
        adapter.notifyDataSetChanged();

    }
}