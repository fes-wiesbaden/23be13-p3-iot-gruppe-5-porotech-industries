package com.porotech.backend.datatransfer.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.web.bind.annotation.RestController;

@Profile("!test")
@Component
public class PoroMqttClient {
    private final MqttClient client;
    private final String broker;

    public PoroMqttClient(@Value("${mqtt.broker}") String broker) throws MqttException {
        this.broker = broker;
        String clientId = MqttClient.generateClientId();
        this.client = new MqttClient(broker, clientId);
    }

    public void connect() throws MqttException {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        client.connect(connOpts);
    }

    public void disconnect() throws MqttException {
        client.disconnect();
    }

    public void subscribe(String topic, int qos, IMqttMessageListener listener) throws MqttException {
        client.subscribe(topic, qos, listener);
    }

    public void publish(String topic, byte[] data, int qos, boolean retained) throws MqttException {
        MqttMessage message = new MqttMessage(data);
        message.setQos(qos);
        message.setRetained(retained);
        client.publish(topic, message);
    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }
}
