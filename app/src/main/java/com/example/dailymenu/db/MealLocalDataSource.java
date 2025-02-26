package com.example.dailymenu.db;

import android.content.Context;

import com.example.dailymenu.Model.DTO.MealsFav;
import com.example.dailymenu.Model.DTO.MealsPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class MealLocalDataSource {
    private DAO dao;
    private static MealLocalDataSource mealLocalDataSource = null;
    private MealLocalDataSource(Context context)
    {
       FavMealsDataBase db = FavMealsDataBase.getInstance(context.getApplicationContext());
       dao = db.dao();

    }
    public static MealLocalDataSource getInstance(Context context)
    {
        if (mealLocalDataSource == null)
        {
            mealLocalDataSource = new MealLocalDataSource(context);
        }
        return mealLocalDataSource;
    }
    public Observable<List<MealsFav>> getData(String userId)
    {
        return dao.favMeals(userId);
    }
    public Completable delete(MealsFav meal)
    {
        return dao.deleteMeal(meal);
    }
    public Completable add(MealsFav meal)
    {
        return dao.addMeal(meal);
    }
    public Completable addMealToPlan(MealsPlan mealsPlan)
    {
       return dao.addPlanedMeal(mealsPlan);
    }
    public Completable removeFromPlans(MealsPlan meal)
    {
        return dao.deletePlanedMeal(meal);
    }
    public Observable<List<MealsPlan>> getAllPlanByDate(String date , String userId)
    {
        return dao.PlanedMeals(date , userId);
    }
    public Observable<Integer> isFavMeal(String id , String userId)
    {
        return dao.isMealIsFav(id , userId);
    }
    public  Observable<MealsFav> getMealById(String mealId , String userId)
    {
        return dao.getMealById(mealId , userId);
    }
    public  Observable<MealsPlan> getPlanMealById(String mealId , String userId)
    {
        return dao.getPlanMealById(mealId, userId);
    }
}
