package com.example.dailymenu.UI.MealDetails.Presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.dailymenu.UI.MealDetails.View.MealDetailsFragment;
import com.example.dailymenu.Model.DTO.IngridentItem;
import com.example.dailymenu.Model.DTO.Meal;
import com.example.dailymenu.Model.DTO.MealsFav;
import com.example.dailymenu.Model.DTO.MealsPlan;
import com.example.dailymenu.Model.Repositry.Repositry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsPresenter{
    MealDetailsFragment mealDetailsFragment;
    Repositry repo;
    static MealDetailsPresenter mealDetailsPresenter = null;
    public MealDetailsPresenter( MealDetailsFragment mealDetailsFragment , Repositry repo)
    {
        this.mealDetailsFragment = mealDetailsFragment;
        this.repo = repo;
    }

    public void getMealById(String id)
    {
         repo.getMealById(id).subscribeOn(Schedulers.io())
                 .map(item -> item.getMeals())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         item ->mealDetailsFragment.setMeal(item.get(0)),
                         err -> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                 );
    }

    public void getMealByIdLocal(String id , String userId)
    {
        repo.getMealById(id , userId).subscribeOn(Schedulers.io())
                .map(item -> item.getMeal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item ->mealDetailsFragment.setMeal(item),
                        err -> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
    public void getPlannedMealById(String id , String userId)
    {
        repo.getPlanMealById(id , userId).subscribeOn(Schedulers.io())
                .map(item -> item.getMeal())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item ->mealDetailsFragment.setMeal(item),
                        err -> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
    public void addToFav(MealsFav meal)
    {
        repo.addToFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->  Toast.makeText(mealDetailsFragment.requireContext(), "Added To Favourites", Toast.LENGTH_SHORT).show(),
                        err-> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
        repo.backupFavMeal(meal , mealDetailsFragment.requireContext());
    }
    public void isFavMeal(String id , String userId)
    {

        repo.isFavMeal(id , userId).subscribeOn(Schedulers.io())
                .map(item -> item > 0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item-> {
                            mealDetailsFragment.setFav(item);
                            Log.i("Fav", "isFavMeal: " + item);
                        }

                );
    }
    public void removeFromFav(MealsFav meal)
    {
        repo.deleteFavMealFirebase(meal , mealDetailsFragment.requireContext());
        repo.removeFromFav(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->  Toast.makeText(mealDetailsFragment.requireContext(), "Removed Favourites", Toast.LENGTH_SHORT).show(),
                        err-> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
    public void addMealToPlan(MealsPlan mealsPlan)
    {
        repo.backupPlans(mealsPlan , mealDetailsFragment.requireContext());
        repo.addMealToPlans(mealsPlan).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ()->  Toast.makeText(mealDetailsFragment.requireContext(), "Added To Plans", Toast.LENGTH_SHORT).show(),
                        err-> Toast.makeText(mealDetailsFragment.requireContext(), err.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
    public  void  getIngrediants(Meal meal , List<IngridentItem> ingridentItemList)
    {
        Gson gson = new GsonBuilder().create();
        String mealJson = gson.toJson(meal);
        Map<String , String> ingrediant = gson.fromJson(mealJson , Map.class);
        for (int i = 1; i <= 20; i++)
        {
            if (ingrediant.get("strIngredient"+i) == null || ingrediant.get("strIngredient"+i).isEmpty())
                break;
            else
            {
                ingridentItemList.add(new IngridentItem(ingrediant.get("strIngredient"+i) , ingrediant.get("strMeasure"+i) , "https://www.themealdb.com/images/ingredients/"+ingrediant.get("strIngredient"+i)+".png"));
            }
        }
    }
}
