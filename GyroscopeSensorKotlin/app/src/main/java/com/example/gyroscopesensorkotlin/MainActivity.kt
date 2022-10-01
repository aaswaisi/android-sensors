package com.example.gyroscopesensorkotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.HorizontalScrollView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() , SensorEventListener{

    lateinit var sensorManager: SensorManager
    lateinit var s: Sensor
    lateinit var textView: TextView
    lateinit var hView: HorizontalScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.infoXYZ)
        hView = findViewById(R.id.hView)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        s = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        if(s != null){
            Toast.makeText(this, "GYROSCOPE Sensor is found ☺", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "GYROSCOPE Sensor is not found!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_GYROSCOPE){
                var x = event.values[0].toInt()
                var y = event.values[1].toInt()
                var z = event.values[2].toInt()

                textView.setText("X: "+x+"\nY: "+y+"\nZ: "+z)

                hView.smoothScrollTo(hView.scrollX + (y*150),0)
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