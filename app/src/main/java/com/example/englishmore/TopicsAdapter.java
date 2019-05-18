package com.example.englishmore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

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


        return mastered.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView topicText;
        TextView topicProgressText;
        ProgressBar progressBar;
        Button startTopicBtn;

        public ViewHolder(View view){
            super(view);

            topicText  = (TextView) view.findViewById(R.id.topicText);
            progressBar = (ProgressBar) view.findViewById(R.id.topicProgress);
            startTopicBtn = (Button) view.findViewById(R.id.topicBtn);
            topicProgressText = (TextView) view.findViewById(R.id.topicProgressText);

            startTopicBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context,DeckersListActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

}
