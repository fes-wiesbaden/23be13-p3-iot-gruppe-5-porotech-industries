package com.porotech.backend.controller;

import com.porotech.backend.datatransfer.mqtt.PoroMqttClient;
import com.porotech.backend.map.CarPosition;
import com.porotech.backend.map.Map3D;
import com.porotech.backend.service.SensorDataService;
import com.porotech.backend.utils.parser.LidarParser;
import com.porotech.backend.utils.sensors.SensorData;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MqttController {

    private final PoroMqttClient mqttClient;
    private final LidarParser lidarParser = new LidarParser();
    private final Map3D map3D;
    private final CarPosition carPosition;
    private final SensorDataService sensorDataService;

    public MqttController(PoroMqttClient mqttClient, Map3D map3D, CarPosition carPosition, SensorDataService sensorDataService) {
        this.mqttClient = mqttClient;
        this.map3D = map3D;
        this.carPosition = carPosition;
        this.sensorDataService = sensorDataService;
    }

    @PostConstruct
    public void start() throws MqttException {
        mqttClient.connect();
        //System.out.println("Connected to MQTT broker");
        mqttClient.subscribe("porocar/arduino/#", 1, (topic, message) -> {
            String payload = new String(message.getPayload());

            switch (topic) {
                case "porocar/arduino/sensors/mpu6050/gyro":
                    long now = System.currentTimeMillis();
                    long delta = now - carPosition.getLastUpdateTime();
                    if (delta > 0 && delta < 1000) {
                        carPosition.updatePositionFromVelocity(delta / 1000.0);
                    }

                    String[] gyro = payload.split(",");
                    carPosition.setVelocityX(Double.parseDouble(gyro[0]));
                    carPosition.setVelocityY(Double.parseDouble(gyro[1]));
                    carPosition.setVelocityZ(Double.parseDouble(gyro[2]));

                    carPosition.setLastUpdateTime(now);
                    break;

                case "porocar/arduino/sensors/mpu6050/angle":
                    // pitch = X-Achse, roll = Z-Achse
                    //String[] angle = payload.split(",");
                    //carPosition.setPitch(Double.parseDouble(angle[1])); // getAngleX = Pitch
                    //carPosition.setRoll(Double.parseDouble(angle[2]));  // getAngleZ = Roll
                    break;

                case "porocar/arduino/sensors/gy-271/azimuth":
                    //carPosition.setYaw(Double.parseDouble(payload));
                    break;

                case "porocar/arduino/sensors/dht11/temperature":
                    double temp = Double.parseDouble(payload);
                    sensorDataService.updateSensor("temperature", temp);
                    break;

                case "porocar/arduino/sensors/gy-271/position":
                    String[] pos = payload.split(",");
                    double xCm = Double.parseDouble(pos[0]) / 10.0;
                    double yCm = Double.parseDouble(pos[1]) / 10.0;
                    double zCm = Double.parseDouble(pos[2]) / 10.0;
                    carPosition.setX(xCm + 500);
                    carPosition.setY(yCm + 500);
                    carPosition.setZ(zCm);
                    break;
            }

        });


        mqttClient.subscribe("porocar/raspberry/ld06", 1, (topic, message) -> {
            //System.out.println("Received Message");
            try {
                byte[] payload = message.getPayload();
                //System.out.println("Payload length: " + payload.length);
                LidarParser.LidarMeasurement measurements = lidarParser.parseLidar(message.getPayload());

                map3D.addLidarPoint(measurements);

            } catch (Exception e) {
                //System.out.println("Error: " + e.getMessage());
            }
        });
    }
}
