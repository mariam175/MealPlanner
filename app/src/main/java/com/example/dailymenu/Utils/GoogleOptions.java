package com.example.dailymenu.Utils;

import static android.provider.Settings.System.getString;

import android.content.Context;

import com.example.dailymenu.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class GoogleOptions {
    public static GoogleSignInClient Options(Context context , String clientId) {
        GoogleSignInClient client;
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(clientId)
                .requestEmail()
                .build();
        client = GoogleSignIn.getClient(context,options);
        return client;
    }
}
