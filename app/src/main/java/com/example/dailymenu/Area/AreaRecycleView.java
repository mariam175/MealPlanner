package com.example.dailymenu.Area;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailymenu.Model.Area;
import com.example.dailymenu.R;

import java.util.ArrayList;
import java.util.List;

public class AreaRecycleView extends RecyclerView.Adapter<areaViewHolder> {
    Context context;
    List<Area> areas , filteredList;
    String fragment;
    OnAreaClick listener;
    public AreaRecycleView(Context context, List<Area> areas ,  OnAreaClick listener) {
        this.context = context;
        this.areas = areas;
       this.listener = listener;
        this.filteredList = new ArrayList<>(areas);
    }

    public void setFilteredList(List<Area> filteredList) {
        this.filteredList = filteredList;
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

                listener.onclick(view , current.getStrArea());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
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
