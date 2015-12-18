package com.bikerlifeguardian.service;

import com.bikerlifeguardian.model.Alert;
import com.bikerlifeguardian.network.Api;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AlertService {

    @Inject
    private Api api;

    public String sendAlert(String userUuid, double latitude, double longitude, double speed){
        Alert alert = new Alert();
        alert.setUser_uuid(userUuid);
        alert.setLatitude(latitude);
        alert.setLongitude(longitude);
        alert.setSpeed(speed);
        return api.sendAlert(alert);
    }

    public void cancelAlert(String uuid) {
        api.cancelAlert(uuid);
    }
}
