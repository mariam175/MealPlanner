package com.example.dailymenu.Firebase;

import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dailymenu.Model.Meal;
import com.example.dailymenu.Model.MealsFav;
import com.example.dailymenu.Model.MealsPlan;
import com.example.dailymenu.Model.User;
import com.example.dailymenu.Utils.UserSharedPrefrence;
import com.example.dailymenu.db.MealLocalDataSource;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Firebase {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private  String userId;

    public Firebase() {

    }
    private String getUserId() {
        return auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : null;
    }

    public void loginWithEmailAndPassword(String email , String pass , AuthResonse resonse , Context context)
    {
        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                   if (task.isSuccessful())
                   {
                       resonse.onLoginSuccess();
                       userId = getUserId();
                       UserSharedPrefrence.addUser(new User(auth.getCurrentUser().getEmail() , userId) ,context);
                   }
                   else{
                       resonse.onLoginFailure();
                   }
                });
    }
    public void logout()
    {
        FirebaseAuth.getInstance().signOut();
    }
    public void singnupWithEmailAndPassword(String email , String pass , AuthResonse resonse)
    {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful())
                    {
                        resonse.onLoginSuccess();
                    }
                    else{
                        resonse.onLoginFailure();
                    }
                });
    }
    public void loginWithGoogle(GoogleSignInAccount account, AuthResonse resonse , Context context)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            resonse.onLoginSuccess();
                            userId = getUserId();
                           UserSharedPrefrence.addUser(new User(auth.getCurrentUser().getEmail() , userId) , context);

                        }else {
                            resonse.onLoginFailure();
                        }

                    }
                });
    }
    public void backupMealToFirestore(MealsFav meal , Context context) {

        db.collection("users").document(UserSharedPrefrence.getUserId(context))
                .collection("fav").document(meal.getMealId())
                .set(meal, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "New Fav meal backed up"))
                .addOnFailureListener(e -> Log.e("Firestore", "Backup failed", e));
    }
    public void backupPlanToFirestore(MealsPlan meal , Context context) {

        db.collection("users").document(UserSharedPrefrence.getUserId(context))
                .collection("plan").document(meal.getMealId())
                .set(meal, SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "New Planned meal backed up"))
                .addOnFailureListener(e -> Log.e("Firestore", "Backup failed", e));
    }
    public void deletePlanFromFirestore(MealsPlan meal , Context context) {

        db.collection("users").document(UserSharedPrefrence.getUserId(context))
                .collection("plan").document(meal.getMealId())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal deleted successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Meal deletion failed", e));
    }
    public void deleteMealFromFirestore(MealsFav meal , Context context) {


        db.collection("users").document(UserSharedPrefrence.getUserId(context))
                .collection("fav").document(meal.getMealId())
                .delete()
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Meal deleted successfully"))
                .addOnFailureListener(e -> Log.e("Firestore", "Meal deletion failed", e));
    }

    public void restoreData(MealLocalDataSource ds) {
        List<Completable> favData = new ArrayList<>();
        List<Completable> planData = new ArrayList<>();
        db.collection("users").document(getUserId())
                .collection("fav").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        MealsFav meal = doc.toObject(MealsFav.class);
                        if (meal != null) {
                            favData.add(ds.add(meal).subscribeOn(Schedulers.io()));
                            Log.i("TAG", "restoreData: " + meal.getMealId());
                        };
                    }
                    Completable.merge(favData)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> Log.i("TAG", "All meals restored successfully"),
                                    err -> Log.e("TAG", "Error restoring meals", err)
                            );

                })
                .addOnFailureListener(e -> Log.e("Firestore", "Restore failed", e));

        db.collection("users").document(getUserId())
                .collection("plan").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        MealsPlan meal = doc.toObject(MealsPlan.class);
                        if (meal != null) {
                           planData.add( ds.addMealToPlan(meal).subscribeOn(Schedulers.io()));
                            Log.i("TAG", "restoreData: " + meal.getMealId());
                        }
                    }
                    Completable.merge(planData)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    () -> Log.i("TAG", "All meals restored successfully"),
                                    err -> Log.e("TAG", "Error restoring meals", err)
                            );
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Restore failed", e));
    }

}
