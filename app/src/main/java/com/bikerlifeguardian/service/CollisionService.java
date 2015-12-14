package com.bikerlifeguardian.service;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.bikerlifeguardian.R;
import com.bikerlifeguardian.dao.RecordDao;
import com.bikerlifeguardian.event.CollisionListener;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

public class CollisionService implements SensorEventListener, LocationListener {

    private final Context context;
    private CollisionListener collisionListener;

    private final List<Float> speeds;
    private float collisionThreshold;

    @Inject
    private RecordDao recordDao;
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private double latitude;
    private double longitude;
    private boolean alerted;

    @Inject
    public CollisionService(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        speeds = new ArrayList<>();

    }

    public boolean isPhoneCompatible() {
        boolean isCompatible = false;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null && sensor.getMaximumRange() > context.getResources().getInteger(R.integer.min_ms2)) {
            isCompatible = true;
        }
        return isCompatible;
    }

    public void initSensors() {
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            collisionThreshold = (float) (sensor.getMaximumRange() - 0.5);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            final float cx = event.values[0];
            final float cy = event.values[1];
            final float cz = event.values[2];

            if (!alerted && (cx > collisionThreshold || cy > collisionThreshold || cz > collisionThreshold)) {
                if (collisionListener != null) {
                    float averageSpeed = getAverageSpeed();
                    alerted = true;
                    collisionListener.onCollision(latitude, longitude, averageSpeed);
                }
            }
        }
    }

    public void reset(){
        synchronized (speeds){
            speeds.clear();
        }
        alerted = false;
    }

    private float getAverageSpeed() {
        float total = 0;
        for (Float speed : speeds){
            total += speed;
        }

        if(speeds.size() == 0){
            return 0;
        }
        else {
            return total / speeds.size();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onLocationChanged(Location location) {
        synchronized (speeds) {
            if (speeds.size() == 5) {
                speeds.remove(speeds.get(0));
            }
            if (location.hasSpeed()) {
                speeds.add(location.getSpeed());
            } else {
                speeds.add(-1f);
            }
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
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

    public void setCollisionListener(CollisionListener collisionListener) {
        this.collisionListener = collisionListener;
    }

    public void stop() {
        sensorManager.unregisterListener(this);
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
