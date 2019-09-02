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
    int steps;

    public StepsListAdapter(Context context, List<ListItem> stepsItemsList, int steps) {
        this.context = context;
        this.stepsItemsList = stepsItemsList;
        this.steps = steps;
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
        LinearLayout goal_reached;
        FrameLayout piece1;
        FrameLayout piece2;
        FrameLayout piece3;
        TextView tvWalkSteps;
        TextView tvAerobicSteps;
        TextView tvRunSteps;

        LinearLayout.LayoutParams params1;
        LinearLayout.LayoutParams params2;
        LinearLayout.LayoutParams params3;
        LinearLayout.LayoutParams goal_reachedParams;

        public StepsListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvSteps = itemView.findViewById(R.id.tvSteps);
            linePieChart = itemView.findViewById(R.id.linLayoutAsBar);
            goal_reached = itemView.findViewById(R.id.goal_reached);
            tvWalkSteps = itemView.findViewById(R.id.tvWalkSteps);
            tvAerobicSteps = itemView.findViewById(R.id.tvAerobicSteps);
            tvRunSteps = itemView.findViewById(R.id.tvRunSteps);
            piece1 = itemView.findViewById(R.id.piece1);
            piece2 = itemView.findViewById(R.id.piece2);
            piece3 = itemView.findViewById(R.id.piece3);

            params1 = (LinearLayout.LayoutParams) piece1.getLayoutParams();
            params2 = (LinearLayout.LayoutParams) piece2.getLayoutParams();
            params3 = (LinearLayout.LayoutParams) piece3.getLayoutParams();
            goal_reachedParams = (LinearLayout.LayoutParams) goal_reached.getLayoutParams();
        }

        void bind(int position) {
            ListItem item = stepsItemsList.get(position);
            int walk = item.getWalk();
            int aerobic = item.getAerobic();
            int run = item.getRun();
            int stepsTotal = walk + aerobic + run;
            if(stepsTotal < steps) {
                goal_reached.setVisibility(View.INVISIBLE);
                goal_reachedParams.height = 0;
                goal_reached.setLayoutParams(goal_reachedParams);
            }
            tvSteps.setText(String.format(
                    context.getResources().getString(R.string.tvSteps_pattern),
                    String.valueOf(stepsTotal),
                    String.valueOf(steps)
            ));

            tvWalkSteps.setText(String.valueOf(walk));
            tvAerobicSteps.setText(String.valueOf(aerobic));
            tvRunSteps.setText(String.valueOf(run));

            Date date = new Date(item.getDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String strDate = sdf.format(date);
            tvDate.setText(strDate);

            params1.weight = walk*1.0 / (aerobic + run) < 0.005 ? ((aerobic + run) / 240f) + 1 : walk;
            params2.weight = aerobic*1.0 / (walk + run) < 0.005 ? ((walk + run) / 240f) + 1 : aerobic;
            params3.weight = run*1.0 / (walk + aerobic) < 0.005 ? ((walk + aerobic) / 240f) + 1 : run;

            piece1.setLayoutParams(params1);
            piece2.setLayoutParams(params2);
            piece3.setLayoutParams(params3);
        }
    }
}