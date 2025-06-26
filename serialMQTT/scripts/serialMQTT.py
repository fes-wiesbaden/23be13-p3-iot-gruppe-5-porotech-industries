import serial
import paho.mqtt.client as mqtt
import time
from enum import Enum

SERIAL_PORT = '/dev/ttyAMA0'  # or '/dev/ttyAMA0' depending on your config
BAUD_RATE = 230400

MEASUREMENTS_PER_PLOT = 480
PACKET_LENGTH = 47

# MQTT configuration
MQTT_BROKER = '10.93.136.118'
MQTT_PORT = 1883
MQTT_TOPIC = 'porocar/raspberry/ld06'


State = Enum("State", ["SYNC0", "SYNC1", "SYNC2", "LOCKED", "SEND_PACKET"])

if __name__ == "__main__":
    # Initialize serial connection
    ser = serial.Serial(SERIAL_PORT, BAUD_RATE, timeout=1)

    # Initialize MQTT client
    client = mqtt.Client()
    client.connect(MQTT_BROKER, MQTT_PORT, 60)
    # Set up initial state
    data = b''
    state = State.SYNC0
    print("state0")

    print("Starting to send over MQTT bridge...")

    try:
        while True:
            # find first header byte
            if state == State.SYNC0:
                data = b''
                byte = ser.read()
                if byte == b'\x54':
                    data = byte
                    state = State.SYNC1
                # find second header byte
                # can be treated as constant since packet length is fixed

            elif state == State.SYNC1:
                byte = ser.read()
                if byte == b'\x2C':
                    data += byte
                    state = State.SYNC2
                else:
                    state = State.SYNC0

                # read reminder of the packet (PACKET_LENGTH - 2)
            elif state == State.SYNC2:
                chunk = ser.read(PACKET_LENGTH -2)
                if len(chunk) < PACKET_LENGTH - 2:
                    state = State.SYNC0
                    continue
                data += chunk
                
                client.publish(MQTT_TOPIC, payload=data)
                data = b''
                state = State.SYNC0

    except KeyboardInterrupt:
        print("Exiting...")

    finally:
        ser.close()
        client.disconnect()
