package com.porotech.backend.utils.sensors;

public class SensorData<T> {
    private T value;
    private long timestamp;

    public SensorData(T value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public T getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setValue(T value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }
}