package com.example.dailymenu.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dailymenu.R;

public class LoginDialog {

    public static void showFavoriteDialog(Context context, View view) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.login_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LottieAnimationView animationView = dialog.findViewById(R.id.lottie_fav);
        Button btnGoToLogin = dialog.findViewById(R.id.btnG2login);
        Button close = dialog.findViewById(R.id.btnClose);
        btnGoToLogin.setOnClickListener(v -> {
            dialog.dismiss();
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.loginFragment);
        });
        close.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}
