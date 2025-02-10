package com.example.dailymenu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AreaRecycleView extends RecyclerView.Adapter<areaViewHolder> {
    Context context;
    Area[]areas;

    public AreaRecycleView(Context context, Area[] areas) {
        this.context = context;
        this.areas = areas;
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
        Area current = areas[position];
        holder.area.setText(current.getStrArea());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , Meals.class);
                intent.putExtra("Area" , current.getStrArea());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return areas.length;
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
