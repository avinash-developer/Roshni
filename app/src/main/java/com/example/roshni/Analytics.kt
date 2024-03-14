package com.example.roshni

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONException
import org.json.JSONObject
import kotlin.random.Random

class Analytics : AppCompatActivity() {

    private lateinit var mqttManager: AWSIotMqttManager
    private lateinit var pir1TextView: TextView
    private lateinit var pir2TextView: TextView
    private lateinit var ldrtextView:TextView
    private lateinit var firetextView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        // Initialize AWS IoT MQTT Manager
        val credentialsProvider = CognitoCachingCredentialsProvider(
            applicationContext,
            "eu-north-1:7134206c-83d7-4816-a545-125d08f38914",
            Regions.EU_NORTH_1
        )

        mqttManager = AWSIotMqttManager("ESP32_LED", "a1tme0huijwr92-ats.iot.eu-north-1.amazonaws.com")



        pir1TextView = findViewById(R.id.pir1TextView)
        pir2TextView = findViewById(R.id.pir2TextView)
        ldrtextView=findViewById(R.id.ldrtextView)
        firetextView=findViewById(R.id.firetextView)

        // Configure charts
        //configureChart(temperatureChart)
      //  configureChart(humidityChart)

        // Establish the MQTT connection
        try {
            mqttManager.connect(credentialsProvider, AWSIotMqttClientStatusCallback { status, throwable ->
                if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected) {
                    subscribeToTopic("esp32/pub")
                }
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error connecting to AWS IoT: ${e.message}")
        }
    }

    private fun subscribeToTopic(topic: String) {
        val qos = AWSIotMqttQos.QOS0 // Choose QoS level: QOS0, QOS1

        mqttManager.subscribeToTopic(topic, qos, object : AWSIotMqttNewMessageCallback {
            override fun onMessageArrived(topic: String?, data: ByteArray?) {
                val message = data?.toString(Charsets.UTF_8)
                Log.d("MainActivity", "Received message: $message")

                try {
                    val jsonObject = JSONObject(message)
                    val pir1 = jsonObject.getInt("pirvalue")
                    val pir2 = jsonObject.getInt("pirvalue2")
                    val ldr =jsonObject.getInt("ldrvalue")
                    val fire=jsonObject.getInt("fire")

                    // Update UI with temperature and humidity values
                    runOnUiThread {
                        updateUI(pir1, pir2,ldr,fire)
                    }
                } catch (e: JSONException) {
                    Log.e("MainActivity", "Error parsing JSON: ${e.message}")
                }
            }
        })
    }

    private fun updateUI(pir1: Int, pir2: Int,ldr:Int,fire:Int) {
        pir1TextView.text = "PIR 1 Status: $pir1 "
        pir2TextView.text = "PIR 2 Status: $pir2 "
        if(ldr==1)
        {
            ldrtextView.text="Status LDR: Night Mode Activated"
        }
        else if (ldr==0)
        {
            ldrtextView.text="Status LDR: Day Mode Activated"
        }
        if(fire==0)
        {
            firetextView.text="Fire Status: Fire Detected in System"
        }
        else if(fire==1)
        {
            firetextView.text="Fire Status: System is Safe"
        }
    }

//    private fun configureChart(chart: LineChart) {
//        chart.description.isEnabled = false
//        chart.setTouchEnabled(false)
//        chart.setDrawGridBackground(false)
//        chart.axisLeft.setDrawGridLines(false)
//        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        chart.xAxis.setDrawGridLines(false)
//        chart.axisRight.isEnabled = false
//        chart.legend.isEnabled = false
//        chart.data = LineData(LineDataSet(null, "Data"))
//
    override fun onDestroy() {
        super.onDestroy()
        mqttManager.disconnect()
    }
}
