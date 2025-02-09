package com.example.dailymenu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MealsRecycleView extends RecyclerView.Adapter<MealsViewHolder> {
    Context context;
    MealsFilter[] mealsFilters;

    public MealsRecycleView(Context context, MealsFilter[] mealsFilters) {
        this.context = context;
        this.mealsFilters = mealsFilters;
    }

    @NonNull
    @Override
    public MealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_item , parent , false);
        MealsViewHolder holder = new MealsViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MealsViewHolder holder, int position) {
        MealsFilter current = mealsFilters[position];
        Glide.with(context).load(current.getStrMealThumb())
                .apply(new RequestOptions().override(600 , 300))
                .into(holder.img);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , MealDetails.class);
                intent.putExtra("meal" ,current.getIdMeal());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealsFilters.length;
    }
}
class MealsViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView img;
    public MealsViewHolder(@NonNull View itemView) {
        super(itemView);
       view = itemView;
        img = itemView.findViewById(R.id.iv_meal_item);
    }
}
