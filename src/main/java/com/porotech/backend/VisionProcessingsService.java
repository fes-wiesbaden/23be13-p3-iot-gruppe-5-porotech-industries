package com.porotech.backend;

import org.bytedeco.opencv.opencv_core.Mat;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_imgproc.Canny;

@Service
public class VisionProcessingService {

    public SteeringCommand analyzeFrame(Mat grayFrame) {
        // Beispiel: Hough-Transformation für Fahrspurerkennung
        Mat edges = new Mat();
        Canny(grayFrame, edges, 50, 150);

        // Linien finden und daraus Winkel berechnen...
        double angle = detectLaneAngle(edges);

        // Geschwindigkeit basierend auf Hindernissen drosseln...
        double throttle = detectObstaclesAndComputeThrottle(grayFrame);

        return new SteeringCommand(angle, throttle);
    }
}