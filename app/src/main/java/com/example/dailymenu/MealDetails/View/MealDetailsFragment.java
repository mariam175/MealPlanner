package com.example.dailymenu.MealDetails.View;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dailymenu.MealDetails.Presenter.MealDetailsPresenter;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.MealsServices;
import com.example.dailymenu.Model.IngridentItem;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsResponse;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FloatingActionButton fav;
    MealDetailsPresenter presenter;
    YouTubePlayerView youtubePlayerView;
    SharedPreferences sharedPreferences;
    boolean isFav;
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
        fav = view.findViewById(R.id.fab_addFav);
         youtubePlayerView = view.findViewById(R.id.youtube_player_view);
        id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();
        presenter = new MealDetailsPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        meal = null;
         sharedPreferences = requireContext().getSharedPreferences("favs" , Context.MODE_PRIVATE);
         isFav = sharedPreferences.getBoolean(id , false);
        presenter.getMealById(id);
        if (meal != null)
        {
            updateUI();
        }
        Log.i("TAG", "onViewCreated: " + id);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (!isFav)
               {
                   presenter.addToFav(meal);
                   fav.setImageResource(R.drawable.baseline_favorite_24);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putBoolean(id , true);
                   editor.commit();
                   Toast.makeText(getContext(), "Added To Favs", Toast.LENGTH_SHORT).show();
               }
               else {
                   presenter.removeFromFav(meal);
                   fav.setImageResource(R.drawable.favorite);
                   SharedPreferences.Editor editor = sharedPreferences.edit();
                   editor.putBoolean(id , false);
                   editor.commit();
                   Toast.makeText(getContext(), "Removed From Favs", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
    public void setMeal(Meal meal)
    {
        this.meal = meal;

            updateUI();


    }

    private void updateUI() {

        instr.setText(meal.getStrInstructions());
        area.setText(meal.getStrArea());

        if (isFav)
        {
            fav.setImageResource(R.drawable.baseline_favorite_24);
        }
        getLifecycle().addObserver(youtubePlayerView);

        String videoId = extractVideoId(meal.getStrYoutube());

        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {

                youTubePlayer.cueVideo(videoId, 0);
                youtubePlayerView.setOnClickListener(v -> youTubePlayer.play());
            }
        });

        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .into(img);
        getIngrediants(meal);
        IngredientRecycleView myRecyleView = new IngredientRecycleView(requireContext() , ingridentItemList.toArray(new IngridentItem[0]));
        recyclerView.setAdapter(myRecyleView);
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
                videoId = split[1].split("&")[0];
            }
        }
        return videoId;
    }
}