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
    SharedPreferences preferences = getSharedPreferences("com.example.englishmore.topicInfo", MODE_PRIVATE);
    ArrayList<ArrayList> afterParsed = JsonHelper.getTopicFromTopicInfo(preferences.getString("topicInfo", "defaultValueforTopicInfo"));
    ArrayList<Integer> totalTemp = afterParsed.get(1);
    ArrayList<String> topicsTemp = afterParsed.get(0);
    for (Integer each : totalTemp) {
        total.add(each);
    }
    for (String each : topicsTemp)
    {
        topics.add(each);
    }

    mastered.add(0);
    adapter.notifyDataSetChanged();
}
public void getTopicsProgress()
{

}

}
