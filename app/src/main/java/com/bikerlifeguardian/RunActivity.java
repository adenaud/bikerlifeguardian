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
    public void onCollision() {
        btnStop.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);
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
