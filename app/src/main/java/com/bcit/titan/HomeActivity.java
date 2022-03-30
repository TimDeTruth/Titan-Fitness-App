package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        System.out.println("INSIDE HOME ON CREATE");

        Button exerciseButton = findViewById(R.id.button_home_exercise);
        Button progressButton = findViewById(R.id.button_home_progress);
        Button settingsButton = findViewById(R.id.button_home_settings);

        TextView name = findViewById(R.id.textView_home_name);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Intent intentExercise = new Intent(this, ExerciseActivity.class);
        Intent intentProgress = new Intent(this, ProgressActivity.class);
        Intent intentSettings = new Intent(this, SettingsActivity.class);

//        String user_email = getIntent().getExtras().getString("user_email");

//        DocumentReference docref = db.collection("users").document(user_email);
//        docref.addSnapshotListener(this, (documentSnapshot, error) -> {
//            name.setText(documentSnapshot.getString("firstname"));
//        });



        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intentExercise.putExtra("user_email", user_email);
//                startActivity(intentExercise);
            }
        });

        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentProgress);
            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intentSettings);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        Intent intentLogin = new Intent(this, MainActivity.class);
        if(currentUser == null){
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLogin);
            finish();
        }
    }
}