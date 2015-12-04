package com.bikerlifeguardian;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bikerlifeguardian.service.ExportService;
import com.github.mikephil.charting.components.YAxis;
import com.google.inject.Inject;

import com.bikerlifeguardian.component.Chart;
import com.bikerlifeguardian.dao.RecordDao;
import com.bikerlifeguardian.model.Record;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActionBarActivity implements SensorEventListener, LocationListener, View.OnClickListener {

    @Inject
    private ExportService exportService;

    @Inject
    private RecordDao recordDao;

    @InjectView(R.id.layout)
    private LinearLayout layout;

    @InjectView(R.id.btn_export)
    private Button btnExport;

    @InjectView(R.id.btn_drop)
    private Button btnDrop;

    private Chart xChart;
    private Chart yChart;
    private Chart zChart;

    private float x;
    private float y;
    private float z;

    private double speed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }catch (SecurityException e){
            e.printStackTrace();
        }


        initCharts();

        btnExport.setOnClickListener(this);
        btnDrop.setOnClickListener(this);
    }

    private void initCharts() {
        xChart = new Chart(this);
        yChart = new Chart(this);
        zChart = new Chart(this);

        xChart.addLine("X", YAxis.AxisDependency.LEFT, Color.RED);
        yChart.addLine("Y", YAxis.AxisDependency.LEFT, Color.GREEN);
        zChart.addLine("Z", YAxis.AxisDependency.LEFT, Color.BLUE);


        layout.addView(xChart.getLineChart());
        layout.addView(yChart.getLineChart());
        layout.addView(zChart.getLineChart());

        xChart.getLineChart().getLayoutParams().height = 400;
        yChart.getLineChart().getLayoutParams().height = 400;
        zChart.getLineChart().getLayoutParams().height = 400;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            final float cx = event.values[0];
            final float cy = event.values[1];
            final float cz = event.values[2];

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    xChart.addEntry(cx, "", 0);
                    yChart.addEntry(cy, "", 0);
                    zChart.addEntry(cz,"",0);

                    double threshold = 3.5;
                    if(Math.abs(cx - x) > threshold || Math.abs(cy - y) > threshold || Math.abs(cz - z) > threshold){
                        saveRecord(cx,cy,cz);
                    }

                    x = cx;
                    y = cy;
                    z = cz;
                }
            });
        }
    }

    private void saveRecord(float x, float y, float z) {
        recordDao.save(new Record((double)x,(double)y,(double)z,speed));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location) {
        speed = (double) location.getSpeed();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {

        if(v == btnExport){
            exportService.exportAll();
        }
        if(v == btnDrop){
            exportService.drop();
        }
    }
}
