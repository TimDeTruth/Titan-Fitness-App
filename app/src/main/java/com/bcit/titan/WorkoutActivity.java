package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {

    TextView timer;
    Button start, pause, reset;
    int countDown = 0;


    public final String DIPSID = Integer.toString(R.drawable.exercise_upper_dips);
    public final String PUSHUPSID = Integer.toString(R.drawable.exercise_upper_push_ups);
    public final String PULLUPSID = Integer.toString(R.drawable.exercise_upper_pull_ups);

    public final String SQUATID = Integer.toString(R.drawable.exercise_lower_squats);
    public final String LUNGEID = Integer.toString(R.drawable.exercise_lower_lunge);
    public final String DEADLIFTID = Integer.toString(R.drawable.exercise_lower_deadlift);

    public final String PLANKID = Integer.toString(R.drawable.exercise_core_plank);
    public final String ELBOWTOKNEEID = Integer.toString(R.drawable.exercise_core_elbow_to_knee);
    public final String LEGRAISESID = Integer.toString(R.drawable.exercise_core_leg_raises);

    public String category = null;
    int upperReps = 0;
    int coreReps = 0;
    int lowerReps = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

//        boolean hasStarted = false;

        setupSpinner();
        refresh();

        Intent intent = new Intent(this, ProgressActivity.class);
        Bundle extras = new Bundle();
        Button button = findViewById(R.id.button_workout_submit);
        EditText enteredReps = findViewById(R.id.editText_workout_reps);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReps();
            }
        });

/*        Intent intent = getIntent();
        Bundle workoutExtras = intent.getExtras();


        int selectedExerciseId = workoutExtras.getInt("workoutResource");
        System.out.println("The resource ID: " + workoutExtras.getInt("workoutResource"));

        String selectedExerciseName = workoutExtras.getString("workoutName");

        TextView textView = findViewById(R.id.textView_workout_title);
        textView.setText(selectedExerciseName);

        ImageView imageView = findViewById(R.id.gif_workout);
        imageView.setImageResource(selectedExerciseId);


        String selectedExerciseTime = workoutExtras.getString("workoutTime");

        Bundle inBundle = getIntent().getExtras();

        String[] selectedWorkoutKey = inBundle.getStringArray("WORKOUTKEY");

        textView.setText(selectedWorkoutKey[0]);
        imageView.setImageResource(Integer.parseInt(selectedWorkoutKey[1]));
//        inBundle.containsKey("SQUATKEY");

        timer = (TextView) findViewById(R.id.textView_exercise_timer);
        start = (Button) findViewById(R.id.button_exercise_start);

        timer.setText(Integer.parseInt(selectedWorkoutKey[2]) / 1000 + "");

        start.setOnClickListener(new View.OnClickListener() {
            int one = 0;

            @Override
            public void onClick(View view) {
                one++;
                CountDownTimer count = new CountDownTimer(Integer.parseInt(selectedWorkoutKey[2]), 1000) {


                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }
                };
                if (one == 1) {
                    count.start();
                }

            }
        });*/

/*        Spinner spinner = (Spinner) findViewById(R.id.spinner_exercise_list);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedWorkout = spinner.getSelectedItem().toString();

                switch (spinner.getSelectedItem().toString()) {
                    case "Push ups":
                        imageView.setImageResource(R.drawable.excercises_upper_push_ups);
                        textView.setText(selectedWorkout);
                        countDown = 60;
                        break;
                    case "Squats":

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            int one = 0;

            @Override
            public void onClick(View view) {
                one++;
                CountDownTimer count = new CountDownTimer(countDown, 1000) {

                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }
                };
                if (one == 1){
                    count.start();
                }

            }
        });*/

    }

    public void submitReps(){
        Intent intent = new Intent(this, ProgressActivity.class);
        Bundle extras = new Bundle();
        EditText enteredReps = findViewById(R.id.editText_workout_reps);

        if(enteredReps != null){
            if(category.equals("upper")){
                upperReps += Integer.parseInt(enteredReps.getText().toString());
            }else if(category.equals("core")){
                coreReps += (Integer.parseInt(enteredReps.getText().toString()));
            }else if(category.equals("lower")){
                lowerReps += (Integer.parseInt(enteredReps.getText().toString()));
            }else{
                System.out.println("Doesn't Exist");
            }
        }

        Bundle pieExtras = new Bundle();
        String[] pieData = {category, enteredReps.getText().toString()};
        pieExtras.putStringArray("PIEKEY", pieData);
        intent.putExtras(extras);

        System.out.println(pieData[0] + " " + pieData[1]);

        startActivity(intent);

    }

    void refresh() {
        setupSpinner();
        Spinner spinner = (Spinner) findViewById(R.id.spinner_workout_dropdown);

        Intent intent = getIntent();
        Bundle workoutExtras = intent.getExtras();


        TextView textView = findViewById(R.id.textView_workout_title);
        ImageView imageView = findViewById(R.id.gif_workout);

        int selectedExerciseId = workoutExtras.getInt("workoutResource");
        System.out.println("The resource ID: " + workoutExtras.getInt("workoutResource"));
        String selectedExerciseName = workoutExtras.getString("workoutName");
        textView.setText(selectedExerciseName);
        imageView.setImageResource(selectedExerciseId);
        String selectedExerciseTime = workoutExtras.getString("workoutTime");
        Bundle inBundle = getIntent().getExtras();
        String[] selectedWorkoutKey = inBundle.getStringArray("WORKOUTKEY");
        textView.setText(selectedWorkoutKey[0]);
        imageView.setImageResource(Integer.parseInt(selectedWorkoutKey[1]));
        int startTimer = Integer.parseInt(selectedWorkoutKey[2]);
        timers(startTimer);

        category = selectedWorkoutKey[3];

        Intent intents = new Intent(this, this.getClass());
        Spinner spinners = (Spinner) findViewById(R.id.spinner_workout_dropdown);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                String selectedWorkout = spinner.getSelectedItem().toString();
                Bundle extras = new Bundle();
                switch (spinners.getSelectedItem().toString()) {
                    //upper
                    case "Push Ups":
                        String[] pushUpsArr = {"Push Ups", PUSHUPSID, 60000 + "", "upper"};
                        extras.putStringArray("WORKOUTKEY", pushUpsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Dips":
                        String[] dipsArr = {"Dips", DIPSID, 45000 + "", "upper"};
                        extras.putStringArray("WORKOUTKEY", dipsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Pull Ups":
                        String[] pullUpsArr = {"Pull Ups", PULLUPSID, 50000 + "", "upper"};
                        extras.putStringArray("WORKOUTKEY", pullUpsArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;

                    //Lower
                    case "Squats":
                        String[] squatArray = {"Squats", SQUATID, 60000 + "", "lower"};
                        extras.putStringArray("WORKOUTKEY", squatArray);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Lunges":
                        String[] lungesArr = {"Lunges", LUNGEID, 60000 + "", "lower"};
                        extras.putStringArray("WORKOUTKEY", lungesArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Deadlift":
                        String[] deadliftArr = {"Dead", DEADLIFTID, 45000 + "", "lower"};
                        extras.putStringArray("WORKOUTKEY", deadliftArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;


                    //Core
                    case "Plank":
                        String[] plankArr = {"Plank", PLANKID, 120000 + "", "core"};
                        extras.putStringArray("WORKOUTKEY", plankArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Leg Raises":
                        String[] legRaisesArr = {"Lunges", LEGRAISESID, 50000 + "", "core"};
                        extras.putStringArray("WORKOUTKEY", legRaisesArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;
                    case "Elbow to knee":
                        String[] elbowToKneeArr = {"Bulgarian Split Squat", ELBOWTOKNEEID, 45000 + "", "core"};
                        extras.putStringArray("WORKOUTKEY", elbowToKneeArr);
                        intent.putExtras(extras);
                        startActivity(intent);
                        break;



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    void timers(int startTimer) {


        timer = (TextView) findViewById(R.id.textView_workout_timer);
        start = (Button) findViewById(R.id.button_workout_start);

        timer.setText(startTimer / 1000 + "");

        start.setOnClickListener(new View.OnClickListener() {
            int one = 0;

            @Override
            public void onClick(View view) {
                one++;
                CountDownTimer count = new CountDownTimer(startTimer, 1000) {


                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {
                        timer.setText("done!");
                    }
                };
                if (one == 1) {
                    count.start();
                }

            }
        });
    }

    void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_workout_dropdown);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.workout_list, android.R.layout.simple_spinner_dropdown_item);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);

    }



}