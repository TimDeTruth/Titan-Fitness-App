package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

public class SettingsActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        username = findViewById(R.id.editText_settings_username);
        getUsername();


//        spinner.setOnItemSelectedListener();

        Button logoutButton = findViewById(R.id.button_settings_logout);
        logoutButton.setOnClickListener(view -> logoutButtonClicked());

        Button submitButton = findViewById(R.id.button_settings_submit);
        // need on click, to update username to save to db.

//        String newPassword = "SOME-SECURE-PASSWORD";
//        auth.getCurrentUser().updatePassword(newPassword)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User password updated.");
//                        }
//                    }
//                });


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
                        username.setText(usernameString);

                    } else {
                        System.out.println(document.get("NO SUCH DOCUMENT"));
                    }
                } else {
                    System.out.println(task.getException().toString());

                }
            }

        });
    }

    private void logoutButtonClicked(){
        auth.signOut();
        Intent intentLogin = new Intent(this, MainActivity.class);
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLogin);
        finish();
    }

    public void easyButton(View view){

    }

    public void mediumButton(View view){

    }

    public void hardButton(View view){

    }

//    void setupSpinner() {
//        Spinner spinner = findViewById(R.id.spinner_settings_difficulty_change);
//        WorkoutData workoutData = new WorkoutData();
//
//        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
//                R.array.difficulty, android.R.layout.simple_spinner_dropdown_item);
//
//        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrAdapter);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                int spinnerPosition = spinner.getSelectedItemPosition();
//                switch(spinner.getSelectedItem().toString()){
//                    case "Easy":
//                        workoutData.setWorkout_level(1f);
//                        spinner.setSelection(spinnerPosition);
//                        Toast.makeText(SettingsActivity.this, "Level: Easy. Settings Changed. Don't click Submit.",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case "Medium":
//                        workoutData.setWorkout_level(1.5f);
//                        Toast.makeText(SettingsActivity.this, "Level: Medium. Settings Changed. Don't click Submit.",
//                                Toast.LENGTH_SHORT).show();
//                        spinner.setSelection(spinnerPosition);
//                        break;
//                    case "Hard":
//                        workoutData.setWorkout_level(2f);
//                        Toast.makeText(SettingsActivity.this, "Level: Hard. Settings Changed. Don't click Submit.",
//                                Toast.LENGTH_SHORT).show();
//                        spinner.setSelection(spinnerPosition);
//                        break;
//                    case "Titan":
//                        workoutData.setWorkout_level(3f);
//                        Toast.makeText(SettingsActivity.this, "Level: Titan. Settings Changed. Don't click Submit.",
//                                Toast.LENGTH_SHORT).show();
//                        spinner.setSelection(spinnerPosition);
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//    }


}