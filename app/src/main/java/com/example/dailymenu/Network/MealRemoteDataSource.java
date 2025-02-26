package com.example.dailymenu.Network;

import com.example.dailymenu.Model.Response.AreaResponse;
import com.example.dailymenu.Model.Response.CatigoryResponse;
import com.example.dailymenu.Model.Response.IngrediantResponse;
import com.example.dailymenu.Model.Response.MealsFilterResponse;
import com.example.dailymenu.Model.Response.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    static MealRemoteDataSource mealRemoteDataSource = null;
    private static final String TAG = "ClientApi";
    private  MealsServices mealsServices;
    private MealRemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealsServices = retrofit.create(MealsServices.class);
    }
    public static MealRemoteDataSource getInstance()
    {
        if (mealRemoteDataSource == null)
        {
            return mealRemoteDataSource = new MealRemoteDataSource();
        }
        return mealRemoteDataSource;
    }
    public  Single<MealsResponse> getMealById(String id)
    {
        return mealsServices.getMealById(id);
    }
    public Single<MealsResponse> getRandomMeal()
    {
        return mealsServices.getRandomMeals();
    }
    public Single<CatigoryResponse> getCategories()
    {
       return mealsServices.getAllCatigories();
    }
    public Single<AreaResponse> getArea()
    {
        return mealsServices.getAreas();
    }
    public Single<IngrediantResponse> getIngrediants()
    {
        return mealsServices.getAllIngrediants();
    }
    public Single<MealsFilterResponse> getMealsByCategory (String c){
        return mealsServices.getMealsByCatigory(c);
    }
    public Single<MealsFilterResponse> getMealsByArea (String a){
        return mealsServices.getMealsByArea(a);
    }
    public Single<MealsFilterResponse> getMealsByIngrediants (String i){
        return mealsServices.getMealsByIngrediants(i);
    }
}
