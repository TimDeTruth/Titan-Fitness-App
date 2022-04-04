package com.bcit.titan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;


import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;



public class ProgressActivity extends AppCompatActivity {
    private PieChart pieChart;
    FirebaseFirestore db;
    FirebaseAuth auth;

    float core = 0f;
    float upper = 0f;
    float lower = 0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        pieChart = findViewById(R.id.piechart_progress_volume);
        setupPieChart();
        loadPieChartData();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Activity");
        pieChart.setCenterTextSize(24);
        pieChart.setHoleColor(Color.WHITE);


        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartData() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        DocumentReference docref = db.collection("exercises").document(auth.getCurrentUser().getUid());
        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        chart(document);
                        updateChart();
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
//                        Log.d(TAG, "No such document");
                    }
                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }

    private void updateChart(){
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry( upper,"Upper Body"));
        entries.add(new PieEntry(lower, "Lower Body"));
        entries.add(new PieEntry(core, "Legs"));
        ;

        ArrayList<Integer> colors = new ArrayList<>();

        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.JOYFUL_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Workout Volume Category");

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseOutSine);
    }

    private void chart(DocumentSnapshot document){
        if (document.getData().get("upper") != null) {
            upper = Float.parseFloat(document.getData().get("upper").toString());
        }
        if (document.getData().get("core") != null) {
            core = Float.parseFloat(document.getData().get("core").toString());
        }
        if (document.getData().get("lower") != null) {
            lower = Float.parseFloat(document.getData().get("lower").toString());
        }

        System.out.println(upper + " " + core + " " + lower);
    }
}