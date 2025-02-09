package com.example.dailymenu;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatigoryServices {
    @GET("categories.php")
    Call<CatigoryResponse> getAllCatigories();
}
