package com.porotech.backend.map;

import org.springframework.stereotype.Component;

@Component
public class CarPosition {
    private int x;
    private int y;
    private int z;
    private double yaw;
    private double pitch;
    private double roll;
    private double velocityX;
    private double velocityY;
    private double velocityZ;
    private long lastUpdateTime;

    public CarPosition() {
        this.x = -1;
        this.y = -1;
        this.z = 0;
        this.yaw = 90;
        this.pitch = 0;
        this.roll = 0;
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.lastUpdateTime = 0;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public double getYaw() { return yaw; }
    public double getPitch() {  return pitch; }
    public double getRoll() { return roll; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public double getVelocityZ() { return velocityZ; }
    public long getLastUpdateTime() { return lastUpdateTime; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setZ(int z) { this.z = z; }
    public void setYaw(double yaw) { this.yaw = yaw; }
    public void setPitch(double pitch) { this.pitch = pitch; }
    public void setRoll(double roll) { this.roll = roll; }
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }
    public void setVelocityZ(double velocityZ) { this.velocityZ = velocityZ; }
    public void setLastUpdateTime(long lastUpdateTime) { this.lastUpdateTime = lastUpdateTime; }


    public void moveUp() {
        this.y += 1;
    }
    public void updatePositionFromVelocity(double deltaTimeSeconds) {
        this.x += this.velocityX * deltaTimeSeconds;
        this.y += this.velocityY * deltaTimeSeconds;
        this.z += this.velocityZ * deltaTimeSeconds;
    }


    public void print() {
        System.out.printf("CarPosition(x=%.2f, y=%.2f, z=%.2f, yaw=%.2f°, pitch=%.2f°, roll=%.2f°, velocityX=%.2f°, velocityY=%.2f°, velocityZ=%.2f°)",
                x, y, z, yaw, pitch, roll, velocityX, velocityY, velocityZ);
    }
}
