package com.example.fittrackapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
            //return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if(item.getItemId() == R.id.home) {


                Intent intent = new Intent(this, BurnedCaloriesActivity.class);
                startActivity(intent);
            }

            else if (item.getItemId() == R.id.login){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

            }
            else if (item.getItemId() == R.id.signup2){
                Intent intent = new Intent(this, ProfileDetails.class);
                startActivity(intent);

            }

            else if (item.getItemId() == R.id.calories){
                Intent intent = new Intent(this, CaloriesConsumed.class);
                startActivity(intent);

            }
            else if (item.getItemId() == R.id.dashboard){
                Intent intent = new Intent(this, UserInfo.class);
                startActivity(intent);

            }

            else {
                return super.onOptionsItemSelected(item);
            }
            return true;


        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity( new Intent(MainActivity.this, RegisterActivity.class));
                startActivity( new Intent(MainActivity.this, ProfileDetails.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserInfo.class));
                finish();
            }
        });*/

    }
}