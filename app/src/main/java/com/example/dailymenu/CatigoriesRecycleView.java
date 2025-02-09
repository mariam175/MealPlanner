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

public class CatigoriesRecycleView extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    Catigory[] catigories;

    public CatigoriesRecycleView(Context context, Catigory[] catigories) {
        this.context = context;
        this.catigories = catigories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.catigory_item , parent , false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Catigory currentCatigory = catigories[position];
        Glide.with(context).load(currentCatigory.getStrCategoryThumb())
                .apply(new RequestOptions().override(300 , 300))
                .into(holder.catigoryImage);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , Meals.class);
                intent.putExtra("Catigory" , currentCatigory.getStrCategory());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return catigories.length;
    }
}
class ViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView catigoryImage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        catigoryImage = itemView.findViewById(R.id.iv_catigory);
    }
}
