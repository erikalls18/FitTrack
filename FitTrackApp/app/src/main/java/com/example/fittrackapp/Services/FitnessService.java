package com.example.fittrackapp.Services;

import com.example.fittrackapp.Model.BurnedCaloriesModel;
import com.example.fittrackapp.Model.FoodItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface FitnessService {

   @Headers("X-Api-Key:" + "4MRkoOa72HZ8LqFI8mLB1g==6yi2y47uMa600hSE")
   @GET("v1/caloriesburned?")
   Call<List<BurnedCaloriesModel>> getCalories(
           @Query("activity") String activity,
           @Query("weight") int weight,
           @Query("duration") int duration
    );

    @Headers("X-Api-Key:" + "4MRkoOa72HZ8LqFI8mLB1g==6yi2y47uMa600hSE")
    @GET("v1/nutrition?")
    Call<List<FoodItem>> getFoodCalories(
            @Query("query") String item

    );

}
