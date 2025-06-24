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

import java.io.Console;
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

                    sensorDataService.updateSensor("gyro_x", Double.parseDouble(gyro[0]));
                    sensorDataService.updateSensor("gyro_y", Double.parseDouble(gyro[1]));
                    sensorDataService.updateSensor("gyro_z", Double.parseDouble(gyro[2]));

                    carPosition.setLastUpdateTime(now);
                    break;

                case "porocar/arduino/sensors/mpu6050/angle":
                    // pitch = X-Achse, roll = Z-Achse
                    String[] angle = payload.split(",");
                    double pitch = (Double.parseDouble(angle[1])); // getAngleX = Pitch
                    double roll = (Double.parseDouble(angle[2]));  // getAngleZ = Roll
                    sensorDataService.updateSensor("pitch", pitch);
                    sensorDataService.updateSensor("roll", roll);
                    break;

                case "porocar/arduino/sensors/gy-271/azimuth":
                    double yaw = Double.parseDouble(payload);
                    carPosition.setYaw(yaw);
                    sensorDataService.updateSensor("yaw", yaw);
                    break;

                case "porocar/arduino/sensors/dht11/temperature":
                    double temp = Double.parseDouble(payload);
                    sensorDataService.updateSensor("temperature", temp);
                    break;

                case "porocar/arduino/sensors/gy-271/position":
                    String[] pos = payload.split(",");
                    int xCm = Integer.parseInt(pos[0]) / 10;
                    int yCm = Integer.parseInt(pos[1]) / 10;
                    int zCm = Integer.parseInt(pos[2]) / 10;
                    carPosition.setX(xCm + 500);
                    carPosition.setY(yCm + 500);
                    carPosition.setZ(zCm);

                    sensorDataService.updateSensor("position_x", carPosition.getX());
                    sensorDataService.updateSensor("position_y", carPosition.getY());
                    sensorDataService.updateSensor("position_z", carPosition.getZ());
                    break;
                case "porocar/arduino/sensors/gy-271/direction":
                    String direction = payload.trim();
                    sensorDataService.updateSensor("direction", direction);
                    break;
                    
                case "porocar/arduino/sensors/mpu6050/acceleration/angle":
                    String[] accelAngles = payload.split(",");
                    if (accelAngles.length >= 2) {
                        double accelAngleX = Double.parseDouble(accelAngles[0]);
                        double accelAngleY = Double.parseDouble(accelAngles[1]);
                        sensorDataService.updateSensor("accel_angle_x", accelAngleX);
                        sensorDataService.updateSensor("accel_angle_y", accelAngleY);
                    }
                    break;
                    
                case "porocar/arduino/sensors/f249/br":
                    double brValue = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("speed_br", brValue);
                    break;
                    
                case "porocar/arduino/sensors/f249/bl":
                    double blValue = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("speed_bl", blValue);
                    break;
                    
                case "porocar/arduino/sensors/dht11/humidity":
                    double humidity = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("humidity", humidity);
                    break;
                    
                case "porocar/arduino/sensors/f249/fr":
                    double frValue = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("speed_fr", frValue);
                    break;
                    
                case "porocar/arduino/sensors/f249/fl":
                    double flValue = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("speed_fl", flValue);
                    break;
                    
                case "porocar/arduino/sensors/hc-sr04/front-left":
                    double frontLeftDistance = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("distance_front_left", frontLeftDistance);
                    
                    break;
                    
                case "porocar/arduino/sensors/hc-sr04/front-right":
                    double frontRightDistance = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("distance_front_right", frontRightDistance);
                    
                    break;
                    
                case "porocar/arduino/sensors/hc-sr04/back":
                    double backDistance = Double.parseDouble(payload.trim());
                    sensorDataService.updateSensor("distance_back", backDistance);
                    
                    break;
                    
                case "porocar/arduino/sensors/mpu6050/acceleration":
                    String[] accel = payload.split(",");
                    if (accel.length >= 3) {
                        double accelX = Double.parseDouble(accel[0]);
                        double accelY = Double.parseDouble(accel[1]);
                        double accelZ = Double.parseDouble(accel[2]);
                        
                        sensorDataService.updateSensor("accel_x", accelX);
                        sensorDataService.updateSensor("accel_y", accelY);
                        sensorDataService.updateSensor("accel_z", accelZ);
                        
                        double accelMagnitude = Math.sqrt(accelX*accelX + accelY*accelY + accelZ*accelZ);
                        sensorDataService.updateSensor("accel_magnitude", accelMagnitude);
                    }
                    break;
                default: {
                    System.out.println("case \"" + topic + "\":\n\n    break;");
                }
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
