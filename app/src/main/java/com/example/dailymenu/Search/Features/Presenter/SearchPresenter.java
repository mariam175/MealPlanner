package com.example.dailymenu.Search.Features.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.Search.Features.View.SearchFragment;

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
}
