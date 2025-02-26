package com.example.dailymenu.Model.Response;

import com.example.dailymenu.Model.DTO.Ingredient;

import java.util.List;

public class IngrediantResponse {
    private List<Ingredient> meals;

    public IngrediantResponse(List<Ingredient> meals) {
        this.meals = meals;
    }

    public List<Ingredient> getMeals() {
        return meals;
    }

    public void setMeals(List<Ingredient> meals) {
        this.meals = meals;
    }
}
