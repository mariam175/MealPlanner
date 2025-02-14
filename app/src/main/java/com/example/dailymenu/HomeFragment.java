package com.example.dailymenu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {

    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    TextView mealName;
    ImageView mealImage;
    Meal meal;
    static List<Catigory>catigoriesList;
    static  List<Area>areasList;
    static List<Ingredient> ingredients;
    String fragment = "homeFragment";
    RecyclerView recyclerView , areaRecycler , ingrediantRecycler;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealName = view.findViewById(R.id.tv_meal_name);
        mealImage = view.findViewById(R.id.iv_meal_img);
        recyclerView = view.findViewById(R.id.rv_catigories);
        areaRecycler = view.findViewById(R.id.rv_areas);
        ingrediantRecycler = view.findViewById(R.id.rv_ing);
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
                    Glide.with(requireContext())
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
//                Intent intent = new Intent(requireContext(), MealDetails.class);
//                intent.putExtra("meal" , meal.getIdMeal());
//                startActivity(intent);
                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(meal.getIdMeal());
                Navigation.findNavController(view).navigate(action);
            }
        });

        Call<CatigoryResponse> callCati = mealsServices.getAllCatigories();
        callCati.enqueue(new Callback<CatigoryResponse>() {
            @Override
            public void onResponse(Call<CatigoryResponse> call, Response<CatigoryResponse> response) {
                if (response.isSuccessful())
                {
                    CatigoryResponse catigoryResponse = response.body();
                    catigoriesList = catigoryResponse.getCatigoryList();
                    List<Catigory> limitedList = catigoriesList.subList(0, 6);
                    CatigoriesRecycleView catigoriesRecycleView = new CatigoriesRecycleView(requireContext() , limitedList, getView() , fragment);
                    recyclerView.setAdapter(catigoriesRecycleView);
                }
            }

            @Override
            public void onFailure(Call<CatigoryResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });

        Call<AreaResponse> callArea = mealsServices.getAreas();
        callArea.enqueue(new Callback<AreaResponse>() {

            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful())
                {
                    AreaResponse areaResponse = response.body();
                    areasList = areaResponse.getMeals();
                    List<Area> limitedList = areasList.subList(0, 6);
                    AreaRecycleView areasRecycleView = new AreaRecycleView(requireContext() , limitedList, fragment);
                    areaRecycler.setAdapter(areasRecycleView);
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        Call<IngrediantResponse>ingreCall = mealsServices.getAllIngrediants();
        ingreCall.enqueue(new Callback<IngrediantResponse>() {
            @Override
            public void onResponse(Call<IngrediantResponse> call, Response<IngrediantResponse> response) {
                if (response.isSuccessful())
                {
                    IngrediantResponse ingrediantResponse = response.body();
                    ingredients = ingrediantResponse.getMeals();
                    List<Ingredient> limitedList = ingredients.subList(0, 6);
                    AllIngrediantsRecyclerView allIngrediantsRecyclerView = new AllIngrediantsRecyclerView(requireContext() , limitedList, fragment);
                    ingrediantRecycler.setAdapter(allIngrediantsRecyclerView);
                }
            }

            @Override
            public void onFailure(Call<IngrediantResponse> call, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}