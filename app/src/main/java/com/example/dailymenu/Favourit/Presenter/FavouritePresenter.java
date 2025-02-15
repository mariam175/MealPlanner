package com.example.dailymenu.Favourit.Presenter;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Favourit.View.FavouritesFragment;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.List;

public class FavouritePresenter {
    FavouritesFragment favouritesFragment;
    Repositry repo;
    static FavouritePresenter favouritePresenter = null;
    public FavouritePresenter(FavouritesFragment favouritesFragment , Repositry repo)
    {
        this.favouritesFragment = favouritesFragment;
        this.repo = repo;
    }

    public LiveData<List<Meal>> getAllFavs()
    {
       return repo.getFavMeals();
    }

}
