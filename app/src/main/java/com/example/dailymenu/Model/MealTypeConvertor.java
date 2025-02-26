package com.example.dailymenu.Model;

import androidx.room.TypeConverter;

import com.example.dailymenu.Model.DTO.Meal;
import com.google.gson.Gson;

public class MealTypeConvertor {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMeal(Meal meal) {
        return gson.toJson(meal);
    }

    @TypeConverter
    public static Meal toMeal(String json) {
        return gson.fromJson(json, Meal.class);
    }
}
