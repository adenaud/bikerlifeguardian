package com.bikerlifeguardian.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import com.bikerlifeguardian.Codes;
import com.bikerlifeguardian.MainActivity;
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

    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private String userUuid;
    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        collisionService.initSensors();
        collisionService.setCollisionListener(this);

        btnStop.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if(!collisionService.isPhoneCompatible()){
            setResult(Codes.PHONE_NOT_COMPATIBLE);
            finish();
        }

        SharedPreferences preferences = getSharedPreferences(MainActivity.class.getPackage().getName(), MODE_PRIVATE);
        userUuid = preferences.getString("uuid","");
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

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
        startWarning();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                uuid = alertService.sendAlert(userUuid, latitude, longitude, speed);
            }
        });
        thread.start();

        Timer alertTimer = new Timer();
        alertTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                collisionService.reset();
            }
        }, getResources().getInteger(R.integer.reset_delay));

    }

    private void startWarning() {
        final AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_RING, am.getStreamMaxVolume(AudioManager.STREAM_RING), 0);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        am.setMode(AudioManager.MODE_NORMAL);
        am.setSpeakerphoneOn(false);
        am.setBluetoothScoOn(false);

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, R.raw.alarme);
        mediaPlayer.start();

        long [] tmp ={250,500};
        for(int i=0;i<5;i++) {
            vibrator.vibrate(tmp, 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnStop) {
            end();
        }
        if (v == btnCancel) {


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    alertService.cancelAlert(uuid);
                }
            });
            thread.start();


            if(mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            if(vibrator.hasVibrator()){
                vibrator.cancel();
            }

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
