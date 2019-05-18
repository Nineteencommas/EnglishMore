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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeckerAdapter extends RecyclerView.Adapter<DeckerAdapter.ViewHolder> {

    ArrayList<Integer> mastered;
    ArrayList<Integer> total;
    ArrayList<String> url;
    Context context;

    public DeckerAdapter(ArrayList<Integer> mastered, ArrayList<Integer> total, Context context)
    {
        this.mastered = mastered;
        this.total = total;
        this.context = context;
    }

    @NonNull
    @Override
    public DeckerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.decker_row, viewGroup, false);
        return new DeckerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.decker_num_txt.setText("Decker"+mastered.get(position));
        viewHolder.mastered_num_txt.setText(mastered.get(position)+"of"+total.get(position)+"is mastered.");
        viewHolder.progressBar.setMax(total.get(position));
        viewHolder.progressBar.setProgress(mastered.get(position));

    }

    @Override
    public int getItemCount() {
        return mastered.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView decker_num_txt ;
        TextView mastered_num_txt;
        ProgressBar progressBar;
        Button startDeckerBtn;
        public ViewHolder(View view){
            super(view);

            decker_num_txt  = (TextView) view.findViewById(R.id.decker_num_txt);
            mastered_num_txt = (TextView) view.findViewById(R.id.mastered_num_txt);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_per_decker);
            startDeckerBtn = (Button) view.findViewById(R.id.practice_decker_btn);

            startDeckerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,CardActivity.class);
                    context.startActivity(intent);
                }
            });
        }

    }

}

