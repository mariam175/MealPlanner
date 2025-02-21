package com.example.dailymenu.Favourit.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.R;

import java.util.List;

public class FavMealsRecycleView extends RecyclerView.Adapter<FavMealsViewHolder> {
    Context context;
    List<Meal> favMeals;

    public FavMealsRecycleView(Context context, List<Meal> favMeals) {
        this.context = context;
        this.favMeals = favMeals;
    }

    public void setFavMeals(List<Meal> favMeals) {
        this.favMeals = favMeals;
    }

    @NonNull
    @Override
    public FavMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.meal_item , parent , false);
        FavMealsViewHolder holder = new FavMealsViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull FavMealsViewHolder holder, int position) {
        Meal current = favMeals.get(position);
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
                Navigation.findNavController(view).popBackStack(R.id.mealDetailsFragment, true);
                FavouritesFragmentDirections.ActionFavouritesFragmentToMealDetailsFragment action =
                        FavouritesFragmentDirections.actionFavouritesFragmentToMealDetailsFragment(current.getIdMeal());

                Navigation.findNavController(view).navigate(action);
               // Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favMeals.size();
    }
}
class FavMealsViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView img;
    TextView name;
    public FavMealsViewHolder(@NonNull View itemView) {
        super(itemView);
       view = itemView;
        img = itemView.findViewById(R.id.iv_meal_item);
        name = itemView.findViewById(R.id.tv_card_meal_name);
    }
}
