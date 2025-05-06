package com.porotech_industries.porocar.datatransfer.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class PoroMqttClient {
    private final MqttClient client;
    private final String broker;

    public PoroMqttClient(String broker) throws MqttException {
        this.broker = broker;
        String clientId = MqttClient.generateClientId();
        this.client = new MqttClient(broker, clientId);
    }

    public void connect() throws MqttException {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        client.connect(connOpts);
        System.out.println("Connected to broker " + broker);
    }

    public void disconnect() throws MqttException {
        client.disconnect();
        System.out.println("Disconnected from broker " + broker);
    }

    public void subscribe(String topic, int qos, IMqttMessageListener listener) throws MqttException {
        client.subscribe(topic, qos, listener);
        System.out.println("Subscribed to topic " + topic + " with QoS " + qos);
    }

    public void publish(String topic, byte[] data, int qos, boolean retained) throws MqttException {
        MqttMessage message = new MqttMessage(data);
        message.setQos(qos);
        message.setRetained(retained);
        client.publish(topic, message);
        System.out.println("Published to topic " + topic + " with QoS " + qos + " retained=" + retained);
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }
}
