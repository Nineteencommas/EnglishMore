package com.example.englishmore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class DeckersListActivity extends AppCompatActivity {
    ArrayList<Integer> mastered = new ArrayList<>();
    ArrayList<Integer> total = new ArrayList<>();
    String topic;
    int deckerNum;
    DeckerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = this.getIntent().getExtras();
        topic= bundle.getString("topic");// get the string of the name of the topic
        deckerNum = bundle.getInt("deckerNum");// get the integer of
        getInfoFromPreferrence();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deckers_list);
        RecyclerView mRecyclerView = findViewById(R.id.deckerRecycle);
        adapter = new DeckerAdapter(mastered,total,topic,this);
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

    public void getInfoFromPreferrence()
    {
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("com.example.englishmore.topicAndDeckerInfo",MODE_PRIVATE);
        int i = 0;
        for( i = 1;i<= deckerNum; i++)
        {
            total.add(preferences.getInt(topic+"Decker"+i,0));
        }
        preferences = getApplicationContext().getSharedPreferences("com.example.englishmore.userProgress",MODE_PRIVATE);
        for( i = 1; i<= deckerNum; i++)
        {
            mastered.add(preferences.getInt(topic+"Decker"+i+"num",0));
        }

    }
}