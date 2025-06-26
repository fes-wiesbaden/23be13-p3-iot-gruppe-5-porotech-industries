package com.porotech_industries.porocar.datatransfer.mqtt;

import com.porotech_industries.porocar.utils.logger.PoroLogger;
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
        PoroLogger.info("MQTT Connection", "Connected to broker: %s", broker);
    }

    public void disconnect() throws MqttException {
        client.disconnect();
        PoroLogger.info("MQTT Connection", "Disconnected from broker: %s", broker);
    }

    public void subscribe(String topic, int qos, IMqttMessageListener listener) throws MqttException {
        client.subscribe(topic, qos, listener);
        PoroLogger.info("MQTT Subscriber", "Subscribed to Topic: %s, with Qos: %d", topic, qos);
    }

    public void publish(String topic, byte[] data, int qos, boolean retained) throws MqttException {
        MqttMessage message = new MqttMessage(data);
        message.setQos(qos);
        message.setRetained(retained);
        client.publish(topic, message);
      
        //PoroLogger.info("MQTT Publisher", "Published to Topic: %s, QoS: %d, Retained: %b", topic, qos, retained);

    }

    public void setCallback(MqttCallback callback) {
        client.setCallback(callback);
    }
}
