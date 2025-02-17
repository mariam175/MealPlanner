package com.example.dailymenu.Calender.Presenter;

import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.dailymenu.Calender.View.CalenderFragment;
import com.example.dailymenu.MealDetails.Presenter.MealDetailsPresenter;
import com.example.dailymenu.MealDetails.View.MealDetailsFragment;
import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.Network.NetworkCallBack;
import com.example.dailymenu.Network.Repositry;

import java.util.List;

public class CalenderPresenter {
        CalenderFragment calenderFragment;
        Repositry repo;
        static com.example.dailymenu.MealDetails.Presenter.MealDetailsPresenter mealDetailsPresenter = null;
        public CalenderPresenter( CalenderFragment calenderFragment , Repositry repo)
        {
            this.calenderFragment = calenderFragment;
            this.repo = repo;
        }


        public void removeFromPlan(MealsPlan meal)
        {
            repo.removeFromPlans(meal);
        }
       public LiveData<List<MealsPlan>> getPlansByDate(String date)
       {
           return repo.getPlanMealsByDate(date);
       }

}
