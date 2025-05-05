package com.porotech.backend;

import jakarta.annotation.PreDestroy;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_java;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@Profile("!test")
@RestController
public class OpenCvStreamController {

    private VideoCapture camera;
    private CascadeClassifier faceCascade;

    @PostConstruct
    public void init() throws Exception {
        Loader.load(opencv_java.class);

        camera = new VideoCapture(0);

        String xmlPath = new ClassPathResource("haarcascade_frontalface_default.xml")
                .getFile().getAbsolutePath();
        faceCascade = new CascadeClassifier(xmlPath);
        if (faceCascade.empty()) {
            throw new RuntimeException("Fehler beim Laden der Cascade: " + xmlPath);
        }
    }

    @PreDestroy
    public void cleanup() {
        camera.release();
        faceCascade.close();
    }

    @GetMapping(value = "/opencv/stream", produces = "multipart/x-mixed-replace;boundary=frame")
    public void stream(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Connection", "keep-alive");
        response.setContentType("multipart/x-mixed-replace;boundary=frame");

        try (OutputStream out = response.getOutputStream()) {
            Mat frame = new Mat();
            Mat gray  = new Mat();
            BytePointer ptr = new BytePointer();
            RectVector faces = new RectVector();

            while (camera.isOpened()) {
                if (!camera.read(frame) || frame.empty()) {
                    continue;
                }

                opencv_imgproc.cvtColor(frame, gray, opencv_imgproc.COLOR_BGR2GRAY);


                faceCascade.detectMultiScale(
                        gray,
                        faces,
                        1.1,
                        3,
                        0,
                        new org.bytedeco.opencv.opencv_core.Size(30, 30),
                        new org.bytedeco.opencv.opencv_core.Size()
                );

                for (int i = 0; i < faces.size(); i++) {
                    Rect r = faces.get(i);
                    Scalar red = new Scalar(0, 0, 255, 0);
                    opencv_imgproc.rectangle(
                            frame,
                            r,
                            red,
                            2,
                            opencv_imgproc.LINE_8,
                            0
                    );
                }

                boolean ok = opencv_imgcodecs.imencode(".jpg", frame, ptr);
                if (!ok) {
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

                Thread.sleep(33);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}