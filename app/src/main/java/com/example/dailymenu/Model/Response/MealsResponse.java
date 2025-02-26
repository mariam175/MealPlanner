package com.example.dailymenu.Model.Response;

import com.example.dailymenu.Model.DTO.Meal;

import java.util.List;

public class MealsResponse {
    private List<Meal> meals;

    public MealsResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
