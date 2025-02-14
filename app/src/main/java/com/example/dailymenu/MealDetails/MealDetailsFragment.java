package com.example.dailymenu.MealDetails;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dailymenu.Network.MealsServices;
import com.example.dailymenu.Model.IngridentItem;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsResponse;
import com.example.dailymenu.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MealDetailsFragment extends Fragment {
    TextView instr;
    ImageView img;
    String id;
    RecyclerView recyclerView;
    Meal meal;
    TextView area;
    List<IngridentItem> ingridentItemList;
    static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    public MealDetailsFragment() {
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
        return inflater.inflate(R.layout.activity_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingridentItemList = new ArrayList<>();
        instr = view.findViewById(R.id.tv_instractions);
        img = view.findViewById(R.id.iv_image);
        recyclerView = view.findViewById(R.id.rv_ingrediants);
        area = view.findViewById(R.id.tv_area);

        id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MealsServices mealsServices = retrofit.create(MealsServices.class);
        Call<MealsResponse> call = mealsServices.getMealById(id);
        Log.i("TAG2", "onResponse: " + id);
        call.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                if (response.isSuccessful())
                {
                    MealsResponse mealsResponse = response.body();
                    List<Meal> meals = mealsResponse.getMeals();
                    meal = meals.get(0);
                    instr.setText(meal.getStrInstructions());
                    area.setText(meal.getStrArea());

                    YouTubePlayerView youtubePlayerView = view.findViewById(R.id.youtube_player_view);
                    getLifecycle().addObserver(youtubePlayerView);

                    String videoId = extractVideoId(meal.getStrYoutube());

                    youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {

                            youTubePlayer.cueVideo(videoId, 0);
                            youtubePlayerView.setOnClickListener(v -> youTubePlayer.play());
                        }
                    });

                    Glide.with(requireContext())
                            .load(meal.getStrMealThumb())
                            .into(img);
                    getIngrediants(meal);
                    IngredientRecycleView myRecyleView = new IngredientRecycleView(requireContext() , ingridentItemList.toArray(new IngridentItem[0]));
                    recyclerView.setAdapter(myRecyleView);
                    Log.i("TAG2", "onResponse: " + meals.size());
                }
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                Log.i("TAG2", "onResponse: fail" );
            }
        });


    }
    void  getIngrediants(Meal meal)
    {
        Gson gson = new GsonBuilder().create();
        String mealJson = gson.toJson(meal);
        Map<String , String> ingrediant = gson.fromJson(mealJson , Map.class);
        for (int i = 1; i <= 20; i++)
        {
            if (ingrediant.get("strIngredient"+i) == null || ingrediant.get("strIngredient"+i).isEmpty())
                break;
            else
            {
                ingridentItemList.add(new IngridentItem(ingrediant.get("strIngredient"+i) , ingrediant.get("strMeasure"+i) , "https://www.themealdb.com/images/ingredients/"+ingrediant.get("strIngredient"+i)+".png"));
            }
        }
    }
    private String extractVideoId(String youtubeUrl) {
        String videoId = "";
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0) {
            String[] split = youtubeUrl.split("v=");
            if (split.length > 1) {
                videoId = split[1].split("&")[0]; // Handle cases with additional query parameters
            }
        }
        return videoId;
    }
}