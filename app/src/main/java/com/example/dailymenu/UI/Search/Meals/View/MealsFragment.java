package com.example.dailymenu.UI.Search.Meals.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Model.DTO.MealsFilter;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.UI.Search.Meals.Presenter.MealsPresenter;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;


public class MealsFragment extends Fragment implements OnMealClick{
    RecyclerView recyclerView;
    List<MealsFilter> meals;
    MealsPresenter presenter;
    String rel;
    String type;
    MealsRecycleView mealsRecycleView;
    EditText search;
    TextView relation;
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
        TextInputLayout textInputLayout = view.findViewById(R.id.meals_search);
        search = textInputLayout.getEditText();
        relation = view.findViewById(R.id.rel);

        type = MealsFragmentArgs.fromBundle(getArguments()).getType();
        presenter = new MealsPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        rel = MealsFragmentArgs.fromBundle(getArguments()).getRel();
        if (type.equals("area"))
        {
            presenter.getMealsByArea(rel);
        }
        else  if (type.equals("catigory"))
        {
            presenter.getMealsByCategory(rel);
        }
        else {
           presenter.getMealsByIngrediant(rel);
        }
        relation.setText(rel);
        search.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<MealsFilter> filters = presenter.seachMeals(meals , charSequence.toString());
                mealsRecycleView.setFilters(filters);
                mealsRecycleView.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void setMeal(List<MealsFilter>meals)
    {
        this.meals = meals;
         mealsRecycleView = new MealsRecycleView(requireContext(), meals , this);
        recyclerView.setAdapter(mealsRecycleView);
    }

    @Override
    public void onMealClick(String id, View view) {
        MealsFragmentDirections.ActionMealsFragmentToMealDetailsFragment action =
                MealsFragmentDirections.actionMealsFragmentToMealDetailsFragment(id);

        Navigation.findNavController(view).navigate(action);
    }
}