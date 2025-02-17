package com.example.dailymenu.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;

import java.util.List;
@Dao
public interface PlansDAO {
    @Query("select * from plans where date = :date")
    LiveData<List<MealsPlan>> PlanedMeals(String date);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPlanedMeal(MealsPlan mealsPlan);
    @Delete
    void deletePlanedMeal(MealsPlan mealsPlan);
}
