package com.example.fittrackapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fittrackapp.Model.BurnedCaloriesModel;
import com.example.fittrackapp.Model.FoodItem;
import com.example.fittrackapp.Model.User;
import com.example.fittrackapp.Services.FitnessService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayFoodItems extends MainActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    private String api_key;

    private ArrayList<FoodItem> foodList;

    private EditText search;

    private static final String TAG = "DisplayFoodItems";

    private Toolbar mtoolbar;

    double fat_total =0;
    double cholesterol =0;
    double carbs =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_food_items);

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        search = findViewById(R.id.search_food);
        foodList = new ArrayList<>();

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);



    }

    private void getFoodCalories(String search_txt) {

        api_key = "";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        User user = new User();
        Log.d(TAG, "Holis!");
        //String search_txt= search.getText().toString();

        FitnessService fitnessService = retrofit.create(FitnessService.class);
        Call<List<FoodItem>> call = fitnessService.getFoodCalories(search_txt);
        Log.d(TAG, "Holis pero de nuevo!!");
        call.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                Log.d(TAG, "Antes del IF");
                if (response.isSuccessful()) {
                    List<FoodItem> consumedCalories = response.body();
                    Log.d(TAG, "este es el body: " + response.body());
                    if (consumedCalories != null && !consumedCalories.isEmpty()) {
                        for (FoodItem item : consumedCalories) {
                            foodList.add(item);

                        }
                        adapter = new RecyclerAdapter(DisplayFoodItems.this, foodList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        for (FoodItem item : foodList) {
                            double fat_total = item.getFatTotalG();
                            int cholesterol= item.getCholesterolMg();
                            double carbs= item.getCarbohydratesTotalG();

                        }



                    } else {
                        Log.d(TAG, "esta vacio: " + response.code());

                    }
                } else {
                    Log.d(TAG, "Error in response: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());

            }
        });

    }
    public void searchItem(View view) {

        String item= search.getText().toString();
        foodList.clear();
        getFoodCalories(item);
        //foodList.clear();


    }
}
