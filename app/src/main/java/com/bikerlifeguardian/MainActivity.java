package com.bikerlifeguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bikerlifeguardian.event.CollisionListener;
import com.bikerlifeguardian.service.ExportService;
import com.bikerlifeguardian.service.CollisionService;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class MainActivity extends RoboActionBarActivity implements View.OnClickListener, CollisionListener {

    @Inject
    private ExportService exportService;

    @Inject
    private CollisionService collisionService;

    @InjectView(R.id.text_test)
    private TextView textView;

    @InjectView(R.id.layout)
    private LinearLayout layout;

    @InjectView(R.id.btn_export)
    private Button btnExport;

    @InjectView(R.id.btn_drop)
    private Button btnDrop;

    @InjectView(R.id.btn_details)
    private Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnExport.setOnClickListener(this);
        btnDrop.setOnClickListener(this);
        btnDetails.setOnClickListener(this);

        collisionService.initSensors();
        collisionService.setCollisionListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == btnExport){
            exportService.exportAll();
        }
        if(v == btnDrop){
            exportService.drop();
        }
        if(v == btnDetails){
            Intent intent = new Intent(this,FormActivity.class);
            startActivityForResult(intent,0x55);
        }
    }

    @Override
    public void onCollision() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Collision detected !");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 0x55){
            Ln.d("Result ok : 0x55");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
