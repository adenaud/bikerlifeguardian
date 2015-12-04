package com.bikerlifeguardian.model;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public class Record {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private long time;

    @DatabaseField
    private double x;

    @DatabaseField
    private double y;

    @DatabaseField
    private double z;

    @DatabaseField
    private double speed;

    public Record() {
    }

    public Record(double x, double y, double z, double speed) {
        this.time = new Date().getTime();
        this.x = x;
        this.y = y;
        this.z = z;
        this.speed = speed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
