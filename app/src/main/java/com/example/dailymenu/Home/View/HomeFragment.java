package com.example.dailymenu.Home.View;

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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dailymenu.Area.OnAreaClick;
import com.example.dailymenu.Home.Presenter.HomePresenter;
import com.example.dailymenu.Ingrediants.AllIngrediantsRecyclerView;
import com.example.dailymenu.Area.AreaRecycleView;
import com.example.dailymenu.Catigory.CatigoriesRecycleView;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.MealsServices;
import com.example.dailymenu.Model.Area;
import com.example.dailymenu.Model.AreaResponse;
import com.example.dailymenu.Model.Catigory;
import com.example.dailymenu.Model.CatigoryResponse;
import com.example.dailymenu.Model.IngrediantResponse;
import com.example.dailymenu.Model.Ingredient;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsResponse;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.Utils.Network;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements OnAreaClick {

    TextView mealName;
    ImageView mealImage;
    Meal meal;
     List<Catigory>catigoriesList;
    List<Area>areasList;
    List<Ingredient> ingredients;
    String fragment = "homeFragment";
    HomePresenter presenter;

    RecyclerView recyclerView , areaRecycler , ingrediantRecycler;
    LottieAnimationView network;
    View con;
    public HomeFragment() {
        // Required empty public constructor
    }

    List<Area> limitedList;
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
        con = view.findViewById(R.id.homeView);
        network = view.findViewById(R.id.lottie_lostNetwork);
        if (!Network.isNetworkAvailable(requireContext())) {

            network.setVisibility(View.VISIBLE);
            con.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            network.setVisibility(View.GONE);
            con.setVisibility(View.VISIBLE);
        }

        presenter = new HomePresenter(this ,
                Repositry.getInstance(MealRemoteDataSource.getInstance() ,
                        MealLocalDataSource.getInstance(requireContext())));
        presenter.getRabdomMeal();
        presenter.getCatigories();
        presenter.getArea();
        presenter.getIngrediants();
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
    }

    @Override
    public void onclick(View view, String area) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment action =
                HomeFragmentDirections.actionHomeFragmentToMealsFragment("area" , area);
        Navigation.findNavController(view).navigate(action);
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
        mealName.setText(meal.getStrMeal());
        Glide.with(getContext()).load(meal.getStrMealThumb())
                .apply(new RequestOptions().override(300 , 300))
                .into(mealImage);
    }
    public void setCategoriesList(List<Catigory> catigories)
    {
        this.catigoriesList = catigories;
        List<Catigory> limitedList = catigoriesList.subList(0, 6);
        CatigoriesRecycleView catigoriesRecycleView = new CatigoriesRecycleView(requireContext() , limitedList, getView() , fragment);
        recyclerView.setAdapter(catigoriesRecycleView);
    }
    public void setAreaLis(List<Area>areas)
    {
        this.areasList = areas;
        limitedList = areasList.subList(0, 6);
        AreaRecycleView areasRecycleView = new AreaRecycleView(requireContext() , limitedList, HomeFragment.this);
        areaRecycler.setAdapter(areasRecycleView);
    }
    public void setIngrediantList(List<Ingredient>ingredientsList)
    {
        this.ingredients = ingredientsList;
        List<Ingredient> limitedList = ingredients.subList(0, 6);
        AllIngrediantsRecyclerView allIngrediantsRecyclerView = new AllIngrediantsRecyclerView(requireContext() , limitedList, fragment);
        ingrediantRecycler.setAdapter(allIngrediantsRecyclerView);
    }
}