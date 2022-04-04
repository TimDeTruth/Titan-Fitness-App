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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Source;

import java.util.HashMap;
import java.util.Map;

public class SettingsActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    EditText username;
    EditText firstname;
    EditText lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        username = findViewById(R.id.editText_settings_username);
        firstname = findViewById(R.id.editText_settings_firstname);
        lastname = findViewById(R.id.editText_settings_lastname);
        getUserInfo();

        Button logoutButton = findViewById(R.id.button_settings_logout);
        logoutButton.setOnClickListener(view -> logoutButtonClicked());

    }

    public void submitChange(View view) {
        DocumentReference docRef = db.collection("users").document(auth.getCurrentUser().getUid());
        docRef.update("username", username.getText().toString(),
                "firstName", firstname.getText().toString(),
                    "lastName", lastname.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    System.out.println("SUCCESSSSS");
                    Toast.makeText(SettingsActivity.this, "User info Updated.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SettingsActivity.this, "Updated failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserInfo() {

        DocumentReference docRef = db.collection("users").document(auth.getCurrentUser().getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String usernameString = document.get("username").toString();
                        String userFirstNameString = document.get("firstName").toString();
                        String userLastNameString = document.get("lastName").toString();
                        username.setText(usernameString);
                        firstname.setText(userFirstNameString);
                        lastname.setText(userLastNameString);
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
        Button easyButton = findViewById(R.id.button_settings_easy);
        Button mediumButton = findViewById(R.id.button_settings_medium);
        Button hardButton = findViewById(R.id.button_settings_hard);
        easyButton.setEnabled(false);
        mediumButton.setEnabled(true);
        hardButton.setEnabled(true);
        WorkoutData data = new WorkoutData();
        data.setWorkout_level(1);
    }

    public void mediumButton(View view){
        Button easyButton = findViewById(R.id.button_settings_easy);
        Button mediumButton = findViewById(R.id.button_settings_medium);
        Button hardButton = findViewById(R.id.button_settings_hard);
        easyButton.setEnabled(true);
        mediumButton.setEnabled(false);
        hardButton.setEnabled(true);
        WorkoutData data = new WorkoutData();
        data.setWorkout_level(1.5f);
    }

    public void hardButton(View view){
        Button easyButton = findViewById(R.id.button_settings_easy);
        Button mediumButton = findViewById(R.id.button_settings_medium);
        Button hardButton = findViewById(R.id.button_settings_hard);
        easyButton.setEnabled(true);
        mediumButton.setEnabled(true);
        hardButton.setEnabled(false);
        WorkoutData data = new WorkoutData();
        data.setWorkout_level(2f);
    }

}