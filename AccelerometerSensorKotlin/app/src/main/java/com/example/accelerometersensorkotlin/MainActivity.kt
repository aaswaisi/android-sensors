package com.example.accelerometersensorkotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() , SensorEventListener{

    lateinit var textView: TextView
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         textView = findViewById(R.id.infoXYZ)
         imageView = findViewById(R.id.myImage)

        lateinit var sensorManager: SensorManager

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val mySensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in mySensors) {
            println("Sensor Name: "+sensor.name)
        }
        //////////////////////////////////////////////////
        var s = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if(s != null){
            println("Sensor is found ☺");
        }else{
            println("Sensor is not found!!!");
        }
        //////////////////////////////////////////////////
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            if(event.sensor.type == Sensor.TYPE_ACCELEROMETER){
                var df = DecimalFormat("0.0")
                var x = event.values[0]
                var y = event.values[1]
                var z = event.values[2]

                textView.setText("X: "+df.format(x)+"\nY: "+df.format(y)+"\nZ: "+df.format(z))

                imageView.setX(imageView.x - (x*3))
                imageView.setY(imageView.y + (y*3))
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //
    }
}