package com.example.dailymenu.UI.splah.Presenter;

import android.content.Context;
import android.content.SharedPreferences;

public class SplashPresenter {
    Context context;

    public SplashPresenter(Context context) {
        this.context = context;
    }
    public  boolean isLogged()
    {
        SharedPreferences isLogged;
        boolean logged;
        isLogged = context.getSharedPreferences("Logged" ,Context.MODE_PRIVATE);
        logged = isLogged.getBoolean("isLogin" , false);
        return logged;
    }

}
