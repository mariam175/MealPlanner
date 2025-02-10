package com.example.dailymenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Meals extends AppCompatActivity {
    TextView Option;
    RecyclerView recyclerView;
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals);
        Option = findViewById(R.id.tv_meals_relation);
        recyclerView = findViewById(R.id.rv_meals);
        Intent income = getIntent();
        String rel = income.getStringExtra("Catigory");
        Option.setText(rel);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealsFilterServices mealsFilterServices = retrofit.create(MealsFilterServices.class);
        Call<MealsFilterResponse> call = mealsFilterServices.getMealsByCatigory(rel);
        call.enqueue(new Callback<MealsFilterResponse>() {
            @Override
            public void onResponse(Call<MealsFilterResponse> call, Response<MealsFilterResponse> response) {
                if (response.isSuccessful())
                {
                    MealsFilterResponse mealsResponse = response.body();
                    List<MealsFilter> meals = mealsResponse.getMeals();
                    Log.i("TAG", "onResponse: " + meals.size());
                    MealsRecycleView mealsRecycleView = new MealsRecycleView(Meals.this , meals.toArray(new MealsFilter[0]));
                    recyclerView.setAdapter(mealsRecycleView);
                }
            }

            @Override
            public void onFailure(Call<MealsFilterResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}