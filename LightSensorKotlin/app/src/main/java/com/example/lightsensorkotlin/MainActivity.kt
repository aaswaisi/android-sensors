package com.example.lightsensorkotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    var s: Sensor? = null

    var textView: TextView? = null

    var constraintLayout: ConstraintLayout? = null

    var mediaPlayerM: MediaPlayer? = null
    var mediaPlayerN: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.infoLightVal)
        constraintLayout = findViewById(R.id.myImage)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        s = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (s != null) {
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sensor is not found!!!", Toast.LENGTH_SHORT).show()
        }

        mediaPlayerM = MediaPlayer.create(this, R.raw.morning)
        mediaPlayerN = MediaPlayer.create(this, R.raw.night)

    }


    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_LIGHT) {
            val valLight = sensorEvent.values[0].toInt()
            textView!!.text = "Val: $valLight Max Val: ${sensorEvent.sensor.maximumRange}"

            if (valLight > 10) {
                constraintLayout!!.setBackgroundResource(R.drawable.morning)
                if (!mediaPlayerM!!.isPlaying) {
                    mediaPlayerM!!.start()
                }
                if (mediaPlayerN!!.isPlaying) {
                    mediaPlayerN!!.pause()
                    mediaPlayerN!!.seekTo(0)
                }
            } else {
                constraintLayout!!.setBackgroundResource(R.drawable.night)
                if (!mediaPlayerN!!.isPlaying) {
                    mediaPlayerN!!.start()
                }
                if (mediaPlayerM!!.isPlaying) {
                    mediaPlayerM!!.pause()
                    mediaPlayerM!!.seekTo(0)
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, i: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}