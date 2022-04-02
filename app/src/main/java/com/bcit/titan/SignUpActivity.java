package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        System.out.println("INSIDE SIGNUP ON CREATE");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


    }

    public void addPerson(View view){

        EditText username = findViewById(R.id.editText_signup_username);
        EditText password = findViewById(R.id.editText_signup_password);
        EditText firstname = findViewById(R.id.editText_signup_first_name);
        EditText lastname = findViewById(R.id.editText_signup_last_name);
        EditText email = findViewById(R.id.editText_signup_email);


        User user = new User(username.getText().toString(), firstname.getText().toString(), lastname.getText().toString(), email.getText().toString());

        Intent intentHome = new Intent(this, HomeActivity.class);

        auth.createUserWithEmailAndPassword(user.getEmail(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                @Override
                public void onSuccess(AuthResult authResult) {

                    db.collection("users").document(authResult.getUser().getUid())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(SignUpActivity.this, "Authentication Collection Success!!!!!.",
                                            Toast.LENGTH_SHORT).show();
                                    intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intentHome);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Debug", "Error writing document", e);
                                    Toast.makeText(SignUpActivity.this, "Authentication Collection failed!!!.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Authentication failed!!!.",
                        Toast.LENGTH_SHORT).show();
            }
        });

//        Map<String, Object> user = new HashMap<>();
//        user.put("username",username.getText().toString());
//        user.put("email",email.getText().toString());
//        user.put("password",password.getText().toString());
//        user.put("firstname",firstname.getText().toString());
//        user.put("lastname",lastname.getText().toString());
//        user.put("Upper", 0 + "");
//        user.put("Lower", 0 + "");
//        user.put("Core", 0 + "");
//        user.put("Log",  "Log: ");

//        String user_id = email.getText().toString();

        //String id = usersRef.collection("users").document().getId(); //Gets de generated id


        //              THIS MIGHT NOT BE NEEDED
//        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
//        CollectionReference usersRef = rootRef.collection("users");
//        usersRef.document(id).set(user);
//        addCollection(id);

//        db.collection("users").document(user_id)
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("Debug", "DocumentSnapshot successfully written.");
//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        intent.putExtra("user_email", user_id);
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Debug", "Error writing document", e);
//                    }
//                });




        //String id = usersRef.collection("users").document().getId(); //Gets de generated id

//        Map<String, Object> exerciseTypes = new HashMap<>();
//        exerciseTypes.put("Upper", 0);
//        exerciseTypes.put("Lower", 0);
//        exerciseTypes.put("Core", 0);

//        String id = user.getUsername();

//

//        db.collection("users")
//                .add(user) // user
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        String id = user.getUsername().toString();
//
//                        usersRef.document(id).set(user);
//
//                        Log.d("Debug", "DocumentSnapshot added with ID: " + documentReference.getId());
//
//
//                        Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
//                        // used to be addCollection(documentReference);
//
//                        addCollection(id);
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Debug", "Error adding document", e);
//                    }
//                });



//        System.out.println("The exercise type: " + exerciseTypes);
//
//
//        System.out.println("This works i got here");


    }

//    void addCollection(String id){      // used to be addCollection(DocumentReference documentReference)
//        Map<String, Object> exerciseTypes = new HashMap<>();
//        exerciseTypes.put("Upper", 0);
//        exerciseTypes.put("Lower", 0);
//        exerciseTypes.put("Core", 0);
//        // document(documentReference.getId());
//        db.collection("users").document(id).collection("Exercise").document("ExerciseType")
//                .set(exerciseTypes)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Log.d("Debug", "DocumentSnapshot successfully written.");
//                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//                        startActivity(intent);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Debug", "Error writing document", e);
//                    }
//                });
//
//        System.out.println("This works i got here22222");
//    }


}