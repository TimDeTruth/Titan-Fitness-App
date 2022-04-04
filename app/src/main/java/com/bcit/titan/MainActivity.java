package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Name(s)  :   Paul Kim, Timmy Lau, Arno Pan
 * Class    :   3717 - Android Development
 * Set      :   3S
 * Term     :   Winter 2022
 * Date     :   04/03/2022
 * Note     :   Final Version - Latest correction: Null crash in pie chart.
 */

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // hide ActionBar
        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // up means its going to look like back button?
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeBottomNavigation();


    }

    private void initializeBottomNavigation() {
        // reference to bottom navigation view
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView_main);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // when an item is selected, there will be animation
            if (item.getItemId() == R.id.button_bottomNav_login) {
                loginItemClicked();
                return true;

            } else if (item.getItemId() == R.id.button_bottomNav_signup) {
                signupItemClicked();
                return true;
            }
            return true;
        });
    }

    private void signupItemClicked() {
        Intent signUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(signUpIntent);
    }

    private void loginItemClicked() {
        Intent intentHome = new Intent(this, HomeActivity.class);

        EditText email = findViewById(R.id.editText_main_username);
        EditText password = findViewById(R.id.editText_main_password);

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        System.out.println("The email: " + email.getText().toString());
        System.out.println("The password: " + password.getText().toString());

        auth.signInWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentHome);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
//

}