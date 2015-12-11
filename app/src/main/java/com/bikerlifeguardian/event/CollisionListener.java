package com.bikerlifeguardian.event;

public interface CollisionListener {
    void onCollision(double latitude, double longitude, double speed);
}
