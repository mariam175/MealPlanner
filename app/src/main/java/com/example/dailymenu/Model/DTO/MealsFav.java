package com.example.dailymenu.Model.DTO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dailymenu.Model.MealTypeConvertor;

@Entity(tableName = "favMeals")
public class MealsFav {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String userId;
    @TypeConverters(MealTypeConvertor.class)
    Meal meal;

    public MealsFav() {
    }

    public MealsFav(String mealId, String userId ,Meal meal) {
        this.mealId = mealId;
        this.userId = userId;
      this.meal = meal;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}

