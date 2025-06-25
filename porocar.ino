//POROCAR

uint32_t delayMS = 0;

//TEMP HUMIDITY
#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>
#define DHTPIN 2
#define DHTTYPE    DHT11
DHT_Unified dht(DHTPIN, DHTTYPE);

// ULTRASCHALL
#include <hcsr04.h>

#define TRIG_PIN_FRONT_LEFT 12
#define ECHO_PIN_FRONT_LEFT 13

HCSR04 hcsr04_front_left(TRIG_PIN_FRONT_LEFT, ECHO_PIN_FRONT_LEFT, 20, 4000);

#define TRIG_PIN_FRONT_RIGHT 10
#define ECHO_PIN_FRONT_RIGHT 11

HCSR04 hcsr04_front_right(TRIG_PIN_FRONT_RIGHT, ECHO_PIN_FRONT_RIGHT, 20, 4000);

#define TRIG_PIN_BACK 8
#define ECHO_PIN_BACK 9

HCSR04 hcsr04_back(TRIG_PIN_BACK, ECHO_PIN_BACK, 20, 4000);


// 6050

#include <Wire.h>
#include <MPU6050_light.h>

MPU6050 mpu(Wire);

// KOMPASS

#include <QMC5883LCompass.h>

QMC5883LCompass compass;


// drehzahl

#define WHEEL_FRONT_LEFT 3
#define WHEEL_FRONT_RIGHT 4
#define WHEEL_BACK_LEFT 5
#define WHEEL_BACK_RIGHT 6

int stateFrontLeft;
int stateFrontRight;
int stateBackLeft;
int stateBackRight;



void setup() {
  // put your setup code here, to run once:
  Serial.begin(115200);
  Serial.println("TEST");

  // 6050
  Serial.println("!");
  Wire.begin();
  byte status = mpu.begin();
  mpu.calcOffsets(true,true);  
  //Serial.print(F("MPU6050 status: "));
  //Serial.println(status);

  // Kompass
  Serial.println("!");
  compass.init();
  compass.setSmoothing(10,true);  
  //compass.setCalibrationOffsets(5.00, 287.00, -730.00);
  //compass.setCalibrationScales(1.05, 1.06, 0.91);

Serial.println("!");
  dht.begin();
  sensor_t sensor;
  dht.temperature().getSensor(&sensor);
  dht.humidity().getSensor(&sensor);

  // F249
  pinMode(WHEEL_FRONT_LEFT, INPUT);
  pinMode(WHEEL_FRONT_RIGHT, INPUT);
  pinMode(WHEEL_BACK_LEFT, INPUT);
  pinMode(WHEEL_BACK_RIGHT, INPUT);
}

void loop() {
  sensors_event_t event;
  dht.temperature().getEvent(&event);

  String Sensordata = "DATASTART|";
  if (!isnan(event.temperature)) {
    // t = porocar/arduino/sensors/dht11/temperature
    Sensordata += "t:" + String(event.temperature) + "|";
  }
  if (!isnan(event.relative_humidity)) {
    // h = porocar/arduino/sensors/dht11/humidity
    Sensordata += "h:" + String(event.relative_humidity) + "|";
  }


//ULTRASCHALL
  // ufl = porocar/arduino/sensors/hc-sr04/front-left
  Sensordata += "ufl:" + String(hcsr04_front_left.distanceInMillimeters()) + "|";

  // ufr = porocar/arduino/sensors/hc-sr04/front-right
  Sensordata += "ufr:" + String(hcsr04_front_right.distanceInMillimeters()) + "|";

  // ub = porocar/arduino/sensors/hc-sr04/back
  Sensordata += "ub:" + String(hcsr04_back.distanceInMillimeters()) + "|";
  
// KOMPASS
  int a, x, y, z;
  compass.read();
  x = compass.getX();
  y = compass.getY();
  z = compass.getZ();

  byte b;
  char myArray[3];

  //a = porocar/arduino/sensors/gy-271/azimuth
  a = compass.getAzimuth();
  Sensordata += "a:" + String(a) + "|";

  b = compass.getBearing(a);
  // b = porocar/arduino/sensors/gy-271/bearing
  Sensordata += "v:" + String(b) + "|";

  compass.getDirection(myArray, a);
  // d = porocar/arduino/sensors/gy-271/direction
  Sensordata += "d:" + String(myArray[0]) + String(myArray[1]) + String(myArray[2]) + "|";

  // p = porocar/arduino/sensors/gy-271/position
  Sensordata += "p:" + String(x) + "," + String(y) + "," + String(z) + "|";

  //Serial.println("Sensordata" + Sensordata);


  //6060
  mpu.update();
  // ga = porocar/arduino/sensors/mpu6050/acceleration
  Sensordata += "ga:" + String(mpu.getAccX()) + "," + String(mpu.getAccY()) + "," + String(mpu.getAccZ()) + "|";

  // ac = porocar/arduino/sensors/mpu6050/acceleration/angle
  Sensordata += "ac:" + String(mpu.getAccAngleX()) + "," + String(mpu.getAccAngleY()) + "|";

  // an = porocar/arduino/sensors/mpu6050/gyro
  Sensordata += "an:" + String(mpu.getGyroX()) + "," + String(mpu.getGyroY()) + "," + String(mpu.getGyroZ()) + "|";

  // aa = porocar/arduino/sensors/mpu6050/angle
  Sensordata += "aa:" + String(mpu.getAngleY()) + "," + String(mpu.getAngleX()) + "," + String(mpu.getAngleZ()) + "|";


  // F249
  stateFrontLeft  = digitalRead(WHEEL_FRONT_LEFT);
  stateFrontRight = digitalRead(WHEEL_FRONT_RIGHT);
  stateBackLeft   = digitalRead(WHEEL_BACK_LEFT);
  stateBackRight  = digitalRead(WHEEL_BACK_RIGHT);
  // fl = porocar/arduino/sensors/f249/front_left
  Sensordata += "fl:" + String(stateFrontLeft) + "|";

  // fr = porocar/arduino/sensors/f249/front_right
  Sensordata += "fr:" + String(stateFrontRight) + "|";

  // bl = porocar/arduino/sensors/f249/back_left
  Sensordata += "bl:" + String(stateBackLeft) + "|";

  // br = porocar/arduino/sensors/f249/back_right
  Sensordata += "br:" + String(stateBackRight) + "|";
  


  Sensordata += "DATAEND";

  Serial.println(Sensordata);
  delay(delayMS);
}