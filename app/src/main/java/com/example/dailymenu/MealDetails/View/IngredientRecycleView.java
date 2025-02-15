package com.example.dailymenu.MealDetails.View;

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
import com.example.dailymenu.Model.IngridentItem;
import com.example.dailymenu.R;

public class IngredientRecycleView extends RecyclerView.Adapter<IngrediantViewHolder> {
private Context context;
private IngridentItem[] ingridentItems;

    public IngredientRecycleView(Context context, IngridentItem[] ingridentItems) {
        this.context = context;
        this.ingridentItems = ingridentItems;
    }

    @NonNull
    @Override
    public IngrediantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingrediant_item , parent , false);
        IngrediantViewHolder ingrediantViewHolder = new IngrediantViewHolder(view);
        return ingrediantViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngrediantViewHolder holder, int position) {
        IngridentItem ingridentItem = ingridentItems[position];
        holder.ing.setText(ingridentItem.getIngrediant());
        holder.measure.setText(ingridentItem.getMeasure());
        Glide.with(context).load(ingridentItem.getIngrediantImage())
                .apply(new RequestOptions().override(200 , 200))
                .into(holder.ingImage);

    }

    @Override
    public int getItemCount() {
        return ingridentItems.length;
    }
}
class IngrediantViewHolder extends RecyclerView.ViewHolder
{
    TextView ing;
    TextView measure;
    ImageView ingImage;
    View view;
    public IngrediantViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        ing = itemView.findViewById(R.id.tv_ingrediant);
        measure = itemView.findViewById(R.id.tv_measurment);
        ingImage = itemView.findViewById(R.id.iv_ingre_img);
    }
}
