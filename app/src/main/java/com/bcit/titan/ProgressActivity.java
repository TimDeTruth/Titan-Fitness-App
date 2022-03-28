package com.bcit.titan;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ProgressActivity extends AppCompatActivity {
    private PieChart pieChart;
    FirebaseFirestore db;

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
        pieChart.setCenterText("Volume");
        pieChart.setCenterTextSize(24);
        pieChart.setHoleColor(Color.WHITE);


//        Description descChartDescription = new Description();
//        descChartDescription.setEnabled(true);
//        pieChart.setDescription(descChartDescription);


        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setEnabled(true);


    }

    private void loadPieChartData() {
        Bundle pieBundle = getIntent().getExtras();
        String[] repsArray = pieBundle.getStringArray("PIEKEY");

        String user_email = repsArray[2];
        db = FirebaseFirestore.getInstance();
        TextView upper = findViewById(R.id.textView_progress_upper);
        TextView core = findViewById(R.id.textView_progress_core);
        TextView lower = findViewById(R.id.textView_progress_lower);

        DocumentReference docref = db.collection("users").document(user_email);
        docref.addSnapshotListener(this, (documentSnapShot, error) -> {
            upper.setText(documentSnapShot.getString("Upper"));
            core.setText(documentSnapShot.getString("Core"));
            lower.setText(documentSnapShot.getString("Lower"));
        });

        System.out.println("Upper is " + upper.getText().toString() + "\n" +
                "core is " + core.getText().toString() + " \n" +
                "lower is " + lower.getText().toString());

        System.out.println(repsArray[1]);

//        String u = upper.getText().toString();
//        String c = upper.getText().toString();
//        String l = upper.getText().toString();
//
//        System.out.println(u);
//        System.out.println(c);
//        System.out.println(l);

//        float upperNum = (float)Integer.parseInt(u);
//        float coreNum = (float)Integer.parseInt(c);
//        float lowerNum = (float)Integer.parseInt(l);
//        Integer upperNumInt = new Integer(upperNum);
//        float upperNumFloat = upperNumInt.floatValue();
//
//        int lowerNum = Integer.parseInt(upper.getText().toString());
//        Integer lowerNumInt = new Integer(lowerNum);
//        float lowerNumFloat = lowerNumInt.floatValue();
//
//        int coreNum = Integer.parseInt(upper.getText().toString());
//        Integer coreNumInt = new Integer(coreNum);
//        float coreNumFloat = coreNumInt.floatValue();

//
//        System.out.println("Upper: " + upper.getText().toString());
//        System.out.println("Upper: " + core.getText().toString());
//        System.out.println(lower.getText().toString());

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.8f, "Upper Body"));
        entries.add(new PieEntry(0.15f, "Lower Body"));
        entries.add(new PieEntry(0.1f, "Legs"));
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
}