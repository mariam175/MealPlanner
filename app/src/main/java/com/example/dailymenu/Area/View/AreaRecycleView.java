package com.example.dailymenu.Area.View;

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
import com.example.dailymenu.Model.Area;
import com.example.dailymenu.R;
import com.example.dailymenu.Utils.Flags;

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
        View view = inflater.inflate(R.layout.catigory_item , parent , false);
        areaViewHolder holder = new areaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull areaViewHolder holder, int position) {
        Area current = filteredList.get(position);
       holder.areaText.setText(current.getStrArea());
        Glide.with(context).load(Flags.flagsImg().getOrDefault(current.getStrArea() , R.drawable.unknown))
                .apply(new RequestOptions().override(300 , 300))
                .apply(RequestOptions.circleCropTransform())
                                .into(holder.area);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
    ImageView area;
    TextView areaText;
    public areaViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        area = itemView.findViewById(R.id.iv_catigory);
        areaText = itemView.findViewById(R.id.tv_cat_name);
    }
}
