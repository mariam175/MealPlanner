package com.example.dailymenu.UI.Search.Meals.Presenter;

import com.example.dailymenu.Model.DTO.MealsFilter;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.UI.Search.Meals.View.MealsFragment;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsPresenter {
    MealsFragment mealsFragment;
    Repositry repo;

    public MealsPresenter(MealsFragment mealsFragment , Repositry repo) {
        this.mealsFragment = mealsFragment;
        this.repo = repo;
    }
    public void getMealsByCategory(String c)
    {
        repo.getMealsByCategory(c)
                .subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> mealsFragment.setMeal(item)
                );
    }
    public void getMealsByArea(String a)
    {
        repo.getMealsByArea(a)
                .subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> mealsFragment.setMeal(item)
                );
    }
    public void getMealsByIngrediant(String i)
    {
        repo.getMealsByIngrediants(i)
                .subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> mealsFragment.setMeal(item)
                );
    }
    public List<MealsFilter> seachMeals(List<MealsFilter> meals , String str)
    {
        List<MealsFilter> filter = meals.stream()
                .filter(meal -> meal.getStrMeal().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toList());
        return filter;
    }
}
