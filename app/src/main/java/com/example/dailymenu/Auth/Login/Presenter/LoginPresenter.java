package com.example.dailymenu.Auth.Login.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.dailymenu.Auth.Login.View.login;
import com.example.dailymenu.Home.Home;
import com.example.dailymenu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    int loginRes;
    public int login(Activity context , String emailStr , String passStr)
    {
         FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginRes = 1;

                        } else {
                            loginRes = 0;
                        }
                    }
                });
        return loginRes;
    }
}
