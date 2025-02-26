package com.example.dailymenu.UI.Profile.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.UI.Profile.View.ProfileFragment;

public class ProfilePresenter {
    ProfileFragment profileFragment;
    Repositry repo;

    public ProfilePresenter(ProfileFragment profileFragment, Repositry repo) {
        this.profileFragment = profileFragment;
        this.repo = repo;
    }
    public static boolean isLogged(Context context)
    {
        SharedPreferences isLogged;
        boolean logged;
        isLogged = context.getSharedPreferences("Logged" , Context.MODE_PRIVATE);
        logged = isLogged.getBoolean("isLogin" , false);
        return logged;
    }
    public void logout()
    {
        repo.logout();
    }
}
