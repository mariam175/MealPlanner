package com.example.dailymenu.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailymenu.Network.MealsServices;
import com.example.dailymenu.Model.MealsFilter;
import com.example.dailymenu.Model.MealsFilterResponse;
import com.example.dailymenu.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealsFragment extends Fragment {
    RecyclerView recyclerView;
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    String type;
    public MealsFragment() {
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
        return inflater.inflate(R.layout.activity_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_meals);
        type = MealsFragmentArgs.fromBundle(getArguments()).getType();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealsServices mealsServices = retrofit.create(MealsServices.class);
        Call<MealsFilterResponse> call = null;
        String rel;
        if (type.equals("area"))
        {
            rel = MealsFragmentArgs.fromBundle(getArguments()).getRel();
            call = mealsServices.getMealsByArea(rel);
        }
        else  if (type.equals("catigory"))
        {
            rel =MealsFragmentArgs.fromBundle(getArguments()).getRel();
            call = mealsServices.getMealsByCatigory(rel);
        }
        else {
            rel = MealsFragmentArgs.fromBundle(getArguments()).getRel();
            call = mealsServices.getMealsByIngrediants(rel);
        }
        call.enqueue(new Callback<MealsFilterResponse>() {
            @Override
            public void onResponse(Call<MealsFilterResponse> call, Response<MealsFilterResponse> response) {
                if (response.isSuccessful())
                {
                    MealsFilterResponse mealsResponse = response.body();
                    List<MealsFilter> meals = mealsResponse.getMeals();
                    Log.i("TAG", "onResponse: " + meals.size());
                    MealsRecycleView mealsRecycleView = new MealsRecycleView(requireContext(), meals.toArray(new MealsFilter[0]));
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