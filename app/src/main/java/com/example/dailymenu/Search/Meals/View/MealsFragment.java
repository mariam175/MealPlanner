package com.example.dailymenu.Search.Meals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.Search.Meals.Presenter.MealsPresenter;
import com.example.dailymenu.db.MealLocalDataSource;


import java.util.List;


public class MealsFragment extends Fragment {
    RecyclerView recyclerView;
    List<MealsFilter> meals;
    MealsPresenter presenter;
    String rel;
    String type;
    public MealsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_meals);
        type = MealsFragmentArgs.fromBundle(getArguments()).getType();
        presenter = new MealsPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        rel = MealsFragmentArgs.fromBundle(getArguments()).getRel();
        if (type.equals("area"))
        {
            presenter.getMealsByArea(rel);
        }
        else  if (type.equals("catigory"))
        {
            presenter.getMealsByCategory(rel);
        }
        else {
           presenter.getMealsByIngrediant(rel);
        }

    }
    public void setMeal(List<MealsFilter>meals)
    {
        this.meals = meals;
        MealsRecycleView mealsRecycleView = new MealsRecycleView(requireContext(), meals.toArray(new MealsFilter[0]));
        recyclerView.setAdapter(mealsRecycleView);
    }
}