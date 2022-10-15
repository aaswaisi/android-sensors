package com.example.proximitysensorkotlin

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() , SensorEventListener{

    lateinit var sensorManager: SensorManager
    lateinit var s: Sensor
    lateinit var imageView: ImageView
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.myImage)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        s = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if(s != null){
            Toast.makeText(this, "PROXIMITY Sensor is found ☺", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "PROXIMITY Sensor is not found!", Toast.LENGTH_SHORT).show()
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.music)

    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_PROXIMITY){
                var value = event.values[0].toInt()
                Toast.makeText(this, "Value: "+value, Toast.LENGTH_SHORT).show()

                if (value > 0) {
                    val objectAnimatorX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 3f)
                    val objectAnimatorY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 3f)
                    val objectAnimatorRX = ObjectAnimator.ofFloat(imageView, View.ROTATION_X, 0f)
                    val objectAnimatorRY = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 0f)
                    objectAnimatorX.duration = 1000
                    objectAnimatorY.duration = 1000
                    objectAnimatorRX.duration = 1000
                    objectAnimatorRY.duration = 1000

                    val animatorSet = AnimatorSet()
                    animatorSet.play(objectAnimatorX).with(objectAnimatorY).with(objectAnimatorRX)
                        .with(objectAnimatorRY)

                    animatorSet.start()

                    if(mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                    }

                } else {
                    val objectAnimatorX = ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.2f)
                    val objectAnimatorY = ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.2f)
                    val objectAnimatorRX = ObjectAnimator.ofFloat(imageView, View.ROTATION_X, 100f)
                    val objectAnimatorRY = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 100f)
                    objectAnimatorX.duration = 1000
                    objectAnimatorY.duration = 1000
                    objectAnimatorRX.duration = 1000
                    objectAnimatorRY.duration = 1000

                    val animatorSet = AnimatorSet()
                    animatorSet.play(objectAnimatorX).with(objectAnimatorY).with(objectAnimatorRX)
                        .with(objectAnimatorRY)

                        animatorSet.start()

                        mediaPlayer.start()
                    }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)

    }
}