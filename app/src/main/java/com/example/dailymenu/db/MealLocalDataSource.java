package com.example.dailymenu.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;

import java.util.List;

public class MealLocalDataSource {
    private DAO dao;
    private LiveData<List<Meal>> meals;
    Context context;
    private static MealLocalDataSource mealLocalDataSource = null;
    private MealLocalDataSource(Context context)
    {
       FavMealsDataBase db = FavMealsDataBase.getInstance(context.getApplicationContext());
       dao = db.dao();
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
                dao.deleteMovie(meal);
            }
        }.start();
    }
    public void add(Meal meal)
    {
        new Thread(){
            public void run()
            {
                dao.addMovie(meal);
            }
        }.start();
    }
}
