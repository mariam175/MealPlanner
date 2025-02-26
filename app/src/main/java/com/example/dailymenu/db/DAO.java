package com.example.dailymenu.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.dailymenu.Model.DTO.MealsFav;
import com.example.dailymenu.Model.DTO.MealsPlan;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface DAO {
    @Query("select * from favMeals where userId = :userId")
    Observable<List<MealsFav>> favMeals(String userId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addMeal(MealsFav meal);
    @Delete
    Completable deleteMeal(MealsFav meal);
    @Query("SELECT COUNT(*) FROM favMeals WHERE mealId = :mealId and userId = :userId")
    Observable<Integer> isMealIsFav(String mealId , String userId);
    @Query("SELECT * FROM favMeals WHERE mealId = :mealId and userId = :userId")
    Observable<MealsFav> getMealById(String mealId , String userId);
    @Query("select * from plans where date = :date and userId = :userId")
    Observable<List<MealsPlan>> PlanedMeals(String date , String userId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable addPlanedMeal(MealsPlan mealsPlan);
    @Delete
    Completable deletePlanedMeal(MealsPlan mealsPlan);
    @Query("SELECT * FROM plans WHERE mealId = :mealId and userId = :userId")
    Observable<MealsPlan> getPlanMealById(String mealId , String userId);
}
