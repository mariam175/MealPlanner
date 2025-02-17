package com.example.dailymenu.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;


@Database(entities = {Meal.class , MealsPlan.class} , version = 2)
public abstract class FavMealsDataBase extends RoomDatabase {
    private static FavMealsDataBase favMealsDataBase = null;
    public abstract DAO dao();
    public abstract PlansDAO plansDAO();
    public static FavMealsDataBase getInstance(Context context)
    {
       if (favMealsDataBase == null){
           favMealsDataBase = Room.databaseBuilder(context.getApplicationContext() , FavMealsDataBase.class , "meals")
                   .build();
       }
       return favMealsDataBase;
    }
}
