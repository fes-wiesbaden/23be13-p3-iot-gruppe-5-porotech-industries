package com.porotech.backend.service;

import com.porotech.backend.utils.sensors.SensorData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SensorDataService {

    private final Map<String, SensorData<?>> sensors = new ConcurrentHashMap<>();

    public void updateSensor(String key, Object value) {
        sensors.put(key, new SensorData<>(value));
    }

    public SensorData<?> getSensor(String key) {
        return sensors.get(key);
    }

    public Map<String, SensorData<?>> getAll() {
        return sensors;
    }
}