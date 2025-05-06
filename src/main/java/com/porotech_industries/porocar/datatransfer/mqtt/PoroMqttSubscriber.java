package com.porotech_industries.porocar.datatransfer.mqtt;

import org.eclipse.paho.client.mqttv3.*;

public class PoroMqttSubscriber {
    private final MqttClient client;

    public  PoroMqttSubscriber(String broker) throws MqttException {
        String clientId = MqttClient.generateClientId();


        MqttConnectOptions connOpts = new MqttConnectOptions();

        this.client = new MqttClient(broker, clientId);

        client.connect(connOpts);
        System.out.println("Connecting to broker " + broker);
    }
}
