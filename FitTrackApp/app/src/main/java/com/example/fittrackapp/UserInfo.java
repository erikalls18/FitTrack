package com.example.fittrackapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.fittrackapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends MainActivity {

    private Toolbar mtoolbar;

    private TextView totalKal, total_carbs, total_fat, total_cholesterol, calories_burned, calories_consume;

    FirebaseAuth mAuth ;

    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

       totalKal = findViewById(R.id.quantity2);

        total_carbs = findViewById(R.id.carbs);
        total_cholesterol = findViewById(R.id.cholesterol);
        total_fat = findViewById(R.id.fat);
        calories_consume = findViewById(R.id.quantity);
        calories_burned  = findViewById(R.id.quantity2);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid2 = currentUser.getUid();
            Log.d("LoginActivity", "userID: " + uid2);


            usersRef.child(uid2).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User userData = dataSnapshot.getValue(User.class);
                    if (userData != null) {
                        Log.d("LoginActivity", "Java: " + userData.getCarbs());

                        long calories = userData.getCalories_consumed();
                        double calories_brn= userData.getCalories_burned();
                        double carbs = userData.getCarbs();
                        double cholesterol = userData.getCholesterol() ;
                        double fat = userData.getFat();
                        double cal= carbs * fat;
                        String formattedCarbs= String.format("%.2f", carbs);
                        String formattedFat = String.format("%.2f", fat);

                        totalKal.setText(String.valueOf(calories) );
                        total_fat.setText((formattedFat));
                        total_cholesterol.setText(String.valueOf(cholesterol));
                        total_carbs.setText(formattedCarbs);
                        calories_consume.setText(String.valueOf(calories));
                        calories_burned.setText(String.valueOf(calories_brn));

                    } else {
                        Log.d("LoginActivity", "User data not found for user ID: " + uid2);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w("LoginActivity", "loadUser:onCancelled", databaseError.toException());
                }
            });
        } else {
            Log.d("LoginActivity", "No current user authenticated.");
        }











    }


}