package com.example.dailymenu.Profile.Presenter;

import com.example.dailymenu.Network.Repositry;
import com.example.dailymenu.Profile.View.ProfileFragment;

public class ProfilePresenter {
    ProfileFragment profileFragment;
    Repositry repo;

    public ProfilePresenter(ProfileFragment profileFragment, Repositry repo) {
        this.profileFragment = profileFragment;
        this.repo = repo;
    }
    public void logout()
    {
        repo.logout();
    }
}
