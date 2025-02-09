package com.example.dailymenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Home extends AppCompatActivity {
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    TextView mealName;
    ImageView mealImage;
    Meal meal;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        mealName = findViewById(R.id.tv_meal_name);
        mealImage = findViewById(R.id.iv_meal_img);
        recyclerView = findViewById(R.id.rv_catigories);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealsServices mealsServices = retrofit.create(MealsServices.class);
        Call<MealsResponse> call = mealsServices.getRandomMeals();
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if(response.isSuccessful())
                {
                    MealsResponse mealsResponse = response.body();
                    List<Meal> meals = mealsResponse.getMeals();
                     meal = meals.get(0);
                    mealName.setText(meal.getStrMeal());
                    Glide.with(Home.this)
                            .load(meal.getStrMealThumb())
                            .into(mealImage);
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        mealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this , MealDetails.class);
                intent.putExtra("meal" , meal.getIdMeal());
                startActivity(intent);
            }
        });
        CatigoryServices catigoryServices = retrofit.create(CatigoryServices.class);
        Call<CatigoryResponse> callCati = catigoryServices.getAllCatigories();
       callCati.enqueue(new Callback<CatigoryResponse>() {
           @Override
           public void onResponse(Call<CatigoryResponse> call, Response<CatigoryResponse> response) {
               if (response.isSuccessful())
               {
                   CatigoryResponse catigoryResponse = response.body();
                   List<Catigory>catigoriesList = catigoryResponse.getCatigoryList();
                   CatigoriesRecycleView catigoriesRecycleView = new CatigoriesRecycleView(Home.this , catigoriesList.toArray(new Catigory[0]));
                   recyclerView.setAdapter(catigoriesRecycleView);
               }
           }

           @Override
           public void onFailure(Call<CatigoryResponse> call, Throwable throwable) {
                throwable.printStackTrace();
           }
       });
    }
}