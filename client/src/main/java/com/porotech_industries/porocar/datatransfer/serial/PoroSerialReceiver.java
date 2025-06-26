package com.porotech_industries.porocar.datatransfer.serial;

import com.fazecast.jSerialComm.*;
import com.porotech_industries.porocar.utils.logger.*;

import java.io.IOException;
import java.util.Arrays;

public class PoroSerialReceiver {
    private SerialPort serialPort;

    public void listSerialPorts() {
        PoroLogger.info("PoroSerialReceiver", "Listing Serial Ports");

        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length == 0) {
            PoroLogger.error("PoroSerialReceiver", "No serial ports found.");
        } else {
            PoroLogger.info("PoroSerialReceiver", "Available serial ports:");
            for (SerialPort port : ports) {
                PoroLogger.info("PoroSerialReceiver", " - %s ( %s )", port.getSystemPortName(), port.getDescriptivePortName());
            }
        }
    }


    public boolean connect(String portName, int baudRate, int timeout) {
        try {
            serialPort = SerialPort.getCommPort(portName);
            serialPort.setBaudRate(baudRate);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, timeout, 0);

            if (serialPort.openPort()) {
                PoroLogger.info("PoroSerialReceiver", "Successfully connected to port: %s", portName);
                return true;
            } else {
                PoroLogger.error("PoroSerialReceiver", "Failed to open port: %s", portName);
                return false;
            }
        } catch (SerialPortInvalidPortException e) {
            PoroLogger.error("PoroSerialReceiver", "Invalid port: %s. Exception: %s", portName, e.getMessage());
            return false;
        } catch (Exception e) {
            PoroLogger.error("PoroSerialReceiver", "Unexpected error while connecting to port: %s. Exception: %s", portName, e.getMessage());
            return false;
        }
    }



    public String receiveData(int timeout) {
        if (serialPort == null || !serialPort.isOpen()) {
            PoroLogger.error("PoroSerialReceiver", "No open connection to serial port.");
            System.exit(1);
        }

        byte[] buffer = new byte[1024];

        //PoroLogger.info("PoroSerialReceiver", "Attempting to receive data with a timeout of %d ms...", timeout);

        StringBuilder lineBuffer = new StringBuilder();
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            int bytesRead = serialPort.readBytes(buffer, buffer.length);
            if (bytesRead > 0) {
                for (int i = 0; i < bytesRead; i++) {
                    char ch = (char) buffer[i];
                    lineBuffer.append(ch);
                    if (ch == '\n') {
                        String line = lineBuffer.toString().trim();
                        //PoroLogger.info("PoroSerialReceiver", "Received line: %s", line);

                        return line;
                    }
                }
            }
        }

        PoroLogger.warn("PoroSerialReceiver", "Timeout reached without receiving complete data.");
        return "";
    }


    public void close() {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
            PoroLogger.info("PoroSerialReceiver", "Serial port closed.");
        }
    }
}