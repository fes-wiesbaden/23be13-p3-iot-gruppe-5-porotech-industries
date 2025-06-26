package com.porotech.backend.service;

import com.porotech.backend.datatransfer.mqtt.PoroMqttClient;
import com.porotech.backend.map.CarPosition;
import com.porotech.backend.map.Map3D;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.scheduling.annotation.Async;
import com.porotech.backend.utils.pathfinding.AStar;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Locale;

@Service
public class DrivingService {
    private final PoroMqttClient mqttClient;
    private final Map3D map;
    public boolean isDriving = false;
    private final int OBSTACLE_DISTANCE = 10;

    public DrivingService(PoroMqttClient mqttClient, Map3D map) {
        this.mqttClient = mqttClient;
        this.map = map;
    }

    public void sendMovementData(String movementData) throws MqttException {
        String topic = "porocar/backend/wheels/move";

        mqttClient.publish(topic, movementData.getBytes(), 1, false);
    }

    public void moveVehicle(String direction, String angle, int timeout) throws MqttException {
        String movementString = String.format("%s,%s,%d", direction, angle, timeout);
        sendMovementData(movementString);
    }

    public void powerWheel(float powerFrontLeft, float powerFrontRight, float powerBackLeft, float powerBackRight) throws MqttException {
        String topic = "porocar/backend/wheels/power";
        String powerString = String.format(Locale.US, "%f|%f|%f|%f",
                powerFrontLeft, powerFrontRight, powerBackLeft, powerBackRight);

        mqttClient.publish(topic, powerString.getBytes(), 1, false);
    }

    public void stopDriving() throws MqttException {
        isDriving = false;
        moveVehicle("STOP", "NONE", 0);
    }

    @Async
    public void driveInCircle() throws Exception {
        int CIRCLE_STEP_TIMEOUT = 20;

        isDriving = true;
        while (isDriving) {
            if (isObstacleAhead()) {
                moveVehicle("stop", "NONE", CIRCLE_STEP_TIMEOUT);
                //moveVehicle("stop", "left", CIRCLE_STEP_TIMEOUT);
            } else {
                moveVehicle("forward", "right", CIRCLE_STEP_TIMEOUT);
            }

            Thread.sleep(CIRCLE_STEP_TIMEOUT);
        }
    }

    @Async
    public void moveToPosition(int x, int y) {
        int DRIVE_TIMEOUT = 100;
        isDriving = true;

        AStar star = new AStar(map);
        while (isDriving) {
            CarPosition carPosition = map.getCarPosition();
            Point start = new Point(carPosition.getX(), carPosition.getY());
            //Point start = new Point(0, 0); //das auskommentieren für testrunner
            System.out.println("start pos " + start.x + "," + start.y);
            Point end = new Point(x, y);


            List<Point> path = star.findPath(start, end);

            int pointCount = path.size();
            int visitedPoints = 0;
            for (int i = 0; i < pointCount; i++) {
                Point point = path.get(i);
                System.out.println(point.x + ", " + point.y);
                if (isObstacleAhead()) {
                    break;
                }
                visitedPoints++;

                //angle rausfinden
                //drehen
                //geradeaus fahren
                //profit
            }
            if (visitedPoints == pointCount) {
                break;
            }
        }
        //HIER SCHLAU MATHE UM ZU BEWEGEN MIT AUSWEICHEN UND SO
    }

    @Async
    public void exploreRoom() {
        while (isDriving) {
            // gucken wo am nächsten kante zu unentfeckt ist
            // mit moveToPos hingehen
            // repeat
            // profit
        }
    }



    private boolean isObstacleAhead() {
        CarPosition carPosition = map.getCarPosition();
        int currentX = carPosition.getX();
        int currentY = carPosition.getY();
        double yawRad = Math.toRadians(carPosition.getYaw());

        int[][] grid = map.getGrid();

        for (int distance = 1; distance <= OBSTACLE_DISTANCE; distance++) {
            int checkX = currentX + (int)(Math.cos(yawRad) * distance);
            int checkY = currentY + (int)(Math.sin(yawRad) * distance);

            if (checkX < 0 || checkX >= grid.length || checkY < 0 || checkY >= grid[0].length) {
                return true;
            }

            if (grid[checkX][checkY] == 2) {
                return true;
            }
        }

        return false;
    }
}


//@Service   // @Bekkaoui [YAW Problem 2]: hier ein test für das pathfinding bewegt sich zum input, einmal x und y eingeben
//class DrivingTestRunner {
//
//    private final DrivingService drivingService;
//
//    public DrivingTestRunner(DrivingService drivingService) {
//        this.drivingService = drivingService;
//    }
//
//    @PostConstruct
//    public void runTest() throws Exception {
//        drivingService.powerWheel(0.7f, 0.7f, 0.7f, 0.7f);
//        Scanner scanner = new Scanner(System.in);
//        String input = scanner.nextLine();
//
//        try {
//            String[] parts = input.trim().split("\\s+");
//            int x = Integer.parseInt(parts[0]);
//            int y = Integer.parseInt(parts[1]);
//            drivingService.moveToPosition(x, y);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        //drivingService.driveInCircle();
//        //Thread.sleep(10000);
//        drivingService.stopDriving();
//        //System.out.println("🚗 Starting driving test to position (5, 5)...");
//        //drivingService.moveToPosition(5, 5);
//
//    }
//}