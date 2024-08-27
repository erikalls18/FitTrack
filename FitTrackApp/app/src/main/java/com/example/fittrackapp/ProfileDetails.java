package com.example.fittrackapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class ProfileDetails extends MainActivity {

    private Toolbar mtoolbar;
    private EditText height, weight, sex, age;
    String height_txt, weight_txt, age_txt, sex_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);

        height =findViewById(R.id.height);
        sex=findViewById(R.id.sex);
        weight=findViewById(R.id.weight);
        age=findViewById(R.id.age);
        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

    }

    public void next(View view) {
        Intent intent = new Intent(this, UserRegistration.class);
        Bundle bundle = new Bundle();

        height_txt= height.getText().toString();
        weight_txt= weight.getText().toString();
        age_txt= age.getText().toString();
        sex_txt= sex.getText().toString();

       if (height_txt.equals("")){
           height.setError("Required");
       }
       else{
           bundle.putString("height", height_txt);
       }

        if (weight_txt.equals("")) {
            weight.setError("Required");
        } else {
            bundle.putString("weight", weight_txt);
        }

        if (age_txt.equals("")) {
            age.setError("Required");
        } else {
            bundle.putString("age", age_txt);
        }

        if (sex_txt.equals("")) {
            sex.setError("Required");
        } else {
            bundle.putString("sex", sex_txt);
        }
        if (!height_txt.equals("") && !weight_txt.equals("") && !age_txt.equals("") && !sex_txt.equals("")) {
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
        else{
            Snackbar snackbar = Snackbar.make(view, "All fields are required", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    private void validateInputs() {
        if (height.equals("")){
            height.setError("Required");
        }

    }
}