package com.example.dailymenu.Catigory;

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
import com.example.dailymenu.Model.Catigory;
import com.example.dailymenu.R;
import com.example.dailymenu.Search.Features.View.SearchFragmentDirections;


import java.util.ArrayList;
import java.util.List;

public class CatigoriesRecycleView extends RecyclerView.Adapter<ViewHolder> {
    Context context;
    List<Catigory> catigories , filteredList;
    View fragmentView;
    String fragment;

    public CatigoriesRecycleView(Context context, List<Catigory>  catigories , View fragmentView , String fragment) {
        this.context = context;
        this.catigories = catigories;
        this.fragmentView = fragmentView;
        this.fragment = fragment;
        this.filteredList = new ArrayList<>(catigories);
    }

    public void setFilteredList(List<Catigory> filteredList) {
        this.filteredList = filteredList;
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
        Catigory currentCatigory = filteredList.get(position);
        holder.catigoryName.setText(currentCatigory.getStrCategory());
        Glide.with(context).load(currentCatigory.getStrCategoryThumb())
                .apply(new RequestOptions().override(300 , 300))
                .apply(RequestOptions.circleCropTransform())
                .into(holder.catigoryImage);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context , Meals.class);
//                intent.putExtra("Type" , "catigory");
//                intent.putExtra("Catigory" , currentCatigory.getStrCategory());
//                context.startActivity(intent);
                if (fragment.equals("homeFragment"))
                {
                    HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                            HomeFragmentDirections.actionHomeFragmentToMealsFragment("catigory" , currentCatigory.getStrCategory());
                    Navigation.findNavController(fragmentView).navigate(action);
                }
                else {
                    SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                            SearchFragmentDirections.actionSearchFragmentToMealsFragment("catigory" , currentCatigory.getStrCategory());
                    Navigation.findNavController(fragmentView).navigate(action);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
}
class ViewHolder extends RecyclerView.ViewHolder
{
    View view;
    ImageView catigoryImage;
    TextView catigoryName;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        catigoryImage = itemView.findViewById(R.id.iv_catigory);
        catigoryName = itemView.findViewById(R.id.tv_cat_name);
    }
}
