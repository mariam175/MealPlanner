package com.example.dailymenu.Favourit.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailymenu.Favourit.Presenter.FavouritePresenter;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.ArrayList;
import java.util.List;


public class FavouritesFragment extends Fragment {
    RecyclerView recyclerView;
    FavouritePresenter presenter;
    FavMealsRecycleView favMealsRecycleView;

    public FavouritesFragment() {
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
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_favs);
        presenter = new FavouritePresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        favMealsRecycleView = new FavMealsRecycleView(getContext() , new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favMealsRecycleView);
        presenter.getAllFavs();

    }
    public void setFavmeals(List<Meal>meals)
    {
        favMealsRecycleView.setFavMeals(meals);
        recyclerView.setAdapter(favMealsRecycleView);
        favMealsRecycleView.notifyDataSetChanged();
    }
}