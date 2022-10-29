package com.example.magneticfieldsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor s;

    TextView textView_magnetic;
    TextView textView_axis;

    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_magnetic = findViewById(R.id.info_magnetic);
        textView_axis = findViewById(R.id.info_axis);
        view = findViewById(R.id.magneticPower);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        s = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (s != null) {
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Sensor is not found !!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            int x = (int) Math.abs(sensorEvent.values[0]);
            int y = (int) Math.abs(sensorEvent.values[1]);
            int z = (int) Math.abs(sensorEvent.values[2]);

            textView_magnetic.setText("X: " + x + "\nY: " + y + "\nZ: " + z + "\nMax Val: "
                    + sensorEvent.sensor.getMaximumRange());

            if (x > y && x > z) {
                textView_axis.setText("X");
            } else if (y > x && y > z) {
                textView_axis.setText("Y");
            } else {
                textView_axis.setText("Z");
            }

            if(x > 1000 || y > 1000 || z > 1000){
                view.setBackgroundColor(Color.RED);
            }else if(x > 500 || y > 500 || z > 500){
                view.setBackgroundColor(Color.BLUE);
            }else if(x > 100 || y > 100 || z > 100){
                view.setBackgroundColor(Color.GREEN);
            }else{
                view.setBackgroundColor(Color.BLACK);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, s, sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}