package com.example.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    Sensor s;
    TextView textView;
    HorizontalScrollView hView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.infoXYZ);
        hView = findViewById(R.id.hView);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        s = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(s != null){
            Toast.makeText(this, "GYROSCOPE Sensor is found ☺", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "GYROSCOPE Sensor is not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            int x = (int ) sensorEvent.values[0];
            int y = (int ) sensorEvent.values[1];
            int z = (int ) sensorEvent.values[2];
            textView.setText("X: "+x+"\nY: "+y+"\nZ: "+z);
            hView.smoothScrollTo(hView.getScrollX()+ (y*130),0);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}