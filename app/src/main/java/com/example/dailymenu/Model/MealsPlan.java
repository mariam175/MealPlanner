package com.example.dailymenu.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "plans")
public class MealsPlan {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String mealName;
    private String mealImage;
    private String date;

    public MealsPlan() {
    }

    public MealsPlan(String mealId, String mealName, String mealImage, String date) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
