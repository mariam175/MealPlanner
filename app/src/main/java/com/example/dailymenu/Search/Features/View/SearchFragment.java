package com.example.dailymenu.Search.Features.View;

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

import com.example.dailymenu.Area.OnAreaClick;
import com.example.dailymenu.Ingrediants.AllIngrediantsRecyclerView;
import com.example.dailymenu.Area.AreaRecycleView;
import com.example.dailymenu.Catigory.CatigoriesRecycleView;
import com.example.dailymenu.Home.View.HomeFragment;
import com.example.dailymenu.Model.Area;
import com.example.dailymenu.Model.Catigory;
import com.example.dailymenu.Model.Ingredient;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.Search.Features.Presenter.SearchPresenter;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment implements OnAreaClick {

    List<Catigory> catigories;
    List<Area> areas;
    List<Ingredient> ingredients;
    ChipGroup chips ;
    String checked = "";
    RecyclerView recyclerView;
    EditText search;
    String fragmentName = "searchFragment";
    AllIngrediantsRecyclerView ingredientRecycleView;
    CatigoriesRecycleView catigoriesRecycleView;
    NestedScrollView scrollView;
    AreaRecycleView areaRecycleView;
    BottomNavigationView bottomNav;
    SearchPresenter presenter;

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
        search = view.findViewById(R.id.et_search);
        scrollView = view.findViewById(R.id.scrollview);
        bottomNav = view.findViewById(R.id.bottom_nav);
        presenter = new SearchPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        presenter.getCatigories();
        presenter.getArea();
        presenter.getIngrediants();
        if (chips.getChildCount() > 0) {
            Chip firstChip = (Chip) chips.getChildAt(0);
            firstChip.setChecked(true);
            checked = firstChip.getText().toString();
            updateRecyclerView();
        }
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
                    catigoriesRecycleView.filter(charSequence.toString());
                }
                else  if (checked.equals("Areas"))
                {
                    areaRecycleView.filter(charSequence.toString());
                }
                else {
                    ingredientRecycleView.filter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void updateRecyclerView() {
        if (checked.equals("Categories")) {
            if (catigories == null) {
                catigories = new ArrayList<>();
            }
            catigoriesRecycleView = new CatigoriesRecycleView(requireContext(), catigories, getView(), fragmentName);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            recyclerView.setAdapter(catigoriesRecycleView);
            catigoriesRecycleView.notifyDataSetChanged();
        } else if (checked.equals("Areas")) {
            if (areas == null) {
                areas = new ArrayList<>();
            }
            areaRecycleView = new AreaRecycleView(requireContext(), areas, this);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            recyclerView.setAdapter(areaRecycleView);
            areaRecycleView.notifyDataSetChanged();
        } else {
            if (ingredients == null) {
                ingredients = new ArrayList<>();
            }
            ingredientRecycleView = new AllIngrediantsRecyclerView(requireContext(), ingredients, fragmentName);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
            recyclerView.setAdapter(ingredientRecycleView);
            ingredientRecycleView.notifyDataSetChanged();
        }
    }


    @Override
    public void onclick(View view , String area) {
        if (area == null || area.isEmpty()) {
            return;
        }
        Log.i("Search Fragment", "onclick: " + area);
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealsFragment("area" , area);
        Navigation.findNavController(view).navigate(action);
    }
    public void setCategoriesList(List<Catigory> catigoriesList) {
        this.catigories = (catigoriesList != null) ? catigoriesList : new ArrayList<>();


    }

    public void setAreaLis(List<Area> areasList) {
        this.areas = (areasList != null) ? areasList : new ArrayList<>();

    }

    public void setIngrediantList(List<Ingredient> ingredientsList) {
        this.ingredients = (ingredientsList != null) ? ingredientsList : new ArrayList<>();

    }

}