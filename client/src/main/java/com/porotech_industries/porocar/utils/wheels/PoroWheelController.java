package com.porotech_industries.porocar.utils.wheels;

public class PoroWheelController {

    public enum Direction {
        FORWARD,
        BACKWARD,
        STOP
    }

    public enum Rotation {
        LEFT,
        RIGHT,
        NONE
    }

    private final L298NController leftController;
    private final L298NController rightController;

    public PoroWheelController(L298NController leftController, L298NController rightController) {
        this.leftController = leftController;
        this.rightController = rightController;
    }

    public void move(Direction direction, Rotation rotation) {
        L298NController.MotorCommand leftFront, leftBack, rightFront, rightBack;

        switch (direction) {
            case FORWARD:
                leftFront = leftBack = L298NController.MotorCommand.FORWARD;
                rightFront = rightBack = L298NController.MotorCommand.FORWARD;
                break;
            case BACKWARD:
                leftFront = leftBack = L298NController.MotorCommand.BACKWARD;
                rightFront = rightBack = L298NController.MotorCommand.BACKWARD;
                break;
            case STOP:
            default:
                leftFront = leftBack = rightFront = rightBack = L298NController.MotorCommand.STOP;
                break;
        }

        switch (rotation) {
            case LEFT:
                leftFront = leftBack = (direction == Direction.FORWARD) ? L298NController.MotorCommand.STOP : L298NController.MotorCommand.BACKWARD;
                break;
            case RIGHT:
                rightFront = rightBack = (direction == Direction.FORWARD) ? L298NController.MotorCommand.STOP : L298NController.MotorCommand.BACKWARD;
                break;
            case NONE:
            default:
                break;
        }

        setMotorCommands(leftFront, leftBack, rightFront, rightBack);
    }

    private void setMotorCommands(
            L298NController.MotorCommand leftFront,
            L298NController.MotorCommand leftBack,
            L298NController.MotorCommand rightFront,
            L298NController.MotorCommand rightBack
    ) {
        leftController.controlMotor1(leftFront);
        leftController.controlMotor2(leftBack);
        rightController.controlMotor1(rightFront);
        rightController.controlMotor2(rightBack);


    }
}
