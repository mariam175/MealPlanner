package com.example.dailymenu.UI.Search.Features.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.dailymenu.Model.DTO.Area;
import com.example.dailymenu.Model.DTO.Catigory;
import com.example.dailymenu.Model.DTO.Ingredient;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.UI.Search.Features.View.SearchFragment;

import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    SearchFragment searchFragment;
    Repositry repo;

    public SearchPresenter(SearchFragment searchFragment, Repositry repo) {
        this.searchFragment = searchFragment;
        this.repo = repo;
    }
    public void getCatigories()
    {
        repo.getCatigories().subscribeOn(Schedulers.io())
                .map(item -> item.getCatigoryList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            searchFragment.setCategoriesList(item);
                            Log.i("TAG", "getCatigories: Search");
                        },
                        err-> Toast.makeText(searchFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
    public void getArea()
    {
        repo.getArea().subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            searchFragment.setAreaLis(item);
                        },
                        err-> Toast.makeText(searchFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
    public void getIngrediants()
    {
        repo.getIngrediants().subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            searchFragment.setIngrediantList(item);
                        },
                        err-> Toast.makeText(searchFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
    public List<Catigory> seachCategories(List<Catigory> catigories , String str)
    {
        List<Catigory> filter = catigories.stream()
                .filter(cat -> cat.getStrCategory().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toList());
        return filter;
    }
    public List<Area> seachArea(List<Area> areas , String str)
    {
        List<Area> filter = areas.stream()
                .filter(area -> area.getStrArea().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toList());
        return filter;
    }
    public List<Ingredient> seachIngrediant(List<Ingredient> ingredients , String str)
    {
        List<Ingredient> filter = ingredients.stream()
                .filter(ing -> ing.getStrIngredient().toLowerCase().contains(str.toLowerCase()))
                .collect(Collectors.toList());
        return filter;
    }
}
