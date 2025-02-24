package com.example.dailymenu.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favMeals")
public class MealsFav {
    @PrimaryKey
    @NonNull
    private String mealId;
    private String userId;
    private String mealName;
    private String mealImage;

    public MealsFav() {
    }

    public MealsFav(String mealId, String userId , String mealName, String mealImage) {
        this.mealId = mealId;
        this.userId = userId;
        this.mealName = mealName;
        this.mealImage = mealImage;

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

}

