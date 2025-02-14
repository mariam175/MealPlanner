package com.example.dailymenu.Search;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.dailymenu.Area.OnAreaClick;
import com.example.dailymenu.Ingrediants.AllIngrediantsRecyclerView;
import com.example.dailymenu.Area.AreaRecycleView;
import com.example.dailymenu.Catigory.CatigoriesRecycleView;
import com.example.dailymenu.Home.HomeFragment;
import com.example.dailymenu.Model.Area;
import com.example.dailymenu.Model.Catigory;
import com.example.dailymenu.Model.Ingredient;
import com.example.dailymenu.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;


public class SearchFragment extends Fragment implements OnAreaClick {

    List<Catigory> catigories = HomeFragment.catigoriesList;
    List<Area> areas = HomeFragment.areasList;
    List<Ingredient> ingredients = HomeFragment.ingredients;
    ChipGroup chips ;
    String checked = "";
    RecyclerView recyclerView;
    EditText search;
    String fragmentName = "searchFragment";
    AllIngrediantsRecyclerView ingredientRecycleView;
    CatigoriesRecycleView catigoriesRecycleView;
    NestedScrollView scrollView;
    AreaRecycleView areaRecycleView;
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
        search.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                scrollView.postDelayed(() -> scrollView.smoothScrollTo(0, search.getBottom()), 200);
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (checked.equals("Catigories")) {
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

        if (checked.equals("Catigories")) {
             catigoriesRecycleView = new CatigoriesRecycleView(requireContext() , catigories , getView() , fragmentName);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext() , 2));
            recyclerView.setAdapter(catigoriesRecycleView);
        }
        else if (checked.equals("Areas"))
        {
             areaRecycleView = new AreaRecycleView(requireContext() , areas, this);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext() , 2));
            recyclerView.setAdapter(areaRecycleView);
        }
        else {
             ingredientRecycleView = new AllIngrediantsRecyclerView(requireContext() , ingredients , fragmentName);
            recyclerView.setLayoutManager(new GridLayoutManager(requireContext() , 2));
            recyclerView.setAdapter(ingredientRecycleView);
        }
    }

    @Override
    public void onclick(View view , String area) {
        SearchFragmentDirections.ActionSearchFragmentToMealsFragment action =
                SearchFragmentDirections.actionSearchFragmentToMealsFragment("area" , area);
        Navigation.findNavController(view).navigate(action);
    }
}