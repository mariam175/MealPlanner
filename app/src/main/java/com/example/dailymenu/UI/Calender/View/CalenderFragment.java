package com.example.dailymenu.UI.Calender.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dailymenu.UI.Calender.Presenter.CalenderPresenter;
import com.example.dailymenu.Model.DTO.MealsPlan;
import com.example.dailymenu.Network.MealRemoteDataSource;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.example.dailymenu.R;
import com.example.dailymenu.Utils.UserSharedPrefrence;
import com.example.dailymenu.db.MealLocalDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalenderFragment extends Fragment implements OnPlanListener{
    CalendarView calender;
    RecyclerView recyclerView;
    String date;
    CalenderPresenter presenter;
    PlansRecyclerView plansRecyclerView;
    LottieAnimationView empty;
    private static final String TAG = "CalenderFragment";
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    String todayDate;
    public CalenderFragment() {
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
        return inflater.inflate(R.layout.fragment_calender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calender = view.findViewById(R.id.calender);
        recyclerView = view.findViewById(R.id.rv_plans);
        empty = view.findViewById(R.id.lottie_empty);

        todayDate = String.valueOf(year) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
        presenter = new CalenderPresenter(this , Repositry.getInstance(MealRemoteDataSource.getInstance() , MealLocalDataSource.getInstance(getContext())));
        plansRecyclerView = new PlansRecyclerView(new ArrayList<>() , getContext() , this);
        presenter.getPlansByDate(todayDate , UserSharedPrefrence.getUserId(requireContext()));
       calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
           @Override
           public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
               date = String.valueOf(i) + "/" + String.valueOf(i1 + 1) + "/" + String.valueOf(i2);
               Log.i(TAG, "onSelectedDayChange: " + date);
               presenter.getPlansByDate(date , UserSharedPrefrence.getUserId(requireContext()));
           }
       });
    }

    @Override
    public void onDeleteClick(MealsPlan meal) {
        presenter.removeFromPlan(meal);
        Toast.makeText(getContext(), "Removed From Plans", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(String id) {
        CalenderFragmentDirections.ActionCalenderFragmentToMealDetailsFragment action =
                CalenderFragmentDirections.actionCalenderFragmentToMealDetailsFragment(id);
        Navigation.findNavController(getView()).navigate(action);
    }
    public void getPlans(List<MealsPlan> mealsPlans) {
        if (mealsPlans.isEmpty())
        {
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
       else {
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            plansRecyclerView.setPlans(mealsPlans);
            recyclerView.setAdapter(plansRecyclerView);
            plansRecyclerView.notifyDataSetChanged();
        }
    }
}