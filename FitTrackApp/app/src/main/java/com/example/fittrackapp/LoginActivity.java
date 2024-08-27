package com.example.fittrackapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends MainActivity {

    private EditText email;
    private EditText password;
    private Button login, register ;

    private FirebaseAuth auth;

    private FirebaseAuth mAuth;

    private Toolbar mtoolbar;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);


        email =findViewById(R.id.log_email);
        password =findViewById(R.id.log_pass);
        login = findViewById(R.id.login);

        auth =FirebaseAuth.getInstance();
        register = findViewById(R.id.singUp);





        /*login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_txt= email.getText().toString();
                String pass_txt = password.getText().toString();
                //loginUser(email_txt, pass_txt);

                if (TextUtils.isEmpty(email_txt)){
                    Toast.makeText(LoginActivity.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass_txt.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    loginUser(email_txt, pass_txt, view);
                }

            }
        });*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity( new Intent(MainActivity.this, RegisterActivity.class));
                startActivity( new Intent(LoginActivity.this, ProfileDetails.class));
                finish();
            }
        });
    }




    private void loginUser(String email, String pass, View view) {

        //FirebaseAuth auth = FirebaseAuth.getInstance();
       // FirebaseUser currentUser = auth.getCurrentUser();



        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = auth.getCurrentUser();
                            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //editor.putString("UID", user.getkey());
                            editor.apply();

                            Log.d("User", user.getUid());

                            startActivity(new Intent(LoginActivity.this, BurnedCaloriesActivity.class));

                            finish();
                        } else {

                            Snackbar snackbar = Snackbar.make(view, "Email or Password are incorrect . Please try again", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                });






    }

    public void singIn(View view) {

        String email_txt= email.getText().toString();
        String pass_txt = password.getText().toString();
        auth.signOut();
        //loginUser(email_txt, pass_txt);

        if (TextUtils.isEmpty(email_txt)){
            Toast.makeText(LoginActivity.this,"Empty credentials", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass_txt.isEmpty()){
            Toast.makeText(LoginActivity.this,"Enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            loginUser(email_txt, pass_txt, view);
        }

        //String email = "lux@gmail.com";
        //String password = "12345678";

        //loginUser(email, password, view);

    }



}