package com.example.dailymenu.MealDetails.Presenter;

import android.widget.Toast;

import com.example.dailymenu.Favourit.Presenter.FavouritePresenter;
import com.example.dailymenu.Favourit.View.FavouritesFragment;
import com.example.dailymenu.MealDetails.View.MealDetailsFragment;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.NetworkCallBack;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.List;

public class MealDetailsPresenter implements NetworkCallBack<Meal> {
    MealDetailsFragment mealDetailsFragment;
    Repositry repo;
    static MealDetailsPresenter mealDetailsPresenter = null;
    public MealDetailsPresenter( MealDetailsFragment mealDetailsFragment , Repositry repo)
    {
        this.mealDetailsFragment = mealDetailsFragment;
        this.repo = repo;
    }

    public void getMealById(String id)
    {
         repo.getMealById(this , id);
    }

    @Override
    public void OnSuccess(List<Meal> meals) {
        mealDetailsFragment.setMeal(meals.get(0));
    }

    @Override
    public void OnFailure(String err) {
        Toast.makeText(mealDetailsFragment.requireContext(), err, Toast.LENGTH_SHORT).show();
    }
    public void addToFav(Meal meal)
    {
        repo.addToFav(meal);
    }
    public void removeFromFav(Meal meal)
    {
        repo.removeFromFav(meal);
    }
    public void addMealToPlan(MealsPlan mealsPlan)
    {
        repo.addMealToPlans(mealsPlan);
    }
}
