package com.example.dailymenu.Model.DTO;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.dailymenu.Model.MealTypeConvertor;

@Entity(tableName = "plans")
public class MealsPlan {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String userId;
    @TypeConverters(MealTypeConvertor.class)
    private Meal meal;
    private String date;

    public MealsPlan() {
    }

    public MealsPlan(String mealId, String userId , Meal meal, String date) {
        this.mealId = mealId;
        this.userId = userId;
        this.meal = meal;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
