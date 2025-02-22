package com.example.dailymenu.Network;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Firebase.Firebase;
import com.example.dailymenu.Model.AreaResponse;
import com.example.dailymenu.Model.CatigoryResponse;
import com.example.dailymenu.Model.IngrediantResponse;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.Model.MealsFilterResponse;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.Model.MealsResponse;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repositry {
    MealRemoteDataSource mealRemoteDataSource;
    MealLocalDataSource mealLocalDataSource;
    static Repositry repo = null;
    Firebase firebase;

    private Repositry(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource) {
        this.mealRemoteDataSource = mealRemoteDataSource;
        this.mealLocalDataSource = mealLocalDataSource;
        firebase = new Firebase();
    }
    public static Repositry getInstance(MealRemoteDataSource mealRemoteDataSource, MealLocalDataSource mealLocalDataSource)
    {
        if (repo == null)
        {
            repo = new Repositry(mealRemoteDataSource , mealLocalDataSource);
        }
        return repo;
    }
    public  Single<MealsResponse> getMealById(String id)
    {
         return mealRemoteDataSource.getMealById(id);
    }
    public Completable addToFav(Meal meal)
    {
       return mealLocalDataSource.add(meal);
    }
    public Completable removeFromFav(Meal meal)
    {
        return mealLocalDataSource.delete(meal);
    }
    public Observable<List<Meal>> getFavMeals()
    {
        return mealLocalDataSource.getData();
    }
    public Completable addMealToPlans(MealsPlan mealsPlan)
    {
        return mealLocalDataSource.addMealToPlan(mealsPlan);
    }
    public Completable removeFromPlans(MealsPlan mealsPlan)
    {
        return mealLocalDataSource.removeFromPlans(mealsPlan);
    }
    public Observable<List<MealsPlan>> getPlanMealsByDate(String date)
    {
        return mealLocalDataSource.getAllPlanByDate(date);
    }
    public Single<MealsResponse> getRandomMeal()
    {
        return mealRemoteDataSource.getRandomMeal();
    }
    public Single<CatigoryResponse> getCatigories()
    {
       return mealRemoteDataSource.getCategories();
    }
    public Single<AreaResponse>getArea()
    {
        return mealRemoteDataSource.getArea();
    }
    public Single<IngrediantResponse>getIngrediants()
    {
        return mealRemoteDataSource.getIngrediants();
    }
    public Single<MealsFilterResponse> getMealsByCategory(String c)
    {
        return mealRemoteDataSource.getMealsByCategory(c);
    }
    public Single<MealsFilterResponse> getMealsByArea(String a)
    {
        return mealRemoteDataSource.getMealsByArea(a);
    }
    public Single<MealsFilterResponse> getMealsByIngrediants(String i)
    {
        return mealRemoteDataSource.getMealsByIngrediants(i);
    }
    public void backupFavMeal(Meal meal)
    {
        firebase.backupMealToFirestore(meal);
    }
    public void deleteFavMealFirebase(Meal meal)
    {
        firebase.deleteMealFromFirestore(meal);
    }
    public void restoreData()
    {
        firebase.restoreData(mealLocalDataSource);
    }
    public Observable<Integer> isFavMeal(String id)
    {
        return mealLocalDataSource.isFavMeal(id);
    }
    public void backupPlans(MealsPlan mealsPlan)
    {
         firebase.backupPlanToFirestore(mealsPlan);
    }
    public void removePlanFirebase(MealsPlan mealsPlan)
    {
        firebase.deletePlanFromFirestore(mealsPlan);
    }
}
