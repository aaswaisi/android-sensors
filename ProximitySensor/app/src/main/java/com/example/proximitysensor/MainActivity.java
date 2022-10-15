package com.example.proximitysensor;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor s;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.myImage);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        s = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(s != null){
            Toast.makeText(this, "Sensor PROXIMITY is found ☺", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Sensor PROXIMITY not found!!!", Toast.LENGTH_SHORT).show();
        }
    }

    MediaPlayer mediaPlayer;
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            int val = (int) sensorEvent.values[0];
            Toast.makeText(this, "Value: "+val, Toast.LENGTH_SHORT).show();

            if(val > 0){
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView,imageView.SCALE_X,3);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView,imageView.SCALE_Y,3);
                ObjectAnimator objectAnimatorRX = ObjectAnimator.ofFloat(imageView,imageView.ROTATION_X,0f);
                ObjectAnimator objectAnimatorRY = ObjectAnimator.ofFloat(imageView,imageView.ROTATION_Y,0f);
                objectAnimatorX.setDuration(1000);
                objectAnimatorY.setDuration(1000);
                objectAnimatorRX.setDuration(1000);
                objectAnimatorRY.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimatorX).with(objectAnimatorY).with(objectAnimatorRX).with(objectAnimatorRY);
                animatorSet.start();
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer = null;
                }
            }else{
                ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageView,imageView.SCALE_X,0.2f);
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageView,imageView.SCALE_Y,0.2f);
                ObjectAnimator objectAnimatorRX = ObjectAnimator.ofFloat(imageView,imageView.ROTATION_X,100f);
                ObjectAnimator objectAnimatorRY = ObjectAnimator.ofFloat(imageView,imageView.ROTATION_Y,100f);
                objectAnimatorX.setDuration(1000);
                objectAnimatorY.setDuration(1000);
                objectAnimatorRX.setDuration(1000);
                objectAnimatorRY.setDuration(1000);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(objectAnimatorX).with(objectAnimatorY).with(objectAnimatorRX).with(objectAnimatorRY);
                animatorSet.start();

                if(mediaPlayer == null){
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music);
                    mediaPlayer.start();
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