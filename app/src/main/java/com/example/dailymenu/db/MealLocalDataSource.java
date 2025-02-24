package com.example.dailymenu.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFav;
import com.example.dailymenu.Model.MealsPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class MealLocalDataSource {
    private DAO dao;
    private PlansDAO plansDAO;
    private LiveData<List<Meal>> meals;
    Context context;
    private static MealLocalDataSource mealLocalDataSource = null;
    private MealLocalDataSource(Context context)
    {
       FavMealsDataBase db = FavMealsDataBase.getInstance(context.getApplicationContext());
       dao = db.dao();
       plansDAO = db.plansDAO();

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
       return plansDAO.addPlanedMeal(mealsPlan);
    }
    public Completable removeFromPlans(MealsPlan meal)
    {
        return plansDAO.deletePlanedMeal(meal);
    }
    public Observable<List<MealsPlan>> getAllPlanByDate(String date , String userId)
    {
        return plansDAO.PlanedMeals(date , userId);
    }
    public Observable<Integer> isFavMeal(String id , String userId)
    {
        return dao.isMealIsFav(id , userId);
    }
}
