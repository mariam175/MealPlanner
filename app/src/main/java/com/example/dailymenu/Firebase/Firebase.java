package com.example.dailymenu.Firebase;

import android.util.Log;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.db.DAO;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Firebase {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String userId;

    public Firebase() {
        if (auth.getCurrentUser() != null) {
            userId = auth.getCurrentUser().getUid();
        } else {
            userId = null;
            Log.e("Firebase", "User is not authenticated!");
        }
    }

    public void backupMealToFirestore(Meal meal) {
        if (userId == null) {
            Log.e("Firestore", "Backup failed: User is not authenticated");
            return;
        }

        db.collection("users").document(userId)
                .collection("fav").document(meal.getIdMeal())
                .set(meal, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "New Fav meal backed up"))
                .addOnFailureListener(e -> Log.e("Firestore", "Backup failed", e));
    }
    public void backupPlanToFirestore(MealsPlan meal) {
        if (userId == null) {
            Log.e("Firestore", "Backup failed: User is not authenticated");
            return;
        }

        db.collection("users").document(userId)
                .collection("plan").document(meal.getMealId())
                .set(meal, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "New Planned meal backed up"))
                .addOnFailureListener(e -> Log.e("Firestore", "Backup failed", e));
    }
    public void deletePlanFromFirestore(MealsPlan meal) {
        if (userId == null) {
            Log.e("Firestore", "Delete failed: User is not authenticated");
            return;
        }

        db.collection("users").document(userId)
                .collection("plan").document(meal.getMealId())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal deleted successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Meal deletion failed", e));
    }
    public void deleteMealFromFirestore(Meal meal) {
        if (userId == null) {
            Log.e("Firestore", "Delete failed: User is not authenticated");
            return;
        }

        db.collection("users").document(userId)
                .collection("fav").document(meal.getIdMeal())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal deleted successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Meal deletion failed", e));
    }


    public void restoreData(MealLocalDataSource ds) {
        db.collection("users").document(userId)
                .collection("fav").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Meal meal = doc.toObject(Meal.class);
                        if (meal != null) {
                            ds.add(meal).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            ()-> Log.i("TAG", "complete Restore: "),
                                            err -> Log.i("TAG", err.getMessage())
                                    );
                            Log.i("TAG", "restoreData: " + meal.getIdMeal());
                        };
                    }


                })
                .addOnFailureListener(e -> Log.e("Firestore", "Restore failed", e));

        db.collection("users").document(userId)
                .collection("plan").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        MealsPlan meal = doc.toObject(MealsPlan.class);
                        if (meal != null) {
                            ds.addMealToPlan(meal).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            ()-> Log.i("TAG", "complete Restore plans: "),
                                            err -> Log.i("TAG", err.getMessage())
                                    );
                            Log.i("TAG", "restoreData: " + meal.getMealId());
                        };
                    }


                })
                .addOnFailureListener(e -> Log.e("Firestore", "Restore failed", e));
    }

}
