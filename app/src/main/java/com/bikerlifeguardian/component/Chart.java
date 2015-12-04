package com.bikerlifeguardian.component;

import android.content.Context;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

public class Chart {

    private LineChart lineChart;
    private LineData lineData;

    public Chart(Context context){
        lineChart = new LineChart(context);
        lineData = new LineData();

        lineChart.setData(lineData);
    }


    public void addLine(String title, YAxis.AxisDependency axisDependency, int color){
        LineDataSet lineDataSet = new LineDataSet(null,title);
        lineDataSet.setAxisDependency(axisDependency);
        lineDataSet.setColor(color);
        lineDataSet.setLineWidth(2f);
        lineData.addDataSet(lineDataSet);
    }

    public void addEntry(float yValue, String xValue, int dataSetIndex){
        lineData.addXValue(xValue);
        lineData.addEntry(new Entry(yValue, lineData.getXValCount() - 1), dataSetIndex);
        lineChart.notifyDataSetChanged();

        lineChart.setVisibleXRange(0,20);
        lineChart.moveViewToX(lineData.getXValCount() - 1);
        lineChart.invalidate();
    }

    public LineChart getLineChart() {
        return lineChart;
    }

}
