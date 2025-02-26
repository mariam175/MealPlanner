//package com.example.dailymenu.Search.View;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.dailymenu.Network.MealsServices;
//import com.example.dailymenu.Model.DTO.MealsFilter;
//import com.example.dailymenu.Model.Response.MealsFilterResponse;
//import com.example.dailymenu.R;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class Meals extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
//    String type;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_meals);
//
//        recyclerView = findViewById(R.id.rv_meals);
//        Intent income = getIntent();
//        type = income.getStringExtra("Type");
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        MealsServices mealsServices = retrofit.create(MealsServices.class);
//        Call<MealsFilterResponse> call = null;
//        String rel;
//       if (type.equals("area"))
//       {
//            rel = income.getStringExtra("Area");
//           call = mealsServices.getMealsByArea(rel);
//       }
//       else  if (type.equals("catigory"))
//       {
//            rel = income.getStringExtra("Catigory");
//           call = mealsServices.getMealsByCatigory(rel);
//       }
//       else {
//           rel = income.getStringExtra("Ingrediant");
//           call = mealsServices.getMealsByIngrediants(rel);
//       }
//        call.enqueue(new Callback<MealsFilterResponse>() {
//            @Override
//            public void onResponse(Call<MealsFilterResponse> call, Response<MealsFilterResponse> response) {
//                if (response.isSuccessful())
//                {
//                    MealsFilterResponse mealsResponse = response.body();
//                    List<MealsFilter> meals = mealsResponse.getMeals();
//                    Log.i("TAG", "onResponse: " + meals.size());
//                    MealsRecycleView mealsRecycleView = new MealsRecycleView(Meals.this , meals.toArray(new MealsFilter[0]));
//                    recyclerView.setAdapter(mealsRecycleView);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MealsFilterResponse> call, Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
//    }
//}