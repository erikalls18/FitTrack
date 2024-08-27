package com.example.fittrackapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CaloriesConsumed extends MainActivity{

    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_consumed);

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

    }

    public void selectItem(View view) {
        Intent intent = new Intent(this, DisplayFoodItems.class);
        startActivity(intent);
    }
}