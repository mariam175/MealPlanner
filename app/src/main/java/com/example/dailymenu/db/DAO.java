package com.example.dailymenu.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dailymenu.Model.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface DAO {
    @Query("select * from favMeals")
    Observable<List<Meal>> favMeals();
    @Insert
    Completable addMeal(Meal meal);
    @Delete
    Completable deleteMeal(Meal meal);
    @Query("SELECT COUNT(*) FROM favMeals WHERE idMeal = :mealId")
    Observable<Integer> isMealIsFav(String mealId);
}
