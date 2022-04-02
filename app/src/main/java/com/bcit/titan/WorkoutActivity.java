package com.bcit.titan;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class WorkoutActivity extends AppCompatActivity {

    Spinner spinner;
    TextView timer;
    Button start, pause, reset;
    int countDown = 0;
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button submit;

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

    String up = "";
    String co = "";
    String lo = "";

    String bodyparts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        db = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.spinner_workout_dropdown);

//        Bundle inBundle = getIntent().getExtras();
//        String[] selectedWorkout = inBundle.getStringArray("WORKOUTKEY");
//
//        String user_email = selectedWorkout[4];
//        TextView toUpdate = findViewById(R.id.textView_workout_invisibileCategory);
//        DocumentReference docref = db.collection("users").document(user_email);
//        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                up = documentSnapshot.getString("Upper");
//                co = documentSnapshot.getString("Core");
//                lo = documentSnapshot.getString("Lower");
//            }
//        });
////        docref.addSnapshotListener(this, (documentSnapShot, error) -> {
////            up.concat(documentSnapShot.getString("Upper").toString());
////            co = documentSnapShot.getString("Core");
////            lo = documentSnapShot.getString("Lower");
////        });
//
//        System.out.println("Workout Activity upper is " + up.toString() + " core is " + co.toString() + " lower is " + lo);
//
//
//        if(selectedWorkout[3].equals("upper")){
//            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
//                toUpdate.setText(documentSnapShot.getString("Upper"));
//            });
//        }else if(selectedWorkout[3].equals("core")){
//            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
//                toUpdate.setText(documentSnapShot.getString("Core"));
//            });
//        }else if(selectedWorkout[3].equals("lower")){
//            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
//                toUpdate.setText(documentSnapShot.getString("Lower"));
//            });
//        }else{
//            System.out.println("Not possible.");
//        }
//
//


        setupSpinner();
//        refresh();

        submit = findViewById(R.id.button_workout_submit);
        Intent intent = new Intent(this, ProgressActivity.class);
        Bundle extras = new Bundle();
        EditText enteredReps = findViewById(R.id.editText_workout_reps);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReps();
            }
        });


    }
    public void submitReps(){
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        Intent intent = new Intent(this, ProgressActivity.class);
        Button submitButton = findViewById(R.id.button_workout_submit);
        EditText enteredReps = findViewById(R.id.editText_workout_reps);
        int enteredRepsCount = Integer.parseInt(enteredReps.getText().toString());
        DocumentReference docref = db.collection("exercises").document(auth.getCurrentUser().getUid());

        docref.update(bodyparts, FieldValue.increment(enteredRepsCount)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    System.out.println("SUCCESSSSS");
                }else {
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put(bodyparts, enteredRepsCount);
                    docref.set(dataMap);
                }
            }
        });
        enteredReps.setText("0");
        submitButton.setEnabled(false);
    }

    void timers(int startTimer) {

        timer = (TextView) findViewById(R.id.textView_workout_timer);
        start = (Button) findViewById(R.id.button_workout_start);
        EditText repsEditText = findViewById(R.id.editText_workout_reps);
        Button submitButton = findViewById(R.id.button_workout_submit);

        repsEditText.setEnabled(false);
        submitButton.setEnabled(false);


        timer.setText(startTimer / 1000 + "");

        start.setOnClickListener(new View.OnClickListener() {
            int one = 0;
            boolean toggle = false;

            @Override
            public void onClick(View view) {
                one++;
                CountDownTimer count = new CountDownTimer(startTimer, 1000) {


                    public void onTick(long millisUntilFinished) {
                        timer.setText("" + millisUntilFinished / 1000);
                    }

                    public void onFinish() {

                        timer.setText("done!");

                        repsEditText.setEnabled(true);
                        submitButton.setEnabled(true);
                    }
                };
                if (one == 1) {
                    count.start();
                }
            }
        });
    }

    void setupSpinner() {

        WorkoutData workoutData = new WorkoutData();
        ImageView imageView = findViewById(R.id.gif_workout);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.workout_list, android.R.layout.simple_spinner_dropdown_item);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        TextView defaultText = findViewById(R.id.texview_workout_defaultText);
        spinner.setSelection(0,false);
        EditText enteredReps = findViewById(R.id.editText_workout_reps);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//                String selectedWorkout = spinner.getSelectedItem().toString();
                Bundle extras = new Bundle();
                switch (spinner.getSelectedItem().toString()) {
                    //upper
                    case "Push Ups":
                    case "Dips":
                    case "Pull Ups":
                        enteredReps.setText("0");
                        workoutData.set_workout(spinner.getSelectedItem().toString());
                        timers(workoutData.getWorkoutTime());
                        imageView.setImageResource(workoutData.get_image());
                        System.out.println(spinner.getSelectedItem().toString());
                        defaultText.setVisibility(View.GONE);
                        updateComponents(spinner.getSelectedItem().toString());
                        bodyparts = "upper";
                        submitReps();
                        break;
                    //Lower
                    case "Squats":
                    case "Lunges":
                    case "Deadlift":
                        workoutData.set_workout(spinner.getSelectedItem().toString());
                        timers(workoutData.getWorkoutTime());
                        imageView.setImageResource(workoutData.get_image());
                        System.out.println(spinner.getSelectedItem().toString());
                        defaultText.setVisibility(View.GONE);
                        updateComponents(spinner.getSelectedItem().toString());
                        bodyparts = "lower";
                        submitReps();
                        break;


                    //Core
                    case "Plank":
                    case "Leg Raises":
                    case "Elbow to knee":
                        workoutData.set_workout(spinner.getSelectedItem().toString());
                        timers(workoutData.getWorkoutTime());
                        imageView.setImageResource(workoutData.get_image());
                        System.out.println(spinner.getSelectedItem().toString());
                        defaultText.setVisibility(View.GONE);
                        updateComponents(spinner.getSelectedItem().toString());
                        bodyparts = "core";
                        submitReps();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateComponents(String workoutTitle){
        TextView title = findViewById(R.id.textView_workout_title);
        GifImageView gifImageView = findViewById(R.id.gif_workout);
        Button startButton = findViewById(R.id.button_workout_start);
        TextView workoutTimer = findViewById(R.id.textView_workout_timer);
        EditText repsEditText = findViewById(R.id.editText_workout_reps);
        submit = findViewById(R.id.button_workout_submit);

        title.setAlpha(1);
        gifImageView.setAlpha(1.0f);
        startButton.setAlpha(1);
        workoutTimer.setAlpha(1);
        repsEditText.setAlpha(1);
        submit.setAlpha(1);
        title.setText(workoutTitle);
    }


}