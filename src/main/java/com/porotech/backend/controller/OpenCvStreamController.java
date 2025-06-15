package com.porotech.backend.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.core.io.Resource;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.bytedeco.opencv.opencv_dnn.DetectionModel;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;

import jakarta.annotation.PreDestroy;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import org.bytedeco.opencv.opencv_dnn.Net;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.bytedeco.opencv.global.opencv_dnn.*;

@Profile("!test")
//@RestController
public class OpenCvStreamController {

    private VideoCapture camera;
    private DetectionModel model;
    private java.util.List<String> classNames;
    private final float confThreshold = 0.5f;
    private final float nmsThreshold = 0.4f;

    // Buffers to decouple capture, processing, and streaming
    private final BlockingQueue<Mat> captureQueue = new LinkedBlockingQueue<>(2);
    private final BlockingQueue<Mat> processedQueue = new LinkedBlockingQueue<>(2);

    //@PostConstruct
    public void init() throws Exception {
        Loader.load(opencv_java.class);

        camera = new VideoCapture(0);

        String cfgPath = new ClassPathResource("models/yolov3.cfg").getURI().getPath();
        String weightsPath = new ClassPathResource("models/yolov3.weights").getURI().getPath();

        Net net = readNetFromDarknet(cfgPath, weightsPath);
        net.setPreferableBackend(DNN_BACKEND_OPENCV);
        net.setPreferableTarget(DNN_TARGET_OPENCL);

        model = new DetectionModel(net);
        model.setInputParams();
        model.setInputSize(new Size(320, 320));
        model.setInputScale(Scalar.all((1.0 / 255.0)));
        model.setInputSwapRB(true);

        Resource namesResource = new ClassPathResource("models/coco.names");
        try (InputStream namesStream = namesResource.getInputStream()) {
            classNames = new BufferedReader(new InputStreamReader(namesStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.toList());
        }

        // Start capture thread
        new Thread(() -> {
            Mat frame = new Mat();
            while (camera.isOpened()) {
                if (camera.read(frame) && !frame.empty()) {
                    captureQueue.offer(frame.clone());
                }
            }
        }, "Capture-Thread").start();

        // Start processing thread
        new Thread(() -> {
            try {
                while (true) {
                    Mat f = captureQueue.take();
                    RectVector boxes = new RectVector();
                    FloatPointer confidences = new FloatPointer();
                    IntPointer classIds = new IntPointer();
                    model.detect(f, classIds, confidences, boxes, confThreshold, nmsThreshold);
                    for (int i = 0; i < boxes.size(); i++) {
                        Rect rect = boxes.get(i);
                        int id = classIds.get(i);
                        float conf = confidences.get(i);
                        String label = (id >= 0 && id < classNames.size()) ? classNames.get(id) : "unknown";
                        opencv_imgproc.putText(f,
                                label + String.format(": %.2f", conf),
                                new Point(rect.x(), rect.y() - 5),
                                opencv_imgproc.FONT_HERSHEY_SIMPLEX, 0.5,
                                new Scalar(0, 255, 0, 0), 1, opencv_imgproc.LINE_8, false);
                        opencv_imgproc.rectangle(f, rect,
                                new Scalar(0, 255, 0, 0), 2, opencv_imgproc.LINE_8, 0);
                    }
                    processedQueue.offer(f);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Processing-Thread").start();
    }

    @PreDestroy
    public void cleanup() {
        camera.release();
    }

    @GetMapping(value = "/opencv/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    @Operation(
        summary = "Live-Video-Stream",
        description = "Gibt einen multipart/x-mixed-replace MJPEG-Stream der verarbeiteten Frames zurück"
    )
    public void stream(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setContentType("multipart/x-mixed-replace;boundary=frame");

        try (OutputStream out = response.getOutputStream()) {
            while (true) {
                Mat frame = processedQueue.take();
                BytePointer ptr = new BytePointer();
                if (!opencv_imgcodecs.imencode(".jpg", frame, ptr)) {
                    continue;
                }
                byte[] imageBytes = new byte[(int) ptr.limit()];
                ptr.get(imageBytes);
                out.write((
                        "--frame\r\n" +
                                "Content-Type: image/jpeg\r\n" +
                                "Content-Length: " + imageBytes.length + "\r\n\r\n"
                ).getBytes(StandardCharsets.US_ASCII));
                out.write(imageBytes);
                out.write("\r\n".getBytes(StandardCharsets.US_ASCII));
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}