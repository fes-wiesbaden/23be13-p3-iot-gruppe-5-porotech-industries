package com.porotech_industries.porocar.utils.wheels;

import com.diozero.api.DigitalInputOutputDevice;
import com.diozero.api.DigitalOutputDevice;
import com.diozero.api.PwmOutputDevice;

import javax.swing.*;

public class L298NController {
    private final DigitalOutputDevice In1;
    private final DigitalOutputDevice In2;
    private final DigitalOutputDevice In3;
    private final DigitalOutputDevice In4;

    private final PwmOutputDevice Ena;
    private final PwmOutputDevice Enb;

    public enum MotorCommand {
        FORWARD,
        STOP,
        BACKWARD
    }

    public L298NController(int in1,int in2, int in3, int in4, int enaPin, int enbPin) {
        In1 = new DigitalOutputDevice(in1);
        In2 = new DigitalOutputDevice(in2);
        In3 = new DigitalOutputDevice(in3);
        In4 = new DigitalOutputDevice(in4);
        Ena = new PwmOutputDevice(enaPin, 50);
        Enb = new PwmOutputDevice(enbPin, 50);
    }

    public void controlMotor1(MotorCommand command) {
        setPins(command, In1, In2);
    }

    public void controlMotor2(MotorCommand command) {
        setPins(command, In3, In4);
    }

    public void setMotor1Speed(float speed) {
        Ena.setValue(clamp(speed));
    }

    public void setMotor2Speed(float speed) {
        Enb.setValue(clamp(speed));
    }

    private float clamp(float val) {
        return Math.max(0f, Math.min(1f, val));
    }

    private void setPins(MotorCommand command, DigitalOutputDevice pin1, DigitalOutputDevice pin2) {
        if (command == MotorCommand.FORWARD) {
            pin1.on();
            pin2.off();
        } else if (command == MotorCommand.STOP) {
            pin1.off();
            pin2.off();
        } else {
            pin2.on();
            pin1.off();
        }
    }
}
