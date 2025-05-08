//POROCAR

//TEMP HUMIDITY
#include <DHT11.h>
DHT11 dht11(2);

// ULTRASCHALL
#include <hcsr04.h>

#define TRIG_PIN_FRONT_LEFT 12
#define ECHO_PIN_FRONT_LEFT 13

HCSR04 hcsr04_front_left(TRIG_PIN_FRONT_LEFT, ECHO_PIN_FRONT_LEFT, 20, 4000);

#define TRIG_PIN_FRONT_RIGHT 10
#define ECHO_PIN_FRONT_RIGHT 11

HCSR04 hcsr04_front_right(TRIG_PIN_FRONT_RIGHT, ECHO_PIN_FRONT_RIGHT, 20, 4000);

#define TRIG_PIN_BACK 9
#define ECHO_PIN_BACK 8

HCSR04 hcsr04_back(TRIG_PIN_BACK, ECHO_PIN_BACK, 20, 4000);


// 6050

#include <Wire.h>
#include <math.h>

const int MPU = 0x68;
int16_t AcX, AcY, AcZ, Tmp, GyX, GyY, GyZ;
int AcXcal, AcYcal, AcZcal, GyXcal, GyYcal, GyZcal, tcal;
double t, tx, tf, pitch, roll, Rolle, Pitch;


// KOMPASS

#include <QMC5883LCompass.h>

QMC5883LCompass compass;



void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

  // 6050
  Wire.begin();
  Wire.beginTransmission(MPU);
  Wire.write(0x6B);
  Wire.write(0);
  Wire.endTransmission(true);

  // Kompass
  compass.init();
}

void loop() {

  Serial.println("------------------");
  Serial.println("|   DATA START   |");
  Serial.println("------------------");
  // put your main code here, to run repeatedly:
  // TEMP HUMIDITY
  int temperature = 0;
  int humidity = 0;

  int result = dht11.readTemperatureHumidity(temperature, humidity);

  if (result == 0) {
      Serial.print("Temperature: ");
      Serial.print(temperature);
      Serial.print(" °C\tHumidity: ");
      Serial.print(humidity);
      Serial.println(" %");
  } else {
      // Print error message based on the error code.
      Serial.println(DHT11::getErrorString(result));
  }

  Serial.println("---------------------");

  //ULTRASCHALLF 
  Serial.println("Front Left");
  Serial.println(hcsr04_front_left.ToString());
  Serial.println("---------------------");
  Serial.println("Front Right");
  Serial.println(hcsr04_front_right.ToString());
  Serial.println("---------------------");
  Serial.println("BACK");
  Serial.println(hcsr04_back.ToString());

  Serial.println("---------------------");
  // 6050

  Wire.beginTransmission(MPU);
  Wire.write(0x3B);
  Wire.endTransmission(false);
  Wire.requestFrom(MPU, 14, true);
  AcXcal = -950;
  AcYcal = -300;
  AcZcal = 0;
  tcal = -1600;
  GyXcal = 480;
  GyYcal = 170;
  GyZcal = 210;
  AcX = Wire.read() << 8 | Wire.read();
  AcY = Wire.read() << 8 | Wire.read();
  AcZ = Wire.read() << 8 | Wire.read();
  Tmp = Wire.read() << 8 | Wire.read();
  GyX = Wire.read() << 8 | Wire.read(); 
  GyY = Wire.read() << 8 | Wire.read();
  GyZ = Wire.read() << 8 | Wire.read();
  tx = Tmp + tcal;
  t = tx / 340 + 36,53;
  tf = (t * 9 / 5) + 32;
  getAngle(AcX, AcY, AcZ);
  Serial.print("Winkel: ");
  Serial.print("Pitch = ");
  Serial.print(pitch);
  Serial.print("  | ");
  Serial.print(Pitch);
  Serial.print(" Roll = ");
  Serial.print(roll);
  Serial.print("  | ");
  Serial.print(Rolle);
  Serial.print("Beschleunigungsmesser: "); Serial.print("X = ");
  Serial.print(AcX + AcXcal);
  Serial.print(" Y = ");
  Serial.print(AcY + AcYcal);
  Serial.print(" Z = ");
  Serial.println(AcZ + AcZcal);
  Serial.print("Temperatur in Celsius = "); Serial.print(t);
  Serial.print(" Fahrenheit = ");
  Serial.println(tf);
  Serial.print("Gyroskop: ");
  Serial.print("X = ");
  Serial.print(GyX + GyXcal);
  Serial.print(" Y = ");
  Serial.print(GyY + GyYcal);
  Serial.print(" Z = ");
  Serial.println(GyZ + GyZcal);

  Serial.println("---------------------");


  // KOMPASS

    int x, y, z;
  
  // Read compass values
  compass.read();

  // Return XYZ readings
  x = compass.getX();
  y = compass.getY();
  z = compass.getZ();
  
  Serial.print("X: ");
  Serial.print(x);
  Serial.print(" Y: ");
  Serial.print(y);
  Serial.print(" Z: ");
  Serial.print(z);
  Serial.println();
  
  Serial.println("---------------------");

  Serial.println("------------------");
  Serial.println("|    DATA End    |");
  Serial.println("------------------");
}


void getAngle(int Ax, int Ay, int Az) {
double x = Ax;
double y = Ay;
double z = Az;
Pitch = atan(x / sqrt((y * y) + (z * z)));
roll = atan(y / sqrt((x * x) + (z * z)));
pitch = pitch * (180,0 / 3,14);
Rolle = Rolle * (180,0 / 3,14) ;
}