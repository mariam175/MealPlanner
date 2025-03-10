package com.example.dailymenu.UI.Search.Meals.View;

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
import com.example.dailymenu.Model.DTO.MealsFilter;
import com.example.dailymenu.R;

import java.util.ArrayList;
import java.util.List;


public class MealsRecycleView extends RecyclerView.Adapter<MealsViewHolder> {
    Context context;
    List<MealsFilter> mealsFilters;
    List<MealsFilter> filters;
    OnMealClick onMealClick;
    public MealsRecycleView(Context context,  List<MealsFilter> mealsFilters , OnMealClick onMealClick) {
        this.context = context;
        this.mealsFilters = mealsFilters;
        this.onMealClick = onMealClick;
        this.filters = new ArrayList<>(mealsFilters);
    }

    public void setFilters(List<MealsFilter> filters) {
        this.filters = filters;
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
        MealsFilter current = filters.get(position);
        holder.name.setText(current.getStrMeal());
        Glide.with(context).load(current.getStrMealThumb())
                .apply(new RequestOptions().override(200 , 200))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.img);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context , MealDetails.class);
//                intent.putExtra("meal" ,current.getIdMeal());
//                context.startActivity(intent);

                onMealClick.onMealClick(current.getIdMeal(), view);
               // Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }
}
class MealsViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView img;
    TextView name;
    public MealsViewHolder(@NonNull View itemView) {
        super(itemView);
       view = itemView;
        img = itemView.findViewById(R.id.iv_meal_item);
        name = itemView.findViewById(R.id.tv_card_meal_name);
    }
}
