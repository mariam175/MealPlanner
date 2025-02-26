package com.example.dailymenu.UI.splah.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dailymenu.R;
import com.example.dailymenu.UI.splah.Presenter.SplashPresenter;


public class SplashFragment extends Fragment {
    SplashPresenter presenter;

    public SplashFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            presenter = new SplashPresenter(requireContext());
            boolean logged = presenter.isLogged();

        new Handler().postDelayed(() -> {
            if (logged) {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
            } else {
                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
            }
        }, 2000);
    }
}