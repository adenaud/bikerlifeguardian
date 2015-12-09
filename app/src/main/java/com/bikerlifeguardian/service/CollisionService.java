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

import com.bikerlifeguardian.dao.RecordDao;
import com.bikerlifeguardian.event.CollisionListener;
import com.bikerlifeguardian.model.Record;
import com.bikerlifeguardian.network.TcpBlgClient;
import com.google.inject.Inject;

public class CollisionService implements SensorEventListener, LocationListener {

    private Context context;

    private CollisionListener collisionListener;

    private float x;
    private float y;
    private float z;
    private float speed;
    private float collisionThreshold;

    @Inject
    private RecordDao recordDao;

    @Inject
    public CollisionService(Context context){
        this.context = context;
    }

    public void initSensors() {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor != null){
            collisionThreshold = (float) (sensor.getMaximumRange() - 0.2);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,200,0,this);
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            final float cx = event.values[0];
            final float cy = event.values[1];
            final float cz = event.values[2];

            double recordThreshold =  3.5;

            if(Math.abs(cx - x) > recordThreshold || Math.abs(cy - y) > recordThreshold || Math.abs(cz - z) > recordThreshold){
                recordDao.save(new Record(cx,cy,cz,0));
            }

            if(cx > collisionThreshold || cy > collisionThreshold || cz > collisionThreshold){
                if(collisionListener != null){
                    collisionListener.onCollision();
                }
            }
            x = cx;
            y = cy;
            z = cz;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location.hasSpeed()){
            speed = location.getSpeed();
        }else{
            speed = -1;
        }

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
}
