package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button exerciseButton = findViewById(R.id.button_home_exercise);
        Button progressButton = findViewById(R.id.button_home_progress);
        Button settingsButton = findViewById(R.id.button_home_settings);

        name = findViewById(R.id.textView_home_name);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        name.setAlpha(0);

        FirebaseUser currentUser = auth.getCurrentUser();
        Intent intentLogin = new Intent(this, MainActivity.class);
        if(currentUser == null){
            intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentLogin);
            finish();
        }

        getUsername();
        Intent intentExercise = new Intent(this, WorkoutActivity.class);
        Intent intentProgress = new Intent(this, ProgressActivity.class);
        Intent intentSettings = new Intent(this, SettingsActivity.class);



        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentExercise);
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

    private void getUsername() {

        DocumentReference docRef = db.collection("users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String usernameString = document.get("username").toString();
                        name.setText("Hello " + usernameString);
                        name.setAlpha(1);

                    } else {
                        System.out.println(document.get("NO SUCH DOCUMENT"));
                    }
                } else {
                    System.out.println(task.getException().toString());

                }
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }
}