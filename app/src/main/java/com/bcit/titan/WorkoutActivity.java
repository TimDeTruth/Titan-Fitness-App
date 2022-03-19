package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WorkoutActivity extends AppCompatActivity {

    TextView timer;
    Button start, pause, reset;
//    long MillisecondTime, TimeBuff, UpdateTime = 0L;        // for actual numbers
//    long StartTime = 0L;
//    Handler handler;
//    int Seconds, Minutes, MilliSeconds; // display only



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

//        boolean hasStarted = false;

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




        // timer

//        pause = (Button)findViewById(R.id.btPause);
//        reset = (Button)findViewById(R.id.btReset);

//        handler = new Handler();

//        StartTime = SystemClock.uptimeMillis();
//        handler.postDelayed(runnable, 0);
//
//        reset.setEnabled(false);

//        start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                StartTime = SystemClock.uptimeMillis();
////                MillisecondTime = 0L;
////                StartTime = 0L;
////                TimeBuff = 0L;
////                UpdateTime = 0L;
////                Seconds = 0;
////                Minutes = 0;
////                MilliSeconds = 0;
//                handler.postDelayed(runnable, 0);
//
//
////                reset.setEnabled(false);
//
//            }
//        });

//        pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TimeBuff += MillisecondTime;
//
//                handler.removeCallbacks(runnable);
//
//                reset.setEnabled(true);
//
//            }
//        });

//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                MillisecondTime = 0L ;
//                StartTime = 0L ;
//                TimeBuff = 0L ;
//                UpdateTime = 0L ;
//                Seconds = 0 ;
//                Minutes = 0 ;
//                MilliSeconds = 0 ;
//
//                timer.setText("00:00:00");
//
//            }
//        });
    }

//    public Runnable runnable = new Runnable() {
//
//        public void run() {
//
//            MillisecondTime = SystemClock.uptimeMillis() - StartTime + 30000;   // 30 000 means in 30 seconds
//
//            UpdateTime = TimeBuff + MillisecondTime;
//
//            Seconds = (int) (UpdateTime / 1000);
//
//            Minutes = Seconds / 60;
//
//            Seconds = Seconds % 60;
//
//            MilliSeconds =  (int) (UpdateTime % 1000);
//
////            timer.setText("" + Minutes + ":"
////                    + String.format("%02d", Seconds) + ":"
////                    + String.format("%03d", MilliSeconds));
//            timer.setText("" + Minutes + ":"
//                    + String.format("%02d", Seconds) + ":"
//                    + String.format("%03d", MilliSeconds));
//
//            handler.postDelayed(this, 0);
//        }
//
//    };


}