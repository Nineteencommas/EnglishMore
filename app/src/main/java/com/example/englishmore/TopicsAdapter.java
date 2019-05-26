package com.example.englishmore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;



public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder>{
    private ArrayList<Integer> mastered;
    private ArrayList<String> topics;
    private ArrayList<Integer> total;
    private Context context;
    public TopicsAdapter(ArrayList<Integer> mastered,ArrayList<String> topics,ArrayList<Integer> total,  Context context) {
        this.mastered = mastered;
        this.context = context;
        this.topics = topics;
        this.total = total;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.topic_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicsAdapter.ViewHolder viewHolder, int i){
        viewHolder.topicText.setText(topics.get(i));
        viewHolder.topicProgressText.setText(mastered.get(i)+" of "+total.get(i)+" is mastered.");
        viewHolder.progressBar.setMax(total.get(i));
        viewHolder.progressBar.setProgress(mastered.get(i));
    }

    @Override
    public int getItemCount() {

        return topics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView topicText;
        TextView topicProgressText;
        ProgressBar progressBar;
        Button startTopicBtn;

        public ViewHolder(View view){
            super(view);

            topicText  = view.findViewById(R.id.topicText);
            progressBar = view.findViewById(R.id.topicProgress);
            startTopicBtn = view.findViewById(R.id.topicBtn);
            topicProgressText = view.findViewById(R.id.topicProgressText);

            startTopicBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences("com.example.englishmore.topicAndDeckerInfo",MODE_PRIVATE);
                    Intent intent = new Intent(context,DeckersListActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("topic", topics.get(getAdapterPosition()));

                    bundle.putInt("topicProgress",mastered.get(getAdapterPosition()));
                    bundle.putInt("deckerNum", getAdapterPosition());


                    Log.d("Topic", "the bundle is"+topics.get(getAdapterPosition()));
                    int temp = preferences.getInt(topics.get(getAdapterPosition())+"Deckernum",1);
                    bundle.putInt("deckerNum",preferences.getInt(topics.get(getAdapterPosition())+"Deckernum",1));
                    Log.d("Topic","the bundle is "+temp);

                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }
            });
        }

    }

}
