package com.porotech.backend;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;

@RestController
public class OpenCvStreamController {

    private VideoCapture camera;

    @PostConstruct
    public void init() {
        Loader.load(opencv_imgcodecs.class);  // OpenCV-Bibliotheken laden
        camera = new VideoCapture(0);
    }

    @GetMapping(value = "/opencv/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    public void stream(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setContentType("multipart/x-mixed-replace;boundary=frame");

        try (OutputStream out = response.getOutputStream()) {
            Mat frame = new Mat();
            BytePointer buf = new BytePointer();

            while (camera.isOpened()) {
                camera.read(frame);
                if (!frame.empty()) {
                    boolean ok = opencv_imgcodecs.imencode(".jpg", frame, buf);
                    if (ok) {
                        byte[] imageBytes = new byte[(int) buf.limit()];
                        buf.get(imageBytes);

                        out.write((
                                "--frame\r\n" +
                                        "Content-Type: image/jpeg\r\n" +
                                        "Content-Length: " + imageBytes.length + "\r\n\r\n"
                        ).getBytes());
                        out.write(imageBytes);
                        out.write("\r\n".getBytes());
                        out.flush();
                    }
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}