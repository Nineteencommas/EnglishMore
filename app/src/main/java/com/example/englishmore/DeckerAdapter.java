package com.example.englishmore;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DeckerAdapter extends RecyclerView.Adapter<DeckerAdapter.ViewHolder> {

    Integer[] mastered;
    Integer[] total;

    public DeckerAdapter(Integer[] mastered, Integer[] total)
    {
        this.mastered = mastered;
        this.total = total;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.decker_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.decker_num_txt.setText("Decker"+mastered[position]);
        viewHolder.mastered_num_txt.setText(mastered[position]+"of"+total[position]+"is mastered.");
        viewHolder.progressBar.setMax(total[position]);
        viewHolder.progressBar.setProgress(mastered[position]);
    }

    @Override
    public int getItemCount() {
        return mastered.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView decker_num_txt ;
        TextView mastered_num_txt;
        ProgressBar progressBar;

        public ViewHolder(View view){
            super(view);

            decker_num_txt  = (TextView) view.findViewById(R.id.decker_num_txt);
            mastered_num_txt = (TextView) view.findViewById(R.id.mastered_num_txt);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_per_decker);


        }
    }
}

