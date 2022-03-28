package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hide ActionBar
        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();



        View login = findViewById(R.id.button_bottomNav_login);

        View signup = findViewById(R.id.button_bottomNav_signup);

        Intent intent = new Intent(this, HomeActivity.class);
        Intent intent2 = new Intent(this, SignUpActivity.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userName = findViewById(R.id.editText_main_username);
                EditText password = findViewById(R.id.editText_main_password);

                String userNameStr = userName.getText().toString();
                String passwordStr = password.getText().toString();

                System.out.println("The username: " + userName.getText().toString());
                System.out.println("The password: " + password.getText().toString());

                List<User> user = new ArrayList<User>();

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("Debug", document.getData().toString());

                                        user.add(
                                                new User(
                                                        document.getData().get("username").toString(),
                                                        document.getData().get("password").toString(),
                                                        document.getData().get("firstname").toString(),
                                                        document.getData().get("lastname").toString(),
                                                        document.getData().get("email").toString()
                                                )
                                        );
                                        System.out.println("List: " + user);
                                        for (int i = 0; i < user.size(); i++) {
                                            System.out.println("Firebase User: " + user.get(i).getUsername());
                                            System.out.println("Firebase User password: " + user.get(i).getPassword());
                                            if(userNameStr.equals(user.get(i).getUsername())
                                                    && passwordStr.equals(user.get(i).getPassword())){

                                                System.out.println(" i got thre");
                                                startActivity(intent);
                                            }
                                        }

                                    }
                                } else {
                                    Log.w("Debug", "Error getting documents", task.getException());
                                }

                                User[] usersArray = user.toArray(new User[user.size()]);
                            }
                        });

//                mAuth.signInWithEmailAndPassword(userNameStr, passwordStr).addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
//                    }
//
//                });

            }

        });


    }


}