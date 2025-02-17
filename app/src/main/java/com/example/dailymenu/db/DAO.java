package com.example.dailymenu.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dailymenu.Model.Meal;

import java.util.List;

@Dao
public interface DAO {
    @Query("select * from favMeals")
    LiveData<List<Meal>> favMeals();
    @Insert
    void addMeal(Meal meal);
    @Delete
    void deleteMeal(Meal meal);
}
