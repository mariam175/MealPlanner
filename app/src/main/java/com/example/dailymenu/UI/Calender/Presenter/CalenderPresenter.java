package com.example.dailymenu.UI.Calender.Presenter;

import android.widget.Toast;

import com.example.dailymenu.UI.Calender.View.CalenderFragment;
import com.example.dailymenu.UI.MealDetails.Presenter.MealDetailsPresenter;
import com.example.dailymenu.Model.DTO.MealsPlan;
import com.example.dailymenu.Model.Repositry.Repositry;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenter {
        CalenderFragment calenderFragment;
        Repositry repo;
        static MealDetailsPresenter mealDetailsPresenter = null;
        public CalenderPresenter( CalenderFragment calenderFragment , Repositry repo)
        {
            this.calenderFragment = calenderFragment;
            this.repo = repo;
        }


        public void removeFromPlan(MealsPlan meal)
        {
            repo.removePlanFirebase(meal , calenderFragment.getContext());
            repo.removeFromPlans(meal).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            ()-> Toast.makeText(calenderFragment.getContext(), "Removed From Plan", Toast.LENGTH_SHORT).show(),
                            err-> Toast.makeText(calenderFragment.getContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
       public void getPlansByDate(String date , String userId)
       {
            repo.getPlanMealsByDate(date , userId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            item -> calenderFragment.getPlans(item),
                            err-> Toast.makeText(calenderFragment.getContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                    );
       }

}
