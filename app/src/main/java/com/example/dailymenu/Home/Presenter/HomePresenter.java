package com.example.dailymenu.Home.Presenter;

import android.widget.Toast;

import com.example.dailymenu.Favourit.Presenter.FavouritePresenter;
import com.example.dailymenu.Favourit.View.FavouritesFragment;
import com.example.dailymenu.Home.View.HomeFragment;
import com.example.dailymenu.Model.Catigory;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Network.NetworkCallBack;
import com.example.dailymenu.Network.Repositry;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter implements IHomePresenter {
    HomeFragment homeFragment;
    Repositry repo;

    public HomePresenter(HomeFragment homeFragment, Repositry repo) {
        this.homeFragment = homeFragment;
        this.repo = repo;
    }
    public void getRabdomMeal()
    {
        repo.getRandomMeal().subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> homeFragment.setMeal(item.get(0)),
                        err-> Toast.makeText(homeFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }


    public void getCatigories()
    {
        repo.getCatigories().subscribeOn(Schedulers.io())
                .map(item -> item.getCatigoryList())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            homeFragment.setCategoriesList(item);
                        },
                        err-> Toast.makeText(homeFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
    public void getArea()
    {
        repo.getArea().subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            homeFragment.setAreaLis(item);
                        },
                        err-> Toast.makeText(homeFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
    public void getIngrediants()
    {
        repo.getIngrediants().subscribeOn(Schedulers.io())
                .map(item -> item.getMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            homeFragment.setIngrediantList(item);
                        },
                        err-> Toast.makeText(homeFragment.requireContext(), err.getMessage() , Toast.LENGTH_SHORT).show()
                );
    }
}
