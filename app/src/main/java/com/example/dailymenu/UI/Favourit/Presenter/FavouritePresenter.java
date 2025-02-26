package com.example.dailymenu.UI.Favourit.Presenter;

import android.widget.Toast;

import com.example.dailymenu.UI.Favourit.View.FavouritesFragment;
import com.example.dailymenu.Model.Repositry.Repositry;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {
    FavouritesFragment favouritesFragment;
    Repositry repo;
    static FavouritePresenter favouritePresenter = null;
    public FavouritePresenter(FavouritesFragment favouritesFragment , Repositry repo)
    {
        this.favouritesFragment = favouritesFragment;
        this.repo = repo;
    }

    public void getAllFavs(String userId)
    {
        repo.getFavMeals(userId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> favouritesFragment.setFavmeals(item),
                        err -> Toast.makeText(favouritesFragment.getContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

}
