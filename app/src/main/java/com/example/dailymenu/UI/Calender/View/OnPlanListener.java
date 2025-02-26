package com.example.dailymenu.UI.Calender.View;

import com.example.dailymenu.Model.DTO.MealsPlan;

public interface OnPlanListener {
    public void onDeleteClick(MealsPlan meal);
    public void onClick(String id);
}
