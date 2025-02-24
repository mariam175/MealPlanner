package com.example.dailymenu.Auth.Signup.Presenter;

import android.content.Context;

import com.example.dailymenu.Auth.Login.View.LoginFragment;
import com.example.dailymenu.Auth.Signup.View.SignupFragment;
import com.example.dailymenu.Firebase.AuthResonse;
import com.example.dailymenu.Network.Repositry;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SignupPresenter {
    SignupFragment signupFragment;
    Repositry repo;

    public SignupPresenter( SignupFragment signupFragment, Repositry repo) {
        this.signupFragment = signupFragment;
        this.repo = repo;
    }
    public void singnup(String email , String pass , AuthResonse resonse) {
        repo.singnupWithEmailAndPassword(email, pass, resonse);
    }
    public void signupWithGoogle(GoogleSignInAccount account , AuthResonse resonse , Context context)
    {
        repo.loginWithGoogle(account, resonse , context);
    }
}
