package com.example.dailymenu.UI.Search.Features.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dailymenu.UI.Area.View.OnAreaClick;
import com.example.dailymenu.UI.Catigory.View.OnCategoryClick;
import com.example.dailymenu.UI.Ingrediants.View.AllIngrediantsRecyclerView;
import com.example.dailymenu.UI.Area.View.AreaRecycleView;
import com.example.dailymenu.UI.Catigory.View.CatigoriesRecycleView;
import com.example.dailymenu.UI.Ingrediants.View.OnIngrediantClick;
import com.example.dailymenu.Model.DTO.Area;
import com.example.dailymenu.Model.DTO.Catigory;
import com.example.dailymenu.Model.DTO.Ingredient;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.UI.Search.Features.Presenter.SearchPresenter;
import com.example.dailymenu.Utils.Network;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class SearchFragment extends Fragment implements OnAreaClick  , OnIngrediantClick , OnCategoryClick {

    List<Catigory> catigories;
    List<Area> areas;
    List<Ingredient> ingredients;
    ChipGroup chips ;
    String checked = "";
    RecyclerView recyclerView;
    EditText search;
    AllIngrediantsRecyclerView ingredientRecycleView;
    CatigoriesRecycleView catigoriesRecycleView;
    NestedScrollView scrollView;
    AreaRecycleView areaRecycleView;
    BottomNavigationView bottomNav;
    SearchPresenter presenter;
    LottieAnimationView network , loading;
    View con;
    TextInputLayout textInputLayout;

    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_filters);
        chips = view.findViewById(R.id.chipGroup);
         textInputLayout = view.findViewById(R.id.et_search);
        search = textInputLayout.getEditText();
        scrollView = view.findViewById(R.id.scrollview);
        bottomNav = view.findViewById(R.id.bottom_nav);
        con = view.findViewById(R.id.search_layout);
        network = view.findViewById(R.id.lottie_lostNetwork);
        loading = view.findViewById(R.id.lottie_loading);
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
        loading.setVisibility(View.VISIBLE);
        presenter = new SearchPresenter(this ,
                Repositry.getInstance(MealRemoteDataSource.getInstance() ,
                        MealLocalDataSource.getInstance(getContext())));
        presenter.getCatigories();
        presenter.getArea();
        presenter.getIngrediants();
        for(int i = 0; i < chips.getChildCount() ; i++)
        {
            Chip chip = (Chip) chips.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        checked = chip.getText().toString();
                        updateRecyclerView();
                    }
                }
            });
        }
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (checked.equals("Categories")) {
                   List<Catigory> filter = presenter.seachCategories(catigories , charSequence.toString());
                   catigoriesRecycleView.setFilteredList(filter);
                   catigoriesRecycleView.notifyDataSetChanged();
                }
                else  if (checked.equals("Areas"))
                {
                    List<Area> filter = presenter.seachArea(areas , charSequence.toString());
                    areaRecycleView.setFilteredList(filter);
                    areaRecycleView.notifyDataSetChanged();
                }
                else if (checked.equals("Ingrediants")){
                    List<Ingredient> filter = presenter.seachIngrediant(ingredients , charSequence.toString());
                    ingredientRecycleView.setFilteredList(filter);
                    ingredientRecycleView.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void updateRecyclerView() {

        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 3));

        if (checked.equals("Categories")) {
            catigoriesRecycleView = new CatigoriesRecycleView(requireContext(), catigories, this);
            recyclerView.setAdapter(catigoriesRecycleView);
            textInputLayout.setHint("Search By Category");

        }
        else if (checked.equals("Areas")) {
            areaRecycleView = new AreaRecycleView(requireContext(), areas, this);
            recyclerView.setAdapter(areaRecycleView);
            textInputLayout.setHint("Search By Area");

        }
        else if (checked.equals("Ingrediants")) {
            ingredientRecycleView = new AllIngrediantsRecyclerView(requireContext(), ingredients, this);
            recyclerView.setAdapter(ingredientRecycleView);
            textInputLayout.setHint("Search By Ingrediant");

        }
    }




    @Override
    public void onclick(View view , String area) {
        Log.i("Search Fragment", "onclick: " + area);
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealsFragment("area" , area);
        Navigation.findNavController(view).navigate(action);
    }
    public void setCategoriesList(List<Catigory> catigoriesList) {
        loading.setVisibility(View.GONE);
        this.catigories = catigoriesList;


    }

    public void setAreaLis(List<Area> areasList) {
        loading.setVisibility(View.GONE);
        this.areas = areasList ;

    }

    public void setIngrediantList(List<Ingredient> ingredientsList) {
        loading.setVisibility(View.GONE);
        this.ingredients =  ingredientsList;

    }

    @Override
    public void onIngrediantClick(View view, String ingrediant) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealsFragment("ingrediant" , ingrediant);
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onCategoryClick(View view, String category) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealsFragment("catigory" , category);
        Navigation.findNavController(view).navigate(action);
    }
}