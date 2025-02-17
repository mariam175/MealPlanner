package com.example.dailymenu.Network;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.List;

public class Repositry {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    static Repositry repo = null;

    private Repositry(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
    }
    public static Repositry getInstance(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource)
    {
        if (repo == null)
        {
            repo = new Repositry(mealRemoteDataSource , mealLocalDataSource);
        }
        return repo;
    }
    public void getMealById(NetworkCallBack networkCallBack , String id)
    {
         mealRemoteDataSource.getMealById(networkCallBack , id);
    }
    public void addToFav(Meal meal)
    {
        mealLocalDataSource.add(meal);
    }
    public void removeFromFav(Meal meal)
    {
        mealLocalDataSource.delete(meal);
    }
    public LiveData<List<Meal>> getFavMeals()
    {
        return mealLocalDataSource.getData();
    }
    public void addMealToPlans(MealsPlan mealsPlan)
    {
        mealLocalDataSource.addMealToPlan(mealsPlan);
    }
    public void removeFromPlans(MealsPlan mealsPlan)
    {
        mealLocalDataSource.removeFromPlans(mealsPlan);
    }
    public LiveData<List<MealsPlan>> getPlanMealsByDate(String date)
    {
        return mealLocalDataSource.getAllPlanByDate(date);
    }
}
