import serial
import paho.mqtt.client as mqtt
import time
import struct
import json
from enum import Enum

# Serial port configuration
SERIAL_PORT = '/dev/ttyAMA0'  # or '/dev/ttyAMA0' depending on your config
BAUD_RATE = 230400

# At the default rotation speed (~3600 deg/s) the system outputs about 
# 480 measurements in a full rotation. We want to plot at least this
# many in order to get a 360 degree plot

MEASUREMENTS_PER_PLOT = 480

# ----------------------------------------------------------------------
# Main Packet Format
# ----------------------------------------------------------------------
# All fields are little endian
# Header (1 byte) = 0x54
# Length (1 byte) = 0x2C (assumed to be constant)
# Speed (2 bytes) Rotation speed in degrees per second
# Start angle (2 bytes) divide by 100.0 to get angle in degrees
# Data Measurements (MEASUREMENT_LENGTH * 3 bytes)
#                   See "Format of each data measurement" below
# Stop angle (2 bytes) divide by 100.0 to get angle in degrees
# Timestamp (2 bytes) In milliseconds
# CRC (1 bytes) Poly: 0x4D, Initial Value: 0x00, Final Xor Value: 0x00
#               Input reflected: False, Result Reflected: False
#               http://www.sunshine2k.de/coding/javascript/crc/crc_js.html
# Format of each data measurement
# Distance (2 bytes) # In millimeters
# Confidence (1 byte)

# ----------------------------------------------------------------------
# Packet format constants
# ----------------------------------------------------------------------
# These do not vary
PACKET_LENGTH = 47
MEASUREMENT_LENGTH = 12 
MESSAGE_FORMAT = "<xBHH" + "HB" * MEASUREMENT_LENGTH + "HHB"

State = Enum("State", ["SYNC0", "SYNC1", "SYNC2", "LOCKED", "SEND_PACKET"])

# MQTT configuration
MQTT_BROKER = '192.168.178.161'
MQTT_PORT = 1883
MQTT_TOPIC = 'poroCar/Lidar'
# Enable Debug messages
PRINT_DEBUG = False

def parse_lidar_data(data):
    # Extract data
    length, speed, start_angle, *pos_data, stop_angle, timestamp, crc = \
        struct.unpack(MESSAGE_FORMAT, data)
    # Scale values
    start_angle = float(start_angle) / 100.0
    stop_angle = float(stop_angle) / 100.0
    # Unwrap angle if needed and calculate angle step size
    if stop_angle < start_angle:
        stop_angle += 360.0
    step_size = (stop_angle - start_angle) / (MEASUREMENT_LENGTH - 1)
    # Get the angle for each measurement in packet
    angle = [start_angle + step_size * i for i in range(0,MEASUREMENT_LENGTH)]
    distance = pos_data[0::2] # in millimeters
    confidence = pos_data[1::2]
    if PRINT_DEBUG:
        print(length, speed, start_angle, *pos_data, stop_angle, timestamp, crc)
    return list(zip(angle, distance, confidence))

if __name__ == "__main__":
    # Initialize serial connection
    ser = serial.Serial(SERIAL_PORT, BAUD_RATE, timeout=1)

    # Initialize MQTT client
    client = mqtt.Client()
    client.connect(MQTT_BROKER, MQTT_PORT, 60)
    # Set up initial state
    measurements = []
    measurementJSON = ""
    data = b''
    state = State.SYNC0
    print("state0")

    print("Starting to send over MQTT bridge...")

    try:
        while True:
            # find first header byte
            if state == State.SYNC0:
                data = b''
                measurements = []
                if ser.read() == b'\x54':
                    data = b'\x54'
                    state = State.SYNC1 
                # find second header byte
                # can be treated as constant since packet length is fixed
            elif state == State.SYNC1:
                if ser.read() == b'\x2C':
                    state = State.SYNC2
                    data += b'\x2C'
                else:
                    state = State.SYNC0
                # read reminder of the packet (PACKET_LENGTH - 2)
            elif state == State.SYNC2:
                data += ser.read(PACKET_LENGTH - 2)
                if len(data) != PACKET_LENGTH:
                    state = State.SYNC0
                    continue
                measurements += parse_lidar_data(data)
                state = State.LOCKED
            elif state == State.LOCKED:
                data = ser.read(PACKET_LENGTH)
                if data[0] != 0x54 or len(data) != PACKET_LENGTH:
                        print("WARNING: Serial sync lost")
                        state = State.SYNC0
                        continue
                measurements += parse_lidar_data(data)
                if len(measurements) > MEASUREMENTS_PER_PLOT:
                    state = State.SEND_PACKET
            elif state == State.SEND_PACKET:
                    measurementJSON = json.dumps(measurements)
                    client.publish(MQTT_TOPIC, measurementJSON)
                    time.sleep(0.00001)
                    state = State.LOCKED
                    measurements = []
                    measurementJSON = ""
    except KeyboardInterrupt:
        print("Exiting...")

    finally:
        ser.close()
        client.disconnect()
