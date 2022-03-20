package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WorkoutActivity extends AppCompatActivity {

    TextView timer;
    Button start, pause, reset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

//        boolean hasStarted = false;

        setupSpinner();
        Intent intent = getIntent();
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
        String[] squats = inBundle.getStringArray("SQUATKEY");
        textView.setText(squats[0]);
        imageView.setImageResource(Integer.parseInt(squats[1]));
//        inBundle.containsKey("SQUATKEY");

        timer = (TextView) findViewById(R.id.textView_exercise_timer);
        start = (Button) findViewById(R.id.button_exercise_start);

        timer.setText(Integer.parseInt(squats[2])/1000 + "");

        start.setOnClickListener(new View.OnClickListener() {
            int one = 0;

            @Override
            public void onClick(View view) {
                one++;
                CountDownTimer count = new CountDownTimer(Integer.parseInt(squats[2]), 1000) {



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
        });
    }

    void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_exercise_list);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.workout_list, android.R.layout.simple_spinner_dropdown_item);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);

    }

}