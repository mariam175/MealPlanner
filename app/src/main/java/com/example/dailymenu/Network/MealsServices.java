package com.example.dailymenu.Network;

import com.example.dailymenu.Model.AreaResponse;
import com.example.dailymenu.Model.CatigoryResponse;
import com.example.dailymenu.Model.IngrediantResponse;
import com.example.dailymenu.Model.MealsFilterResponse;
import com.example.dailymenu.Model.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsServices {
   @GET("random.php")
    Call<MealsResponse>getRandomMeals();
   @GET("lookup.php")
    Call<MealsResponse>getMealById(@Query("i")String i);
   @GET("list.php?a=list")
    Call<AreaResponse>getAreas();
    @GET("categories.php")
    Call<CatigoryResponse> getAllCatigories();
    @GET("list.php?i=list")
    Call<IngrediantResponse>getAllIngrediants();
    @GET("filter.php")
    Call<MealsFilterResponse> getMealsByCatigory(@Query("c")String c);
    @GET("filter.php")
    Call<MealsFilterResponse> getMealsByArea(@Query("a")String a);
    @GET("filter.php")
    Call<MealsFilterResponse> getMealsByIngrediants(@Query("i")String i);

}
