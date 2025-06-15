package com.porotech.backend.map;

import com.porotech.backend.utils.parser.LidarParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class Map3D {
    private static final int WIDTH = 2000;
    private static final int HEIGHT = 2000;
    private static final double CELL_SIZE_CM = 1.0;

    private final int[][] grid = new int[WIDTH][HEIGHT]; // 0: unknown, 1: free, 2: obstacle
    private final CarPosition carPosition;

    public Map3D(CarPosition carPosition) {
        this.carPosition = carPosition;
    }

    public void addLidarPoint(LidarParser.LidarMeasurement measurement) {
        if (carPosition.getX() == -1 && carPosition.getY() == -1) return;
        System.out.println("Car Position: " + carPosition.getX() + ", " + carPosition.getY());

        int[] distances = measurement.distances;
        double startAngle = measurement.startAngle;
        double endAngle = measurement.endAngle;

        int numPoints = distances.length;
        double angleStep = (endAngle - startAngle) / (numPoints - 1);

        double yawRad = Math.toRadians(carPosition.getYaw());

        double carWorldX_mm = carPosition.getX() * CELL_SIZE_CM * 10; // Zelle → cm → mm
        double carWorldY_mm = carPosition.getY() * CELL_SIZE_CM * 10;

        for (int i = 0; i < numPoints; i++) {
            int distance_mm = distances[i];
            if (distance_mm <= 0 || distance_mm > 5000) continue;

            double angleDeg = startAngle + i * angleStep;
            double localAngleRad = Math.toRadians(angleDeg);

            double globalAngleRad = yawRad + localAngleRad;

            double step_mm = CELL_SIZE_CM *10;
            for (double r = 0; r < distance_mm; r += step_mm) {
                double x_mm = carWorldX_mm + Math.cos(globalAngleRad) * r;
                double y_mm = carWorldY_mm + Math.sin(globalAngleRad) * r;

                int cellX = (int)(x_mm / (CELL_SIZE_CM * 10));
                int cellY = (int)(y_mm / (CELL_SIZE_CM * 10));

                if (inBounds(cellX, cellY)) {
                    grid[cellX][cellY] = 1;
                }
            }

            double x_mm = carWorldX_mm + Math.cos(globalAngleRad) * distance_mm;
            double y_mm = carWorldY_mm + Math.sin(globalAngleRad) * distance_mm;

            int cellX = (int)(x_mm / (CELL_SIZE_CM * 10));
            int cellY = (int)(y_mm / (CELL_SIZE_CM * 10));

            if (inBounds(cellX, cellY)) {
                grid[cellX][cellY] = 2;
            }
        }
    }



    private boolean inBounds(int x, int y) {
        //System.out.println(x + " " + y);
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public int[][] getGrid() {
        return grid;
    }

    public CarPosition getCarPosition() {
        return carPosition;
    }

}

//@Service
//class CarPositionUpdater {
//
//    private final Map3D map3D;
//
//    public CarPositionUpdater(Map3D map3D) {
//        this.map3D = map3D;
//    }
//
//    @Scheduled(fixedRate = 1000)  // alle 1000ms (1 Sekunde)
//    public void moveCarUp() {
//        // Aktuelle Position holen, y um 1 erhöhen, zurücksetzen
//        var pos = map3D.getCarPosition();
//        //pos.setY(pos.getY() + 2);
//        pos.setX(pos.getX() + 5);
//        map3D.updateCarPosition(pos);
//
//        System.out.println("Car position moved up to y = " + pos.getY());
//    }
//}