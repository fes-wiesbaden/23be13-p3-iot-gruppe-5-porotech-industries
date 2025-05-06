package com.porotech_industries.porocar;

/**
 * Hello world!
 */
import com.porotech_industries.porocar.datatransfer.mqtt.*;
import org.eclipse.paho.client.mqttv3.MqttException;

public class App {
    public static void main(String[] args) throws MqttException, InterruptedException {
        System.out.println("Hello World!");
	String broker = "tcp://10.93.133.78:1883";
	String TOPIC = "test/1";

	PoroMqttClient subscriber = new PoroMqttClient(broker);
	subscriber.connect();

	PoroMqttClient publisher = new PoroMqttClient(broker);
	publisher.connect();

	PoroMqttClient publisher2 = new PoroMqttClient(broker);
	publisher2.connect();

	subscriber.subscribe(TOPIC, 1, (topic, message) -> {
	    System.out.println("\nReceived message:");
	    System.out.println("Topic: " + topic);
	    System.out.println("Content: " + new String(message.getPayload()));
	    System.out.println("QoS: " + message.getQos());
	});    

	String testMessage = "Hello from PoroCar!";
	publisher.publish(TOPIC, testMessage.getBytes(), 1, false);

	Thread.sleep(2000);


	publisher.publish(TOPIC, testMessage.getBytes(), 1, false);

	publisher.disconnect();

	publisher2.publish(TOPIC, "im still here".getBytes(), 1, false);
	Thread.sleep(2000);

	subscriber.disconnect();
	publisher2.disconnect();
    }
}
