package com.dima.hw09;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = findViewById(R.id.chart);
        chart.getDescription().setEnabled(false);
        chart.setFitBars(true);

        setData();
        chart.invalidate();
    }

    private void setData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 40f));
        entries.add(new BarEntry(2, 60f));
        entries.add(new BarEntry(3, 50f));
        entries.add(new BarEntry(4, 70f));
        entries.add(new BarEntry(5, 90f));
        entries.add(new BarEntry(6, 80f));

        BarDataSet dataSet = new BarDataSet(entries, "Monthly Sales");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);

        chart.setData(data);
    }
}