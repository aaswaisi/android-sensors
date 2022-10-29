package com.example.magneticfieldsensorkotlin

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {

    var sensorManager: SensorManager? = null
    var s: Sensor? = null

    var textView_magnetic: TextView? = null
    var textView_axis: TextView? = null

    var view: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView_magnetic = findViewById(R.id.info_magnetic)
        textView_axis = findViewById(R.id.info_axis)

        view = findViewById(R.id.magneticPower)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        s = sensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if (s != null) {
            Toast.makeText(this, "Sensor is found ☺", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sensor is not found !!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        if (sensorEvent.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            val x = Math.abs(sensorEvent.values[0]).toInt()
            val y = Math.abs(sensorEvent.values[1]).toInt()
            val z = Math.abs(sensorEvent.values[2]).toInt()

            textView_magnetic!!.text =
            "X:"+ x+"\nY: "+y+"\nZ: "+z+"\nMax Val: "+sensorEvent.sensor.maximumRange

            if (x > y && x > z) {
                textView_axis!!.text = "X"
            } else if (y > x && y > z) {
                textView_axis!!.text = "Y"
            } else {
                textView_axis!!.text = "Z"
            }

            if (x > 1000 || y > 1000 || z > 1000) {
                view!!.setBackgroundColor(Color.RED)
            } else if (x > 500 || y > 500 || z > 500) {
                view!!.setBackgroundColor(Color.BLUE)
            } else if (x > 100 || y > 100 || z > 100) {
                view!!.setBackgroundColor(Color.GREEN)
            } else {
                view!!.setBackgroundColor(Color.BLACK)
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