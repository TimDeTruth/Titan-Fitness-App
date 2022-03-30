package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ExerciseActivity extends AppCompatActivity {

    public final String DIPSID = Integer.toString(R.drawable.exercise_upper_dips);
    public final String PUSHUPSID = Integer.toString(R.drawable.exercise_upper_push_ups);
    public final String PULLUPSID = Integer.toString(R.drawable.exercise_upper_pull_ups);

    public final String SQUATID = Integer.toString(R.drawable.exercise_lower_squats);
    public final String LUNGEID = Integer.toString(R.drawable.exercise_lower_lunge);
    public final String DEADLIFTID = Integer.toString(R.drawable.exercise_lower_deadlift);

    public final String PLANKID = Integer.toString(R.drawable.exercise_core_plank);
    public final String ELBOWTOKNEEID = Integer.toString(R.drawable.exercise_core_elbow_to_knee);
    public final String LEGRAISESID = Integer.toString(R.drawable.exercise_core_leg_raises);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        String user_email = getIntent().getExtras().getString("user_email");


//        ExerciseDetails exercise1 = new ExerciseDetails("Push ups", R.drawable.exercises_push_up, 0);
//        ExerciseDetails exercise2 = new ExerciseDetails("Squats", R.drawable.exercises_squats, 1);

        setupSpinner();

        Intent intent = new Intent(this, WorkoutActivity.class);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_exercise_list);
        TextView workoutTypeDescription = findViewById(R.id.textView_exercise_description);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedWorkout = spinner.getSelectedItem().toString();
                Bundle extras = new Bundle();
                switch (spinner.getSelectedItem().toString()) {


                    //upper
                    case "Push Ups":
                        String[] pushUpsArr = {"Squats", PUSHUPSID, 30000 + "", "upper", user_email};
                        extras.putStringArray("WORKOUTKEY", pushUpsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Dips":
                        String[] dipsArr = {"Dips", DIPSID, 45000 + "", "upper", user_email};
                        extras.putStringArray("WORKOUTKEY", dipsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Pull Ups":
                        String[] pullUpsArr = {"Pull Ups", PULLUPSID, 50000 + "", "upper", user_email};
                        extras.putStringArray("WORKOUTKEY", pullUpsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;

                    //Lower
                    case "Squats":
                        String[] squatArray = {"Squats", SQUATID, 60000 + "", "lower", user_email};
                        extras.putStringArray("WORKOUTKEY", squatArray);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Lunges":
                        String[] lungesArr = {"Lunges", LUNGEID, 60000 + "", "lower", user_email};
                        extras.putStringArray("WORKOUTKEY", lungesArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Deadlift":
                        String[] deadliftArr = {"Dead", DEADLIFTID, 45000 + "", "lower", user_email};
                        extras.putStringArray("WORKOUTKEY", deadliftArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;


                    //Core
                    case "Plank":
                        String[] plankArr = {"Plank", PLANKID, 120000 + "", "core", user_email};
                        extras.putStringArray("WORKOUTKEY", plankArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Leg Raises":
                        String[] legRaisesArr = {"Lunges", LEGRAISESID, 50000 + "", "core", user_email};
                        extras.putStringArray("WORKOUTKEY", legRaisesArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Elbow to knee":
                        String[] elbowToKneeArr = {"Bulgarian Split Squat", ELBOWTOKNEEID, 45000 + "", "core", user_email};
                        extras.putStringArray("WORKOUTKEY", elbowToKneeArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;



                    case "Upper Body":
                        workoutTypeDescription.setText(R.string.upper_description);
                        break;
                    case "Lower Body":
                        workoutTypeDescription.setText(R.string.lower_description);
                        break;
                    case "Core":
                        workoutTypeDescription.setText(R.string.core_description);
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

