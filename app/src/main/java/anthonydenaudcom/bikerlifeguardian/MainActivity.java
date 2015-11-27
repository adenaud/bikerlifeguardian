package anthonydenaudcom.bikerlifeguardian;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.YAxis;

import java.util.Date;

import anthonydenaudcom.bikerlifeguardian.component.Chart;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    private TextView textView;
    private LinearLayout layout;

    private Chart xChart;
    private Chart yChart;
    private Chart zChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text_xyz);
        layout = (LinearLayout) findViewById(R.id.layout);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if(sensor != null){
            sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

        initCharts();
    }

    private void initCharts() {
        xChart = new Chart(this);
        yChart = new Chart(this);
        zChart = new Chart(this);

        xChart.addLine("X", YAxis.AxisDependency.LEFT, Color.RED);
        yChart.addLine("Y", YAxis.AxisDependency.LEFT, Color.GREEN);
        zChart.addLine("Z", YAxis.AxisDependency.LEFT, Color.BLUE);


        layout.addView(xChart.getLineChart());
        layout.addView(yChart.getLineChart());
        layout.addView(zChart.getLineChart());

        xChart.getLineChart().getLayoutParams().height = 400;
        yChart.getLineChart().getLayoutParams().height = 400;
        zChart.getLineChart().getLayoutParams().height = 400;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;

        if(sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            final float x = event.values[0];
            final float y = event.values[1];
            final float z = event.values[2];

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText( "x: " + String.valueOf(x) + "    y: " +String.valueOf(y+ "   z: " + String.valueOf(z)));

                    xChart.addEntry(x, "", 0);
                    yChart.addEntry(y, "", 0);
                    zChart.addEntry(z,"",0);


                }
            });
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
