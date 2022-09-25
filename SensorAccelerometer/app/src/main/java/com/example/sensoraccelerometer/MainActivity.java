package com.example.sensoraccelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.infoXYZ);
        imageView = findViewById(R.id.myImage);

        SensorManager sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        List<Sensor> mySensor =  sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < mySensor.size(); i++) {
            System.out.println("ALL Sensors Name: "+mySensor.get(i).getName());
        }
        //////////////////////////////////////////////////////////////////////////
        Sensor s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(s != null){
            System.out.println("Sensor Accelerometer is found ☺");
        }else{
            System.out.println("Sensor Aaccelerometer is not found!!!");
        }
        ////////////////////////////////////////////////
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            DecimalFormat df = new DecimalFormat("0.0");
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            textView.setText("X: "+df.format(x)+"\nY: "+df.format(y)+"\nZ: "+df.format(z));

            imageView.setX(imageView.getX() - (x*3));
            imageView.setY(imageView.getY() + (y*3));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}