package com.example.dailymenu.UI.Profile.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.UI.Profile.Presenter.ProfilePresenter;
import com.example.dailymenu.R;
import com.example.dailymenu.Utils.UserSharedPrefrence;
import com.example.dailymenu.db.MealLocalDataSource;

public class ProfileFragment extends Fragment {
    TextView email;
    Button logout;
    ProfilePresenter presenter;
    SharedPreferences isLogged;
    SharedPreferences.Editor editor;
    boolean logged;
    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.tv_email);
        logout = view.findViewById(R.id.btn_logout);

        presenter = new ProfilePresenter(this , Repositry.getInstance(
                MealRemoteDataSource.getInstance(),
                MealLocalDataSource.getInstance(requireContext())
        ));
        Toast.makeText(requireContext(), UserSharedPrefrence.getUserEmail(requireContext()), Toast.LENGTH_SHORT).show();
        email.setText(UserSharedPrefrence.getUserEmail(requireContext()));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogged = requireContext().getSharedPreferences("Logged" , Context.MODE_PRIVATE);
                editor = isLogged.edit();
                editor.putBoolean("isLogin" , false);
                editor.commit();
                presenter.logout();
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });
    }
}