package com.example.dailymenu.Network;

import android.util.Log;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    static MealRemoteDataSource mealRemoteDataSource = null;
    private static final String TAG = "ClientApi";
    private  MealsServices mealsServices;
    private Meal meal;
    private MealRemoteDataSource()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
    public void getMealById(NetworkCallBack networkCallBack , String id)
    {

        Call<MealsResponse> call = mealsServices.getMealById(id);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful())
                {
                    MealsResponse mealsResponse = response.body();
                    List<Meal> meals = mealsResponse.getMeals();
                   networkCallBack.OnSuccess(meals);
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.i("TAG2", "onResponse: fail" );
            }
        });
    }

}
