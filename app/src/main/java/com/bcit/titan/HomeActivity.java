package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Button exerciseButton = findViewById(R.id.button_home_exercise);
        Button progressButton = findViewById(R.id.button_home_progress);
        Button settingsButton = findViewById(R.id.button_home_settings);


        Intent intentExercise = new Intent(this, ExerciseActivity.class);
        Intent intentProgress = new Intent(this, ProgressActivity.class);



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
        
    }
}