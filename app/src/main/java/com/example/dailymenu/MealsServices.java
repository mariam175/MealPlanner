package com.example.dailymenu;

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
}
