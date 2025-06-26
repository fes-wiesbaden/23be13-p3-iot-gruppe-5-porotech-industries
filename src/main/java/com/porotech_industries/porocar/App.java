package com.porotech_industries.porocar;

/**
 * Hello world!
 */
import com.porotech_industries.porocar.datatransfer.mqtt.*;
import com.porotech_industries.porocar.datatransfer.serial.*;
import com.porotech_industries.porocar.utils.parser.PoroArduinoParser;
import com.porotech_industries.porocar.utils.wheels.L298NController;
import com.porotech_industries.porocar.utils.wheels.PoroWheelCommandHandler;
import com.porotech_industries.porocar.utils.wheels.PoroWheelController;

import org.eclipse.paho.client.mqttv3.MqttException;
import com.porotech_industries.porocar.utils.logger.*;

import java.util.HashMap;
import java.util.Objects;

public class App {
    public static void main(String[] args) throws MqttException, InterruptedException {
		System.out.println("Hello World!");
		String broker = "tcp://10.93.136.118:1883";

		String TOPIC = "test/1";
		PoroLogger logger = new PoroLogger();
		PoroSerialReceiver serialReceiver = new PoroSerialReceiver();


		logger.enable();
		logger.setLogLevel(PoroLogger.LogLevel.INFO);

		System.setProperty("diozero.provider", "pigpio");
		//L298NController leftController = new L298NController(17, 27, 5, 6, 12, 13); // pwd currently buggy makes cpu go to 100% 
		//L298NController rightController = new L298NController(24, 25, 22, 23, 18, 19);

		//PoroWheelController wheelController = new PoroWheelController(leftController, rightController);

		PoroLogger.info("WHEEL", "DRiving forward");
		//wheelController.move(PoroWheelController.Direction.FORWARD, PoroWheelController.Rotation.NONE);
        //rightController.controlMotor1(L298NController.MotorCommand.FORWARD);
        //rightController.controlMotor2(L298NController.MotorCommand.FORWARD);

		Thread.sleep(1000);

        //rightController.setMotor1Speed(0.0f);
        //rightController.setMotor2Speed(0.0f);

        //leftController.setMotor1Speed(0.0f);
        //leftController.setMotor2Speed(1.0f);

        //rightController.controlMotor1(L298NController.MotorCommand.FORWARD);
        //rightController.controlMotor2(L298NController.MotorCommand.FORWARD);

        //rightController.setMotor1Speed(0.0f);
        //rightController.setMotor2Speed(0.0f);

        //wheelController.move(PoroWheelController.Direction.BACKWARD, PoroWheelController.Rotation.NONE);

        Thread.sleep(2000);

        //rightController.controlMotor1(L298NController.MotorCommand.STOP);
        //rightController.controlMotor2(L298NController.MotorCommand.STOP);
		//wheelController.move(PoroWheelController.Direction.STOP, PoroWheelController.Rotation.NONE);

		serialReceiver.listSerialPorts();

		if (!serialReceiver.connect("ttyACM0", 115200, 100)) {
			PoroLogger.error("SerialReceiver", "FAiled to connect, dying now");
			//System.exit(69420);
		} else {
			PoroLogger.info("SerialReceiver", "Connected to SerialPort `ttyACM0`");
		}

		//serialReceiver.receiveData(1000);

		PoroLogger.info("Test", "this is a test msg");

		PoroMqttClient subscriber = new PoroMqttClient(broker);
		subscriber.connect();

		PoroMqttClient publisher = new PoroMqttClient(broker);
		publisher.connect();

		//PoroMqttClient publisher2 = new PoroMqttClient(broker);
		//publisher2.connect();

        //PoroWheelCommandHandler cmdHandler = new PoroWheelCommandHandler(wheelController, leftController, rightController);


        subscriber.subscribe("porocar/backend/wheels/move", 1, (topic, message) -> {
            String payload = new String(message.getPayload());
            //cmdHandler.handleMoveCommand(payload);
        });

        subscriber.subscribe("porocar/backend/wheels/power", 1, (topic, message) -> {
            String payload = new String(message.getPayload());
            //cmdHandler.handlePowerCommand(payload);
        });
		String testMessage = "Hello from PoroCar!";
		publisher.publish(TOPIC, testMessage.getBytes(), 1, false);

		//Thread.sleep(2000);


		publisher.publish(TOPIC, testMessage.getBytes(), 1, false);

		//publisher.disconnect();

		//publisher2.publish("PoroCar/Stream/LD06/send", "im still here".getBytes(), 1, false);
		//Thread.sleep(2000);

		//subscriber.disconnect();
		//publisher2.disconnect();


		while (true) {
			String data = serialReceiver.receiveData(1000);
			if (Objects.equals(data, "")) {
				continue;
			}
			HashMap<String, String> parsedData = PoroArduinoParser.parseArduinoData(data);

			for (String topic : parsedData.keySet()) {
				//PoroLogger.info("Parsed Arduino Data", "key: %s, value %s", topic, parsedData.get(topic));
				publisher.publish(topic, parsedData.get(topic).getBytes(), 1, false);
			}
			//System.exit(-1);
			//System.out.println("send datastream");

		}
	}
		//serialReceiver.close();
}
