package com.example.fittrackapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fittrackapp.Model.FoodItem;
import com.example.fittrackapp.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<FoodItem> foodList;

    private Context context;

    User user;
    private DatabaseReference usersRef;

    FirebaseAuth mAuth ;
    FirebaseUser currentUser ;

    private DataSnapshot dataSnapshot;
    double carbs;

    double cholesterol;

    double fat;

    public RecyclerAdapter(Context context, ArrayList<FoodItem> foodList){
        this.context=context;
        this.foodList = foodList;

       //FirebaseApp firebaseApp;
       //firebaseApp= FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("User");
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_food_item,
                viewGroup, false);

        ViewHolder  viewHolder =new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {

        FoodItem foodItem = foodList.get(position);
        carbs= foodItem.getCarbohydratesTotalG();
        cholesterol = foodItem.getCholesterolMg();
        fat = foodItem.getFatSaturatedG();
        holder.itemName.setText(String.valueOf(foodItem.getName()));
        holder.itemCal.setText(String.valueOf(foodItem.getCholesterolMg()));
        holder.itemQ.setText(String.valueOf(foodItem.getFatSaturatedG()));
        holder.itemImage.setImageResource(R.drawable.baseline_add_circle_24);

    }

    @Override
    public int getItemCount() {

        return foodList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemQ, itemCal;
        ImageView itemImage;

        private FirebaseAuth auth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.name);
            itemQ = itemView.findViewById(R.id.grams);
            itemCal = itemView.findViewById(R.id.cal);
            itemImage = itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Confirmation");
                    builder.setMessage("Would you like to save this item?");

                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            saveData();

                            Snackbar snackbar = Snackbar.make(view, "Saving Data", Snackbar.LENGTH_LONG);
                            snackbar.show();

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
            });

        }
        private void saveData() {
            user = new User();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                String uid2 = currentUser.getUid();
                Log.d("LoginActivity", "userID: " + uid2);


                usersRef.child(uid2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User userData = dataSnapshot.getValue(User.class);
                        if (userData != null) {
                            Log.d("LoginActivity", "OdioJava: " + userData.getCarbs());

                            double cal= carbs * fat;
                            double updatedCalories = userData.getCalories_consumed() + cal;
                            double updatedCarbs = userData.getCarbs() + carbs;
                            double updatedCholesterol = userData.getCholesterol() + cholesterol;
                            double updatedFat = userData.getFat() + fat;
                            //String formattedCarbs = String.format("%.2f", updatedCarbs);

                            usersRef.child(uid2).child("calories_consumed").setValue(updatedCalories);
                            usersRef.child(uid2).child("carbs").setValue(updatedCarbs);
                            usersRef.child(uid2).child("cholesterol").setValue(updatedCholesterol);
                            usersRef.child(uid2).child("fat").setValue(updatedFat);



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
}
