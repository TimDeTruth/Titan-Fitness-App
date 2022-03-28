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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class WorkoutActivity extends AppCompatActivity {

    TextView timer;
    Button start, pause, reset;
    int countDown = 0;
    FirebaseFirestore db;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        db = FirebaseFirestore.getInstance();
        Bundle inBundle = getIntent().getExtras();
        String[] selectedWorkout = inBundle.getStringArray("WORKOUTKEY");

        String user_email = selectedWorkout[4];
        TextView toUpdate = findViewById(R.id.textView_workout_invisibileCategory);
        DocumentReference docref = db.collection("users").document(user_email);
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                up = documentSnapshot.getString("Upper");
                co = documentSnapshot.getString("Core");
                lo = documentSnapshot.getString("Lower");
            }
        });
//        docref.addSnapshotListener(this, (documentSnapShot, error) -> {
//            up.concat(documentSnapShot.getString("Upper").toString());
//            co = documentSnapShot.getString("Core");
//            lo = documentSnapShot.getString("Lower");
//        });

        System.out.println("Workout Activity upper is " + up.toString() + " core is " + co.toString() + " lower is " + lo);


        if(selectedWorkout[3].equals("upper")){
            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                toUpdate.setText(documentSnapShot.getString("Upper"));
            });
        }else if(selectedWorkout[3].equals("core")){
            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                toUpdate.setText(documentSnapShot.getString("Core"));
            });
        }else if(selectedWorkout[3].equals("lower")){
            docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                toUpdate.setText(documentSnapShot.getString("Lower"));
            });
        }else{
            System.out.println("Not possible.");
        }




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


    }

    public void submitReps(){
        db = FirebaseFirestore.getInstance();

        Intent intent = new Intent(this, ProgressActivity.class);
        EditText enteredReps = findViewById(R.id.editText_workout_reps);
        TextView categoryUpdate = findViewById(R.id.textView_workout_invisibileCategory);

        Bundle inBundle = getIntent().getExtras();
        String[] selectedWorkoutKey = inBundle.getStringArray("WORKOUTKEY");

        String user_email = selectedWorkoutKey[4];

        DocumentReference docref = db.collection("users").document(user_email);

        if(enteredReps != null){
            if(category.equals("upper")){
                upperReps += Integer.parseInt(enteredReps.getText().toString());
                docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                   categoryUpdate.setText(documentSnapShot.getString("Upper"));
                });
                int upper = Integer.parseInt(categoryUpdate.getText().toString() + upperReps);
                docref.update("Upper", upper + "").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Updated: " + upper);
                        upperReps = 0;
                    }
                });

            }else if(category.equals("core")){
                coreReps += Integer.parseInt(enteredReps.getText().toString());
                docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                    categoryUpdate.setText(documentSnapShot.getString("Core"));
                });
                int core = Integer.parseInt(categoryUpdate.getText().toString() + coreReps);
                docref.update("Core", core + "").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Updated: " + core);
                        coreReps = 0;
                    }
                });

            }else if(category.equals("lower")){
                lowerReps += Integer.parseInt(enteredReps.getText().toString());
                docref.addSnapshotListener(this, (documentSnapShot, error) -> {
                    categoryUpdate.setText(documentSnapShot.getString("Lower"));
                });
                int lower = Integer.parseInt(categoryUpdate.getText().toString() + lowerReps);
                docref.update("Lower", lower + "").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("Updated: " + lower);
                        lowerReps = 0;
                    }
                });
            }else{
                System.out.println("Doesn't Exist");
            }
        }

        Bundle pieExtras = new Bundle();
        String[] pieData = {category, enteredReps.getText().toString(), user_email};
        pieExtras.putStringArray("PIEKEY", pieData);
        intent.putExtras(pieExtras);

        System.out.println(pieData[0] + " " + pieData[1] + " " + pieData[2]);

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


        String user_email = selectedWorkoutKey[4];

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
                        String[] pushUpsArr = {"Push Ups", PUSHUPSID, 60000 + "", "upper", user_email};
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