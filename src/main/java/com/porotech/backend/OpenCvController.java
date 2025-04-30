package com.porotech.backend;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("!test")
@RestController
public class OpenCvController {

    static {
        Loader.load(opencv_java.class);
    }

    @GetMapping("/opencv/test")
    public String testOpenCv() {
        try {
            // Kamera (Device 0) öffnen
            VideoCapture camera = new VideoCapture(0);

            if (!camera.isOpened()) {
                return "❌ Fehler: Kamera konnte nicht geöffnet werden.";
            }

            Mat frame = new Mat();
            camera.read(frame);

            if (frame.empty()) {
                camera.release();
                return "⚠️ Fehler: Kein Bild von der Kamera erhalten.";
            }

            camera.release();
            return "✅ OpenCV funktioniert! Frame erfolgreich aufgenommen (Größe: "
                    + frame.size() + ")";
        } catch (Exception e) {
            return "❌ OpenCV Fehler: " + e.getMessage();
        }
    }
}