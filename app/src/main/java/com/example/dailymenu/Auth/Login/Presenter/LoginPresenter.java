package com.example.dailymenu.Auth.Login.Presenter;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.dailymenu.Auth.Login.View.LoginFragment;
import com.example.dailymenu.Calender.View.CalenderFragment;
import com.example.dailymenu.Firebase.AuthResonse;
import com.example.dailymenu.Network.Repositry;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
