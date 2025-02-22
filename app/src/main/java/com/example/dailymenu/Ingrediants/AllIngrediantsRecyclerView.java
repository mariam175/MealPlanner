package com.example.dailymenu.Ingrediants;

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
import com.example.dailymenu.Home.View.HomeFragmentDirections;
import com.example.dailymenu.Model.Ingredient;
import com.example.dailymenu.R;

import com.example.dailymenu.Search.Features.View.SearchFragmentDirections;
import com.example.dailymenu.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AllIngrediantsRecyclerView extends RecyclerView.Adapter<allIngrediantsViewHolder> {
    Context context;
    List<Ingredient> ingredients , filteredList;
    String fragment;

    public AllIngrediantsRecyclerView(Context context, List<Ingredient> ingredients , String fragment) {
        this.context = context;
        this.ingredients = ingredients;
        this.fragment = fragment;
        this.filteredList = new ArrayList<>(ingredients);
    }

    public void setFilteredList(List<Ingredient> filteredList) {
        this.filteredList = filteredList;
    }

    @NonNull
    @Override
    public allIngrediantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.catigory_item , parent , false);
        allIngrediantsViewHolder holder = new allIngrediantsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull allIngrediantsViewHolder holder, int position) {
        Ingredient current = filteredList.get(position);
        holder.catigoryName.setText(current.getStrIngredient());
        Glide.with(context).load(Utils.INGREDIANT_IMAGE + current.getStrIngredient() + ".png")
                .apply(new RequestOptions().override(300 , 300))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.catigoryImage);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context , Meals.class);
//                intent.putExtra("Type" , "ingrediant");
//                intent.putExtra("Ingrediant" , current.getStrIngredient());
//                context.startActivity(intent);
               if (fragment.equals("homeFragment"))
               {
                   HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                           HomeFragmentDirections.actionHomeFragmentToMealsFragment("ingrediant" , current.getStrIngredient());
                   Navigation.findNavController(view).navigate(action);
               }
               else {
                   SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                           SearchFragmentDirections.actionSearchFragmentToMealsFragment("ingrediant" , current.getStrIngredient());
                   Navigation.findNavController(view).navigate(action);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

}
class allIngrediantsViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView catigoryImage;
    TextView catigoryName;
    public allIngrediantsViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        catigoryImage = itemView.findViewById(R.id.iv_catigory);
        catigoryName = itemView.findViewById(R.id.tv_cat_name);
    }
}

