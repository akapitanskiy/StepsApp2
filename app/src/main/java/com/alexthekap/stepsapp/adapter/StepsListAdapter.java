package com.alexthekap.stepsapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alexthekap.stepsapp.R;
import com.alexthekap.stepsapp.model.ListItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {

    Context context;
    List<ListItem> stepsItemsList;

    public StepsListAdapter(Context context, List<ListItem> stepsItemsList) {
        this.context = context;
        this.stepsItemsList = stepsItemsList;
    }

    @NonNull
    @Override
    public StepsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        // false - говорим что не надо помещать эту view в родителя это за нас сделает RecyclerView
        return new StepsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsListViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return stepsItemsList.size();
    }

    class StepsListViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvSteps;
        LinearLayout linePieChart;
        FrameLayout piece1;
        FrameLayout piece2;
        FrameLayout piece3;
        TextView tvWalkSteps;
        TextView tvAerobicSteps;
        TextView tvRunSteps;

        LinearLayout.LayoutParams params1;
        LinearLayout.LayoutParams params2;
        LinearLayout.LayoutParams params3;

        public StepsListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvSteps = itemView.findViewById(R.id.tvSteps);
            linePieChart = itemView.findViewById(R.id.linLayoutAsBar);
            tvWalkSteps = itemView.findViewById(R.id.tvWalkSteps);
            tvAerobicSteps = itemView.findViewById(R.id.tvAerobicSteps);
            tvRunSteps = itemView.findViewById(R.id.tvRunSteps);
            piece1 = itemView.findViewById(R.id.piece1);
            piece2 = itemView.findViewById(R.id.piece2);
            piece3 = itemView.findViewById(R.id.piece3);

            params1 = (LinearLayout.LayoutParams) piece1.getLayoutParams();
            params2 = (LinearLayout.LayoutParams) piece2.getLayoutParams();
            params3 = (LinearLayout.LayoutParams) piece3.getLayoutParams();
        }

        void bind(int position) {
            ListItem item = stepsItemsList.get(position);
            int stepsTotal = item.getAerobic() + item.getRun() + item.getWalk();
            tvSteps.setText(String.format(
                    context.getResources().getString(R.string.tvSteps_pattern),
                    String.valueOf(stepsTotal)
            ));

            tvWalkSteps.setText(String.valueOf(item.getWalk()));
            tvAerobicSteps.setText(String.valueOf(item.getAerobic()));
            tvRunSteps.setText(String.valueOf(item.getRun()));

            Date date = new Date(item.getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String strDate = sdf.format(date);
            tvDate.setText(strDate);

            params1.weight = item.getWalk();
            params2.weight = item.getAerobic();
            params3.weight = item.getRun();

            piece1.setLayoutParams(params1);
            piece2.setLayoutParams(params2);
            piece3.setLayoutParams(params3);
        }
    }
}