package com.example.dailymenu;

import java.util.List;

public class MealsFilterResponse {
    List<MealsFilter> meals;

    public MealsFilterResponse(List<MealsFilter> meals) {
        this.meals = meals;
    }

    public List<MealsFilter> getMeals() {
        return meals;
    }

    public void setMeals(List<MealsFilter> meals) {
        this.meals = meals;
    }
}
