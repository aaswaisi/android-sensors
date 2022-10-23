package com.example.lightsensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor s;
    TextView textView;

    ConstraintLayout constraintLayout;

    MediaPlayer mediaPlayerM;
    MediaPlayer mediaPlayerN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.infoLightVal);
        constraintLayout = findViewById(R.id.myImage);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        s = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(s != null){
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sensor is not found!!!", Toast.LENGTH_SHORT).show();
        }

        mediaPlayerM = MediaPlayer.create(this,R.raw.morning);
        mediaPlayerN = MediaPlayer.create(this,R.raw.night);

    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT){
            int valLight = (int) sensorEvent.values[0];
            textView.setText("Val: "+valLight+"\n\nMax Val: "+sensorEvent.sensor.getMaximumRange());

            if(valLight > 10){
                constraintLayout.setBackgroundResource(R.drawable.morning);
                if(!mediaPlayerM.isPlaying()){
                    mediaPlayerM.start();
                }
                if(mediaPlayerN.isPlaying()){
                    mediaPlayerN.pause();
                    mediaPlayerN.seekTo(0);
                }
            }else{
                constraintLayout.setBackgroundResource(R.drawable.night);
                if(!mediaPlayerN.isPlaying()){
                    mediaPlayerN.start();
                }
                if(mediaPlayerM.isPlaying()){
                    mediaPlayerM.pause();
                    mediaPlayerM.seekTo(0);
                }
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