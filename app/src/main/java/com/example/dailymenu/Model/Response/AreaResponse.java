package com.example.dailymenu.Model.Response;

import com.example.dailymenu.Model.DTO.Area;

import java.util.List;

public class AreaResponse {
    private List<Area> meals;

    public AreaResponse(List<Area> meals) {
        this.meals = meals;
    }

    public List<Area> getMeals() {
        return meals;
    }

    public void setMeals(List<Area> meals) {
        this.meals = meals;
    }
}
