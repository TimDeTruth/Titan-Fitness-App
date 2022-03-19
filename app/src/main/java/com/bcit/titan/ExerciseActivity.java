package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);


//        ExerciseDetails exercise1 = new ExerciseDetails("Push ups", R.drawable.exercises_push_up, 0);
//        ExerciseDetails exercise2 = new ExerciseDetails("Squats", R.drawable.exercises_squats, 1);

        setupSpinner();

        Intent intent = new Intent(this, WorkoutActivity.class);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_exercise_list);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedWorkout = spinner.getSelectedItem().toString();

                switch (spinner.getSelectedItem().toString()) {
                    case "Push ups":
                        Bundle bundle = new Bundle();

//                        intent.putExtras(bundle);
                        intent.putExtra("workoutResource", R.drawable.exercises_push_up);
                        intent.putExtra("workoutName", selectedWorkout);
                        intent.putExtra("workoutTime", 30000);

                        startActivity(intent);
                        break;
                    case "Squats":
                        String squatID = R.drawable.exercises_squats + "";
                        String[] squatArray = {"Squats", squatID, 60000 + ""};
                        Bundle extras = new Bundle();
                        extras.putStringArray("SQUATKEY", squatArray);
                        intent.putExtras(extras);
//                        intent.putExtra("workoutResource", R.drawable.exercises_squats);
//                        intent.putExtra("workoutName", selectedWorkout);
//                        intent.putExtra("workoutTime", 60000);
                        startActivity(intent);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


//    public ExerciseDetails[] generateWorkouts() {
//
//        ExerciseDetails[] exerciseDetails = new ExerciseDetails[100];
//
//
//        return exerciseDetails;
//    }

    void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_exercise_list);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.workout_list, android.R.layout.simple_spinner_dropdown_item);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);

    }
}

