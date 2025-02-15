package com.example.dailymenu.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;

import java.util.List;

@Dao
public interface DAO {
    @Query("select * from favMeals")
    LiveData<List<Meal>> favMeals();
    @Insert
    void addMovie(Meal meal);
    @Delete
    void deleteMovie(Meal meal);
}
