package com.example.orientationsensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor AMSensor;
    Sensor MFSensor;

    float rotationValues[] = new float[9];
    float AMValues[] = new float[3];
    float MFValues[] = new float[3];

    float finalValues[] = new float[3];

    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        textView = findViewById(R.id.infoCompass);
        imageView = findViewById(R.id.imageCompass);

        AMSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        MFSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if(AMSensor != null && MFSensor != null){
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sensor not found!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            /*AMValues[0] = sensorEvent.values[0];
            AMValues[1] = sensorEvent.values[1];
            AMValues[2] = sensorEvent.values[2];*/
            AMValues = sensorEvent.values;
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            MFValues = sensorEvent.values;
        }

        SensorManager.getRotationMatrix(rotationValues, null,AMValues,MFValues);
        SensorManager.getOrientation(rotationValues, finalValues);

        int val = (int)Math.toDegrees(finalValues[0]);
        if(val < 0) {
              System.out.println("!!!: "+(val+360));
              textView.setText(""+(val+360));
              imageView.setRotation(val*-1);
        }else{
            System.out.println("!!!: "+val);
            textView.setText(""+val);
            imageView.setRotation(val*-1);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,
                AMSensor, sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,
                MFSensor, sensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}