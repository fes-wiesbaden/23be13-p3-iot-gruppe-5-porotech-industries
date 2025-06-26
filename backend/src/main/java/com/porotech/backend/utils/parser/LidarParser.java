package com.porotech.backend.utils.parser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class LidarParser {
    private static final int MEASUREMENT_LENGTH = 12;

    public static class LidarMeasurement {
        public final float startAngle;
        public final float endAngle;
        public final int[] distances;
        public final int[] confidences;

        public LidarMeasurement(float startAngle, float endAngle, int[] distances, int[] confidences) {
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.distances = distances;
            this.confidences = confidences;
        }
    }

    public LidarMeasurement parseLidar(byte[] data) {
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

        int endAngleRaw = buffer.getShort() & 0xFFFF;
        int timestamp = buffer.getShort() & 0xFFFF;
        int crc = buffer.get() & 0xFF;

        float startAngle = startAngleRaw / 100.0f;
        float endAngle = endAngleRaw / 100.0f;

        if (endAngle < startAngle) {
            endAngle += 360.0f;
        }

        //System.out.print("Distances: [");
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            //System.out.print(distances[i]);
            //if (i < MEASUREMENT_LENGTH - 1) System.out.print(", ");
        }
        //System.out.println("]");

        //System.out.print("Confidences: [");
        for (int i = 0; i < MEASUREMENT_LENGTH; i++) {
            //System.out.print(confidences[i]);
            //if (i < MEASUREMENT_LENGTH - 1) System.out.print(", ");
        }
        //System.out.println("]");

        LidarMeasurement measurement = new LidarMeasurement(startAngle, endAngle, distances, confidences);

        //System.out.printf("Length: %d, Speed: %d, StartAngle: %.2f, endAngle: %.2f, Timestamp: %d, CRC: %d%n",length, speed, startAngle, endAngle, timestamp, crc);

        return measurement;
    }

}
