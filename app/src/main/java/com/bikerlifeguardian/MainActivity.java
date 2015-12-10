package com.bikerlifeguardian;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bikerlifeguardian.service.ExportService;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;
import roboguice.util.Ln;

public class MainActivity extends RoboActionBarActivity implements View.OnClickListener {

    @Inject
    private ExportService exportService;

    @InjectView(R.id.text_test)
    private TextView textView;

    @InjectView(R.id.btn_export)
    private Button btnExport;

    @InjectView(R.id.btn_drop)
    private Button btnDrop;

    @InjectView(R.id.btn_details)
    private Button btnDetails;

    @InjectView(R.id.btn_start)
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnExport.setOnClickListener(this);
        btnDrop.setOnClickListener(this);
        btnDetails.setOnClickListener(this);
        btnStart.setOnClickListener(this);


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
            startActivityForResult(intent, Codes.REQUEST_FORM);
        }
        if(v == btnStart){
            Intent intent = new Intent(this,RunActivity.class);
            startActivityForResult(intent,Codes.REQUEST_RUN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == Codes.REQUEST_FORM){
            Ln.d("Result ok : 0x55");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
