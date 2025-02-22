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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
            repo.removePlanFirebase(meal);
            repo.removeFromPlans(meal).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ()-> Toast.makeText(calenderFragment.getContext(), "Removed From Plan", Toast.LENGTH_SHORT).show(),
                            err-> Toast.makeText(calenderFragment.getContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
       public void getPlansByDate(String date)
       {
            repo.getPlanMealsByDate(date).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item -> calenderFragment.getPlans(item),
                            err-> Toast.makeText(calenderFragment.getContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                    );
       }

}
