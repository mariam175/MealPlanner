package com.example.dailymenu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dailymenu.Model.DTO.User;

public class UserSharedPrefrence {
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;
    public static void addUser(User user , Context context)
    {
        sharedPreferences = context.getSharedPreferences("User" , Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("userEmail" , user.getEmail());
        editor.putString("userId" , user.getUserId());
        editor.commit();
    }
    public static String getUserId(Context context)
    {
        sharedPreferences = context.getSharedPreferences("User" , Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId" , "none");
        return userId;
    }
    public static String getUserEmail(Context context)
    {
        sharedPreferences = context.getSharedPreferences("User" , Context.MODE_PRIVATE);
        String userEmail = sharedPreferences.getString("userEmail" , "none");
        return userEmail;
    }
}
