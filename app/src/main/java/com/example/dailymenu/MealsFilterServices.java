package com.example.dailymenu;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsFilterServices {
    @GET("filter.php")
    Call<MealsFilterResponse> getMealsByCatigory(@Query("c")String c);
    Call<MealsFilterResponse> getMealsByArea(@Query("a")String a);
    Call<MealsFilterResponse> getMealsByIngrediants(@Query("i")String i);
}
