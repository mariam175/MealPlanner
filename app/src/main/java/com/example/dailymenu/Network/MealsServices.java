package com.example.dailymenu.Network;

import com.example.dailymenu.Model.Response.AreaResponse;
import com.example.dailymenu.Model.Response.CatigoryResponse;
import com.example.dailymenu.Model.Response.IngrediantResponse;
import com.example.dailymenu.Model.Response.MealsFilterResponse;
import com.example.dailymenu.Model.Response.MealsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsServices {
   @GET("random.php")
   Single<MealsResponse> getRandomMeals();
   @GET("lookup.php")
    Single<MealsResponse>getMealById(@Query("i")String i);
   @GET("list.php?a=list")
    Single<AreaResponse>getAreas();
    @GET("categories.php")
    Single<CatigoryResponse> getAllCatigories();
    @GET("list.php?i=list")
    Single<IngrediantResponse>getAllIngrediants();
    @GET("filter.php")
    Single<MealsFilterResponse> getMealsByCatigory(@Query("c")String c);
    @GET("filter.php")
    Single<MealsFilterResponse> getMealsByArea(@Query("a")String a);
    @GET("filter.php")
    Single<MealsFilterResponse> getMealsByIngrediants(@Query("i")String i);

}
