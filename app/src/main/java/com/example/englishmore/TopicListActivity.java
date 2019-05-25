package com.example.englishmore;

import android.content.Context;
import android.content.SharedPreferences;
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


        // can be added if mastered is 0 add a new tag
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics_list);
        RecyclerView mRecyclerView = findViewById(R.id.topicRecycle);
        adapter = new TopicsAdapter(mastered,topics, total,this);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        getTopicInfoAndTotal();
    }

public void getTopicInfoAndTotal() {
    SharedPreferences preferences = getSharedPreferences("com.example.englishmore.basicInfo", MODE_PRIVATE);
    ArrayList<String> topicList = JsonHelper.getTopicFromBasicInfo(preferences.getString("topicList", "defaultValueforTopicInfo"));
    preferences = getSharedPreferences("com.example.englishmore.topicAndDeckerInfo",MODE_PRIVATE);
    for (String each : topicList) {
        total.add((Integer)preferences.getInt(each+"TotalWords", 0));
        topics.add(each);
    }


    preferences = getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);

    for ( String each : topics)
    {
        mastered.add((Integer)preferences.getInt(each,0));
    }

    adapter.notifyDataSetChanged();
    // attention! The arraylist used for recyclerView shoudnt be changed, other wise the notifyDataSet will not take effect
    //

    }

    @Override
    protected void onResume() {
        super.onResume();
        mastered.clear();
        SharedPreferences preferences = getSharedPreferences("com.example.englishmore.userProgress", MODE_PRIVATE);

        for ( String each : topics)
        {
            mastered.add((Integer)preferences.getInt(each,0));
        }

        adapter.notifyDataSetChanged();

    }
}
