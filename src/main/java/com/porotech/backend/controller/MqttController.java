package com.porotech.backend.controller;

import com.porotech.backend.datatransfer.mqtt.PoroMqttClient;
import com.porotech.backend.utils.parser.LidarParser;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttController {
    private final PoroMqttClient mqttClient;
    private final LidarParser lidarParser = new LidarParser();

    public MqttController(PoroMqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @PostConstruct
    public void start() throws MqttException {
        mqttClient.connect();
        System.out.println("Connected to MQTT broker");
        mqttClient.subscribe("porocar/raspberry/ld06", 1, (topic, message) -> {
            System.out.println("Received Message");
            try {
                byte[] payload = message.getPayload();
                System.out.println("Payload length: " + payload.length);
                List<LidarParser.LidarMeasurement> measurements = lidarParser.parseLidar(message.getPayload());

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        });
    }
}
