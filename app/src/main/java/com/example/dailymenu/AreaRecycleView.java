package com.example.dailymenu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AreaRecycleView extends RecyclerView.Adapter<areaViewHolder> {
    Context context;
    List<Area> areas , filteredList;
    String fragment;

    public AreaRecycleView(Context context, List<Area> areas , String fragment) {
        this.context = context;
        this.areas = areas;
        this.fragment = fragment;
        this.filteredList = new ArrayList<>(areas);
    }

    @NonNull
    @Override
    public areaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.area_item , parent , false);
        areaViewHolder holder = new areaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull areaViewHolder holder, int position) {
        Area current = filteredList.get(position);
        holder.area.setText(current.getStrArea());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context , Meals.class);
//                intent.putExtra("Type" , "area");
//                intent.putExtra("Area" , current.getStrArea());
//                context.startActivity(intent);
                if (fragment.equals("homeFragment"))
                {
                    HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                            HomeFragmentDirections.actionHomeFragmentToMealsFragment("area" , current.getStrArea());
                    Navigation.findNavController(view).navigate(action);
                }
                else {
                    SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                            SearchFragmentDirections.actionSearchFragmentToMealsFragment("area" , current.getStrArea());
                    Navigation.findNavController(view).navigate(action);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(areas);
        } else {
            text = text.toLowerCase();
            for (Area area : areas) {
                if (area.getStrArea().toLowerCase().contains(text)) {
                    filteredList.add(area);
                }
            }
        }
        notifyDataSetChanged();
    }
}
class areaViewHolder extends RecyclerView.ViewHolder
{
    View view;
    TextView area;
    public areaViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        area = view.findViewById(R.id.iv_area);
    }
}
