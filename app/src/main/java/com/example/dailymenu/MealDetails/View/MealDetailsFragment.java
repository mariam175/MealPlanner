package com.example.dailymenu.MealDetails.View;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailymenu.MealDetails.Presenter.MealDetailsPresenter;
import com.example.dailymenu.Model.MealsFav;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.Model.User;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.MealsServices;
import com.example.dailymenu.Model.IngridentItem;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsResponse;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.Utils.Flags;
import com.example.dailymenu.Utils.UserSharedPrefrence;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class MealDetailsFragment extends Fragment {
    TextView instr;
    ImageView img , area_img;
    String id;
    RecyclerView recyclerView;
    Meal meal;
    TextView area;
    List<IngridentItem> ingridentItemList;
    FloatingActionButton fav , plan;
    MealDetailsPresenter presenter;
    YouTubePlayerView youtubePlayerView;
    SharedPreferences isLogged;
    boolean isFav;
    boolean logged;
    String date = null;
    View content;

    LottieAnimationView loading;
    private static final String TAG = "MealDetailsFragment";
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
        meal = null;
        ingridentItemList = new ArrayList<>();
        instr = view.findViewById(R.id.tv_instractions);
        img = view.findViewById(R.id.iv_image);
        recyclerView = view.findViewById(R.id.rv_ingrediants);
        area = view.findViewById(R.id.tv_area);
        fav = view.findViewById(R.id.fab_addFav);
        plan = view.findViewById(R.id.fab_calender);
        youtubePlayerView = view.findViewById(R.id.youtube_player_view);
        area_img = view.findViewById(R.id.iv_meal_area);
        loading = view.findViewById(R.id.lottie_loading);
        content = view.findViewById(R.id.details);
        id = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();

        loading.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        presenter = new MealDetailsPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
         isLogged = requireContext().getSharedPreferences("Logged" , Context.MODE_PRIVATE);
         logged = isLogged.getBoolean("isLogin" , false);

        presenter.getMealById(id);
        presenter.isFavMeal(id , UserSharedPrefrence.getUserId(requireContext()));
        if (meal != null)
        {
            updateUI();
        }
        Log.i("TAG", "onViewCreated: " + id);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (logged)
              {
                  if (!isFav)
                  {
                      presenter.addToFav(new MealsFav(meal.getIdMeal() ,  UserSharedPrefrence.getUserId(requireContext()), meal.getStrMeal() , meal.getStrMealThumb()));
                      fav.setImageResource(R.drawable.baseline_favorite_24);
                  }
                  else {
                      presenter.removeFromFav(new MealsFav(meal.getIdMeal(), UserSharedPrefrence.getUserId(requireContext()), meal.getStrMeal(), meal.getStrMealThumb()));
                      fav.setImageResource(R.drawable.favorite);
                  }
              }
              else {
                  Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
              }
            }
        });

        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(logged)
                {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            date = String.valueOf(i) + "/" + String.valueOf(i1 + 1) + "/" + String.valueOf(i2);
                            Log.i(TAG, "onDateSet: " + date);
                            presenter.addMealToPlan(new MealsPlan(id , UserSharedPrefrence.getUserId(requireContext()), meal.getStrMeal() , meal.getStrMealThumb() , date));
                            Toast.makeText(getContext() , "added to plan" , Toast.LENGTH_SHORT).show();
                        }
                    } , year , month , day);
                    datePickerDialog.show();
                }
                else {
                    Toast.makeText(getContext(), "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void setMeal(Meal meal)
    {
        loading.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        this.meal = meal;
            updateUI();

    }

    private void updateUI() {

        instr.setText(meal.getStrInstructions());
        area.setText(meal.getStrArea());
        Glide.with(getContext())
                .applyDefaultRequestOptions(RequestOptions.circleCropTransform())
                .load(Flags.flagsImg().getOrDefault(meal.getStrArea() , R.drawable.unknown))
                .into(area_img);
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

            @Override
            public void onError(@NonNull YouTubePlayer youTubePlayer, @NonNull PlayerConstants.PlayerError error) {
                super.onError(youTubePlayer, error);
                if (error == PlayerConstants.PlayerError.VIDEO_NOT_FOUND) {

                    youtubePlayerView.setVisibility(View.GONE);
                }
            }
        });

        Glide.with(getContext())
                .load(meal.getStrMealThumb())
                .into(img);
        presenter.getIngrediants(meal , ingridentItemList);
        IngredientRecycleView myRecyleView = new IngredientRecycleView(requireContext() , ingridentItemList.toArray(new IngridentItem[0]));
        recyclerView.setAdapter(myRecyleView);
    }

    public void setFav(boolean fav) {
        isFav = fav;
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