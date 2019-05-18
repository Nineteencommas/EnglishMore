package com.example.englishmore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TopicListActivity extends AppCompatActivity {
    TopicsAdapter adapter;
    ArrayList<Integer> mastered = new ArrayList<>();
    ArrayList<Integer> total = new ArrayList<>();
    ArrayList<String> topics = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mastered.add(200);
        mastered.add(300);
        mastered.add(500);
        total.add(201);
        total.add(301);
        total.add(501);
        topics.add("GRE");
        topics.add("TOEFL");
        topics.add("SAT");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);
        RecyclerView mRecyclerView = findViewById(R.id.topicRecycle);
        adapter = new TopicsAdapter(mastered,topics, total,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}
