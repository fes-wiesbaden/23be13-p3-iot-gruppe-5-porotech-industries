package com.porotech_industries.porocar.utils.wheels;

import com.porotech_industries.porocar.utils.logger.PoroLogger;

public class PoroWheelCommandHandler {
    private final PoroWheelController wheelController;
    private final L298NController leftController;
    private final L298NController rightController;

    public PoroWheelCommandHandler(PoroWheelController wheelController, L298NController leftController, L298NController rightController) {
        this.wheelController = wheelController;
        this.leftController = leftController;
        this.rightController = rightController;
    }

    public void handleMoveCommand(String payload) {
        try {
            String[] parts = payload.trim().split(",");
            if (parts.length != 3) {
                PoroLogger.error("MQTT_MOVE", "Invalid move command format: " + payload);
                return;
            }

            String directionStr = parts[0].toUpperCase();
            String rotationStr = parts[1].toUpperCase();
            int timeout = Integer.parseInt(parts[2]);

            PoroWheelController.Direction direction = PoroWheelController.Direction.valueOf(directionStr);
            PoroWheelController.Rotation rotation = PoroWheelController.Rotation.valueOf(rotationStr);

            wheelController.move(direction, rotation);
            PoroLogger.info("WHEEL", "Move command received: " + direction + ", " + rotation);
        } catch (Exception e) {
            PoroLogger.error("MQTT_MOVE", "Error parsing move command: " + e.getMessage());
        }
    }

    public void handlePowerCommand(String payload) {
        try {
            String[] parts = payload.trim().split("\\|");
            if (parts.length != 4) {
                PoroLogger.error("MQTT_POWER", "Invalid power command format: " + payload);
                return;
            }

            float left1 = Float.parseFloat(parts[0]);
            float left2 = Float.parseFloat(parts[1]);
            float right1 = Float.parseFloat(parts[2]);
            float right2 = Float.parseFloat(parts[3]);

            leftController.setMotor1Speed(left1);
            leftController.setMotor2Speed(left2);
            rightController.setMotor1Speed(right1);
            rightController.setMotor2Speed(right2);

            PoroLogger.info("MOTOR", "Set speeds to L1=" + left1 + ", L2=" + left2 + ", R1=" + right1 + ", R2=" + right2);
        } catch (Exception e) {
            PoroLogger.error("MQTT_POWER", "Error parsing power command: " + e.getMessage());
        }
    }
}
