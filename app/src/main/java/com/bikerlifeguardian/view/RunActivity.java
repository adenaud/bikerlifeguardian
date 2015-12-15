package com.bikerlifeguardian.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bikerlifeguardian.R;
import com.bikerlifeguardian.event.CollisionListener;
import com.bikerlifeguardian.service.AlertService;
import com.bikerlifeguardian.service.CollisionService;
import com.google.inject.Inject;

import java.util.Timer;
import java.util.TimerTask;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

public class RunActivity extends RoboActionBarActivity implements CollisionListener, View.OnClickListener {

    @Inject
    private CollisionService collisionService;

    @Inject
    private AlertService alertService;

    @InjectView(R.id.btn_stop)
    private Button btnStop;

    @InjectView(R.id.btn_cancel)
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        collisionService.initSensors();
        collisionService.setCollisionListener(this);

        btnStop.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        end();
        super.onBackPressed();
    }

    @Override
    public void onCollision(final double latitude, final double longitude, final double speed) {
        btnStop.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);

        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                alertService.sendAlert("2d931510-d99f-494a-8c67-87feb05e1594",latitude,longitude,speed);
                collisionService.reset();
            }
        },getResources().getInteger(R.integer.cancel_delay));

    }

    @Override
    public void onClick(View v) {
        if (v == btnStop) {
            end();
        }
        if (v == btnCancel) {
            btnCancel.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);
        }
    }

    private void end() {
        collisionService.stop();
        setResult(RESULT_OK);
        finish();
    }

}
