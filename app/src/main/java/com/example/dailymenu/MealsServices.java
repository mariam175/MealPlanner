package com.example.dailymenu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsServices {
   @GET("random.php")
    Call<MealsResponse>getRandomMeals();
}
