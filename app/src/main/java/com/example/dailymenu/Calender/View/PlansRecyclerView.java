package com.example.dailymenu.Calender.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.R;

import java.util.List;

public class PlansRecyclerView extends RecyclerView.Adapter<PlansViewHolder> {
    List<MealsPlan> plans;
    Context context;
    OnPlanListener listener;

    public PlansRecyclerView(List<MealsPlan> plans, Context context, OnPlanListener listener) {
        this.plans = plans;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.plan_item , parent , false);
        PlansViewHolder holder = new PlansViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlansViewHolder holder, int position) {
        MealsPlan plan = plans.get(position);
        holder.mealName.setText(plan.getMealName());
        Glide.with(context).load(plan.getMealImage())
                .apply(new RequestOptions().override(300 , 300))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mealImage);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteClick(plan);
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(plan.getMealId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public void setPlans(List<MealsPlan> plans) {
        this.plans = plans;
    }
}
class PlansViewHolder extends RecyclerView.ViewHolder
{
    View view;
    TextView mealName;
    ImageView mealImage;
    ImageView delete;
    public PlansViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        mealImage = itemView.findViewById(R.id.iv_plan_image);
        mealName = itemView.findViewById(R.id.tv_plan_name);
        delete = itemView.findViewById(R.id.iv_delete);
    }
}
