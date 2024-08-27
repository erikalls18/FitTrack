package com.example.fittrackapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fittrackapp.Model.BurnedCaloriesModel;
import com.example.fittrackapp.Model.User;
import com.example.fittrackapp.Services.FitnessService;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;

import java.security.AccessController;
import java.util.List;

public class BurnedCaloriesActivity extends MainActivity {

    private String api_key;
    private TextView jsontextView;
    private TextView activity_name, kcal, msg;

    private EditText activity, duration;

    private String activity_txt, duration_txt;

    private Toolbar mtoolbar;

    private DatabaseReference usersRef;

    FirebaseAuth mAuth ;
    FirebaseUser currentUser ;
    BurnedCaloriesModel lastCalories;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_burned_calories);

        activity= findViewById(R.id.activity);
        duration =findViewById(R.id.duration);
        kcal =findViewById(R.id.kcal);
        msg =findViewById(R.id.msg);

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        jsontextView = findViewById( R.id.calories_per_hour);
        activity_name =findViewById(R.id.activity_name);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("User");

        lastCalories = new BurnedCaloriesModel();

    }

    private void getCalories(){

        activity_txt= activity.getText().toString();
        duration_txt = duration.getText().toString();

        int durationInt=0;
        if (!duration_txt.isEmpty() && duration_txt.matches("\\d+")) {
            // Convertir duration_txt a un entero
            durationInt = Integer.parseInt(duration_txt);
        }


        api_key ="4MRkoOa72HZ8LqFI8mLB1g==6yi2y47uMa600hSE";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.api-ninjas.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        User user= new User();


        //Float weightFloat = user.getWeight();

        //int weight = Math.round(weightFloat);

        FitnessService fitnessService = retrofit.create(FitnessService.class);
        Call<List<BurnedCaloriesModel>> call = fitnessService.getCalories(activity_txt, 50, durationInt);

        call.enqueue(new Callback<List<BurnedCaloriesModel>>() {
            @Override
            public void onResponse(Call<List<BurnedCaloriesModel>> call, Response<List<BurnedCaloriesModel>> response) {
                if (response.isSuccessful()) {
                    List<BurnedCaloriesModel> burnedCalories = response.body();
                    //BurnedCaloriesModel lastCalories = new BurnedCaloriesModel();
                    if (burnedCalories != null && !burnedCalories.isEmpty()) {
                        for (BurnedCaloriesModel calories : burnedCalories) {
                            lastCalories = calories;

                        }

                        kcal.setText(lastCalories.getTotal_calories() + " Kcals");

                        SharedPreferences sharedPreferences = getSharedPreferences("CaloriesBurned", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        int total_kal= lastCalories.getTotal_calories();
                        editor.putInt("calories_b", total_kal);
                        editor.apply();

                    } else {
                        Log.d(TAG, "Response body is null");
                        msg.setText("No exercise found. Try again");

                    }
                } else {
                    Log.d(TAG, "Error in response: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<BurnedCaloriesModel>> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());

            }
        });

    }

    public void searchActivity(View view) {
        msg.setText("");
        getCalories();
    }

    public void saveData(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Would you like to save this item?");

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    String uid2 = currentUser.getUid();

                    // Obtener la referencia del usuario actual
                    usersRef.child(uid2).child("calories_burned").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Long currentCaloriesBurned = dataSnapshot.getValue(Long.class);


                            if (currentCaloriesBurned == null) currentCaloriesBurned = 0L;

                            double newCaloriesBurned = lastCalories.getTotal_calories(); // Ajusta según tu lógica
                            double updatedCaloriesBurned = currentCaloriesBurned + newCaloriesBurned;


                            usersRef.child(uid2).child("calories_burned").setValue(updatedCaloriesBurned);
                            Log.d("LoginActivity", "userID: " + uid2);


                            Snackbar snackbar = Snackbar.make(view, "Saving Data", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("LoginActivity", "loadCalories:onCancelled", databaseError.toException());
                        }
                    });
                } else {
                    Snackbar snackbar = Snackbar.make(view, "You need to be logged in to save data", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }





    public void addConsumed(View view) {

        Intent intent = new Intent(this, CaloriesConsumed.class);
        startActivity(intent);
    }


}