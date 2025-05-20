package com.porotech.backend.utils.parser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class LidarParser {
    private static final int MEASUREMENT_LENGTH = 12;

    public static class LidarMeasurement {
        public final float angle;
        public final float distance;
        public final int confidence;

        public LidarMeasurement(float angle, float distance, int confidence) {
            this.angle = angle;
            this.distance = distance;
            this.confidence = confidence;
        }
    }

    public List<LidarMeasurement> parseLidar(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);

        buffer.get();

        int length = buffer.get() & 0xFF; // 1 byte unsigned
        int speed = buffer.getShort() & 0xFFFF;
        int startAngleRaw = buffer.getShort() & 0xFFFF;

        int[] distances = new int[MEASUREMENT_LENGTH];
        int[] confidences = new int[MEASUREMENT_LENGTH];

        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            distances[i] = buffer.getShort() & 0xFFFF;
            confidences[i] = buffer.get() & 0xFF;
        }

        int stopAngleRaw = buffer.getShort() & 0xFFFF;
        int timestamp = buffer.getShort() & 0xFFFF;
        int crc = buffer.get() & 0xFF;

        float startAngle = startAngleRaw / 100.0f;
        float stopAngle = stopAngleRaw / 100.0f;

        if (stopAngle < startAngle) {
            stopAngle += 360.0f;
        }

        float stepSize = (stopAngle - startAngle) / (MEASUREMENT_LENGTH - 1);

        System.out.print("Distances: [");
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            System.out.print(distances[i]);
            if (i < MEASUREMENT_LENGTH - 1) System.out.print(", ");
        }
        System.out.println("]");

        System.out.print("Confidences: [");
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            System.out.print(confidences[i]);
            if (i < MEASUREMENT_LENGTH - 1) System.out.print(", ");
        }
        System.out.println("]");

        List<LidarMeasurement> measurements = new ArrayList<>();
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            float angle = startAngle + stepSize * i;
            measurements.add(new LidarMeasurement(angle, distances[i], confidences[i]));
        }

        System.out.printf("Length: %d, Speed: %d, StartAngle: %.2f, StopAngle: %.2f, Timestamp: %d, CRC: %d%n",
                length, speed, startAngle, stopAngle, timestamp, crc);

        return measurements;
    }

}
