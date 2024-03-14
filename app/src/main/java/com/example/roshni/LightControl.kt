package com.example.roshni

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Switch
import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager
import com.amazonaws.mobileconnectors.iot.AWSIotMqttNewMessageCallback
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos
import com.amazonaws.regions.Regions


class LightControl : AppCompatActivity() {
    private lateinit var mqttManager: AWSIotMqttManager;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light_control)

        // Initialize AWS IoT MQTT Manager
        val credentialsProvider = CognitoCachingCredentialsProvider(
            applicationContext,
            "eu-north-1:7134206c-83d7-4816-a545-125d08f38914",
            Regions.EU_NORTH_1
        )

        mqttManager = AWSIotMqttManager("ESP32_LED", "a1tme0huijwr92-ats.iot.eu-north-1.amazonaws.com")

        // Establish the MQTT connection
        try {
            mqttManager.connect(credentialsProvider, AWSIotMqttClientStatusCallback { status, throwable ->
                if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Connected) {

                } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.Reconnecting) {

                } else if (status == AWSIotMqttClientStatusCallback.AWSIotMqttClientStatus.ConnectionLost) {

                }
            })
        } catch (e: Exception) {
            Log.e("MainActivity", "Error connecting to AWS IoT: ${e.message}")
        }

        val switch_led1 = findViewById<View>(R.id.led1) as Switch
        val switch_led2 = findViewById<View>(R.id.led2) as Switch
        val switch_led3 = findViewById<View>(R.id.led3) as Switch
        val switch_led4 = findViewById<View>(R.id.led4) as Switch
        val switch_led5 = findViewById<View>(R.id.led5) as Switch
        val switch_led6 = findViewById<View>(R.id.led6) as Switch
        val switch_led7 = findViewById<View>(R.id.led7) as Switch
        val switch_led8 = findViewById<View>(R.id.led8) as Switch
        val switch_led9 = findViewById<View>(R.id.led9) as Switch
        val switch_led10 = findViewById<View>(R.id.led10) as Switch
        switch_led1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"11\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"10\"\n" +
                        "}")
            }
        }
        switch_led2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"21\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"20\"\n" +
                        "}")
            }
        }
        switch_led3.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"31\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"30\"\n" +
                        "}")
            }
        }
        switch_led4.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"41\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"40\"\n" +
                        "}")
            }
        }
        switch_led5.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"51\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"50\"\n" +
                        "}")
            }
        }
        switch_led6.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"61\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"60\"\n" +
                        "}")
            }
        }
        switch_led7.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"71\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"70\"\n" +
                        "}")
            }
        }
        switch_led8.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"81\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"80\"\n" +
                        "}")
            }
        }
        switch_led9.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"91\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"90\"\n" +
                        "}")
            }
        }
        switch_led10.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                publishMessage("{\n" +
                        "  \"message\": \"101\"\n" +
                        "}")
            } else {
                publishMessage("{\n" +
                        "  \"message\": \"100\"\n" +
                        "}")
            }
        }
    }
    private fun publishMessage(message: String) {
        val topic = "led" // Replace with your desired topic

        try {
            mqttManager.publishString(message, topic, AWSIotMqttQos.QOS0)
            Log.d("MainActivity", "Published message: $message")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error publishing message: ${e.message}")
        }
    }

    private fun subscribeToTopic(topic: String) {
        val qos = AWSIotMqttQos.QOS0 // Choose QoS level: QOS0, QOS1

        mqttManager.subscribeToTopic(topic, qos, object : AWSIotMqttNewMessageCallback {
            override fun onMessageArrived(topic: String?, data: ByteArray?) {
                val message = data?.toString(Charsets.UTF_8)
                Log.d("MainActivity", "Received message: $message")
                // Handle received message
            }
        })
    }
}