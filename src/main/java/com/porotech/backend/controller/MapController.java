package com.porotech.backend.controller;

import com.porotech.backend.map.CarPosition;
import com.porotech.backend.map.Map3D;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/map")
public class MapController {

    private final Map3D map3D;

    public MapController(Map3D map3D) {
        this.map3D = map3D;
    }

    @GetMapping(value = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getMapImage() throws IOException {
        int[][] grid = map3D.getGrid();
        int width = grid.length;
        int height = grid[0].length;
        CarPosition carPosition = map3D.getCarPosition();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int val = grid[x][y];
                int color;
                switch (val) {
                    case 0: color = 0xFFFFFF; break;
                    case 1: color = 0xAAAAAA; break;
                    case 2: color = 0x000000; break;
                    default: color = 0xFF00FF;
                }
                image.setRGB(x, height - y - 1, color);
            }
        }

        int carX = (int) (carPosition.getX());
        int carY = (int) (carPosition.getY());

        //auto
        int radius = 4;
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx * dx + dy * dy <= radius * radius) {
                    int px = carX + dx;
                    int py = carY + dy;
                    if (px >= 0 && px < width && py >= 0 && py < height) {
                        image.setRGB(px, height - py - 1, 0xFF0000);
                    }
                }
            }
        }


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
