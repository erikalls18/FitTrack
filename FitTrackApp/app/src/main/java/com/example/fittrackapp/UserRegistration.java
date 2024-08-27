package com.example.fittrackapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fittrackapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends MainActivity {


    private EditText email, name,  height, weight, sex;
    private EditText password;
    private Button signup;

    private int age;

    private float altura, peso;

    private String ageStr,heightStr,weightStr, sexStr  ;

   FirebaseDatabase firebaseDatabase;
   //FirebaseReference reference;
    DatabaseReference reference;

    private FirebaseAuth auth;

    private Toolbar mtoolbar;

    private static final String TAG = "UserRegistration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        mtoolbar =findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        starFirebase();

        name =findViewById(R.id.logName);
        email =findViewById(R.id.email);
        password =findViewById(R.id.password);
        signup = findViewById(R.id.sing_up);
        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                 heightStr = bundle.getString("height");
                 ageStr = bundle.getString("age");
                 weightStr = bundle.getString("weight");
                 sexStr  = bundle.getString("sex");
            }

        }

        age = Integer.parseInt(ageStr);
        altura= Float.parseFloat(heightStr);
        peso= Float.parseFloat(weightStr);



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_txt= email.getText().toString();
                String pass_txt = password.getText().toString();
                String name_txt= name.getText().toString();

                if (name_txt.isEmpty() || TextUtils.isEmpty(email_txt) || TextUtils.isEmpty(pass_txt)) {
                    //Toast.makeText(UserRegistration.this,"Empty credentials", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(v, "All fields are required", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                else if (pass_txt.length() <6 ){
                    Toast.makeText(UserRegistration.this,"Password too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Registra al usuario
                    auth.createUserWithEmailAndPassword(email_txt, pass_txt)
                            .addOnCompleteListener(UserRegistration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Registro exitoso
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        if (firebaseUser != null) {
                                            String uid = firebaseUser.getUid();
                                            Log.d("UserRegistration", "User ID: " + uid);

                                            // Guarda los datos del usuario en la base de datos
                                            DatabaseReference usersRef = firebaseDatabase.getReference("User");
                                            User user = new User();
                                            user.setName(name_txt);
                                            user.setEmail(email_txt);
                                            user.setPassword(pass_txt);
                                            user.setHeight(altura);
                                            user.setWeight(peso);
                                            user.setAge(age);
                                            user.setSex(sexStr);
                                            user.setUid(uid);
                                            reference.child("User").child(uid).setValue(user);

                                            // Inicia sesión automáticamente
                                            auth.signInWithEmailAndPassword(email_txt, pass_txt)
                                                    .addOnCompleteListener(UserRegistration.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                // Inicio de sesión exitoso
                                                                startActivity(new Intent(UserRegistration.this, BurnedCaloriesActivity.class));
                                                                finish();
                                                            } else {
                                                                // Error en el inicio de sesión
                                                                Snackbar snackbar = Snackbar.make(v, "Login failed. Please try again.", Snackbar.LENGTH_LONG);
                                                                snackbar.show();
                                                            }
                                                        }
                                                    });
                                        }
                                    } else {
                                        // Error en el registro
                                        Snackbar snackbar = Snackbar.make(v, "Registration failed. Please try again.", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                            });
                }
            }
        });


    }



    private void starFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference();
    }

    private void registerUser(String email, String pass, View view ) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(UserRegistration.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d(TAG, "Fields validated successfully");


                } else {

                    Log.d(TAG, "Fail in validation fields");
                }
            }
        });

    }

}