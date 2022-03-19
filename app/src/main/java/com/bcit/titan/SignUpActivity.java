package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = FirebaseFirestore.getInstance();


    }

    public void addPerson(View view){
        EditText userName = findViewById(R.id.editText_signup_username);
        EditText password = findViewById(R.id.editText_signup_password);
        EditText firstName = findViewById(R.id.editText_signup_first_name);
        EditText lastName = findViewById(R.id.editText_signup_last_name);
        EditText email = findViewById(R.id.editText_signup_email);
        User user = new User(userName.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString());
        db.collection("users")
                .add(user) // user
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Debug", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error adding document", e);
                    }
                });
    }

}