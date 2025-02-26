package com.example.dailymenu.Auth.Login.Presenter;

import android.content.Context;

import com.example.dailymenu.Auth.Login.View.LoginFragment;
import com.example.dailymenu.Firebase.AuthResonse;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class LoginPresenter {
    LoginFragment loginFragment;
    Repositry repo;

    public LoginPresenter(LoginFragment loginFragment, Repositry repo) {
        this.loginFragment = loginFragment;
        this.repo = repo;
    }
    public void loginWithEmailAndPassword(String email , String pass , AuthResonse resonse , Context context)
    {
        repo.loginWithEmailAndPassword(email, pass, resonse , context);
    }
    public void loginWithGoogle(GoogleSignInAccount account, AuthResonse resonse , Context context)
    {
        repo.loginWithGoogle(account , resonse , context);
    }
    public void resoreData()
    {
        repo.restoreData();
    }
}
