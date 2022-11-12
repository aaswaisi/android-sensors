package com.example.orientationsensorkotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    var AMSensor: Sensor? = null
    var MFSensor: Sensor? = null
    var rotationValues = FloatArray(9)
    var AMValues = FloatArray(3)
    var MFValues = FloatArray(3)
    var finalValues = FloatArray(3)
    var textView: TextView? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.infoCompass)
        imageView = findViewById(R.id.imageCompass)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        AMSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        MFSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if (AMSensor != null && MFSensor != null) {
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sensor not found!!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            /*AMValues[0] = sensorEvent.values[0];
            AMValues[1] = sensorEvent.values[1];
            AMValues[2] = sensorEvent.values[2];*/
            AMValues = sensorEvent.values
        } else if (sensorEvent.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            MFValues = sensorEvent.values
        }

        SensorManager.getRotationMatrix(rotationValues, null, AMValues, MFValues)
        SensorManager.getOrientation(rotationValues, finalValues)

        var newValue = Math.toDegrees(finalValues[0].toDouble()).toInt()

        if (newValue < 0) {
            println("!!!: " + (newValue + 360))
            textView!!.text = "" + (newValue + 360)
            imageView!!.rotation = (newValue*-1).toFloat()
        } else {
            println("!!!: $newValue")
            textView!!.text = "" + newValue
            imageView!!.rotation = (newValue*-1).toFloat()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(
            this,
            AMSensor, SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager!!.registerListener(
            this,
            MFSensor, SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }
}