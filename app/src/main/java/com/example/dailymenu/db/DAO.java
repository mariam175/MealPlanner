package com.example.dailymenu.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFav;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DAO {
    @Query("select * from favMeals where userId = :userId")
    Observable<List<MealsFav>> favMeals(String userId);
    @Insert
    Completable addMeal(MealsFav meal);
    @Delete
    Completable deleteMeal(MealsFav meal);
    @Query("SELECT COUNT(*) FROM favMeals WHERE mealId = :mealId and userId = :userId")
    Observable<Integer> isMealIsFav(String mealId , String userId);
}
