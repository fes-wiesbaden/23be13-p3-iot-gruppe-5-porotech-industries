package com.porotech.backend.controller;

import com.porotech.backend.service.SensorDataService;
import com.porotech.backend.utils.sensors.SensorData;
import org.springframework.boot.actuate.endpoint.web.annotation.RestControllerEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    private final SensorDataService sensorDataService;

    public SensorController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @GetMapping("/temperature")
    public SensorData<?> getTemperature() {
        return sensorDataService.getSensor("temperature");
    }

    @GetMapping("/all")
    public Map<String, SensorData<?>> getAllSensorData() {
        return sensorDataService.getAll();
    }

}
