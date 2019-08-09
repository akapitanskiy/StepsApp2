package com.alexthekap.stepsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {


    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int list_item_Rid = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(list_item_Rid, parent, false);

        StepsListViewHolder stepsListViewHolder = new StepsListViewHolder(view);
        return stepsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder viewHolder, int position) {

    }

    @Override
    public int getItemCount() {
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvSteps;
        LinearLayout linePieChart;
        TextView stepsWalk;
        TextView stepsAerobic;
        TextView stepsRun;

        public StepsListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvSteps = itemView.findViewById(R.id.tvSteps);
            linePieChart = itemView.findViewById(R.id.linLayoutAsBar);
            stepsWalk = itemView.findViewById(R.id.tvWalkSteps);
            stepsAerobic = itemView.findViewById(R.id.tvAerobicSteps);
            stepsRun = itemView.findViewById(R.id.tvRunSteps);
        }

        void bind(int position) {

        }
    }
}
// false - говорим что не надо помещать эту view в родителя это за нас сделает RecyclerView