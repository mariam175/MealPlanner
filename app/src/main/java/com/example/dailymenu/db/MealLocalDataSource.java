package com.example.dailymenu.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;

import java.util.List;

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
       meals = dao.favMeals();
    }
    public static MealLocalDataSource getInstance(Context context)
    {
        if (mealLocalDataSource == null)
        {
            mealLocalDataSource = new MealLocalDataSource(context);
        }
        return mealLocalDataSource;
    }
    public LiveData<List<Meal>> getData()
    {
        return meals;
    }
    public void delete(Meal meal)
    {
        new Thread(){
            public void run()
            {
                dao.deleteMeal(meal);
            }
        }.start();
    }
    public void add(Meal meal)
    {
        new Thread(){
            public void run()
            {
                dao.addMeal(meal);
            }
        }.start();
    }
    public void addMealToPlan(MealsPlan mealsPlan)
    {
        new Thread(){
            public void run()
            {
                plansDAO.addPlanedMeal(mealsPlan);
            }
        }.start();
    }
    public void removeFromPlans(MealsPlan meal)
    {
        new Thread(){
            public void run()
            {
                plansDAO.deletePlanedMeal(meal);
            }
        }.start();
    }
    public LiveData<List<MealsPlan>> getAllPlanByDate(String date)
    {
        return plansDAO.PlanedMeals(date);
    }
}
