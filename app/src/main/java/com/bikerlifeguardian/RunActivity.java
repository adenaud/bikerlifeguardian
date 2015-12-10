package com.bikerlifeguardian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bikerlifeguardian.event.CollisionListener;
import com.bikerlifeguardian.service.CollisionService;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class RunActivity extends RoboActionBarActivity implements CollisionListener, View.OnClickListener {

    @Inject
    private CollisionService collisionService;

    @InjectView(R.id.btn_stop)
    private Button btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        collisionService.initSensors();
        collisionService.setCollisionListener(this);

        btnStop.setOnClickListener(this);
    }

    @Override
    public void onCollision() {

    }

    @Override
    public void onClick(View v) {
        if( v == btnStop){
            finishActivity(RESULT_OK);
        }
    }
}
