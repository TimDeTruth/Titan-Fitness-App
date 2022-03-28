package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupSpinner();

        Spinner spinner = findViewById(R.id.spinner_settings_difficulty_change);

//        spinner.setOnItemSelectedListener();



    }


    void setupSpinner() {
        Spinner spinner = findViewById(R.id.spinner_settings_difficulty_change);

        ArrayAdapter<CharSequence> arrAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty, android.R.layout.simple_spinner_dropdown_item);

        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);

    }


}