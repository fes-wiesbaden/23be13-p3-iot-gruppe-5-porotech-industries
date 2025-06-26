# PoroCar Client

## Überblick

Dies ist der Client unseres PoroCar Projektes welcher auf einem RaspberryPi 5 8GB läuft.

Der Client ist primär für das Erfassen der Sensordaten (Arduino + Raspberry) zuständig. Diese werden per MQTT an das [Backend](https://github.com/PoroTech-Industries/porotech-backend) weitergeleitet.
Zudem steuert er die Motorik unseres Fahrzeuges.


## Funktionen

| Funktion            | Beschreibung                                                                                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------|
| Serielle Verbindung | Baut serielle Verbindung zum [Arduino Uno](https://github.com/PoroTech-Industries/porotech-embedded) auf und liest Sensordaten |
| Datenverarbeitung   | Parsed Sensordaten (extrahiert MQTT Pfade)                                                                                     |
| MQTT-Kommunikation  | Baut MQTT-Verbindung zu Broker auf (Mosquitto)                                                                                 |
| Datenübertragung    | Leitet Sensordaten an MQTT-Broker weiter                                                                                             |
| Logging             | Hat Konfigurierbaren Logger eingerichtet                                                                                       |

## Bekannte Einschränkungen

| Problem                                  | Ursache                                                                                                                                                                                           |
|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| PWM-Steuerung funktioniert nicht richtig | Bei Start werden hardware PWM pins (12,13,18,19) nicht als diese anerkannt und über Software gesteuert wodurch die CPU-Auslastung auf 100% geht (Diozero Library)                                          |
| Bewegungskommandos theoretisch möglich   | Da unser [Backend](https://github.com/PoroTech-Industries/porotech-backend), durch fehlerhafte Gierwerte unseres Kompassmoduls, die Daten des LIDAR-Sensors nicht als Karte visualisern kann, wurde dies nur oberflächlich implementiert |

---

## Guide

### 1. Clone

Die Repo zum Start zunächst klonen.

```bash
git clone https://github.com/PoroTech-Industries/porotech-client.git
```

### 2. Dependencies Installieren

```bash
sudo apt install java maven 
```

### 3. Verkabeln

Die Verkabelung umfasst den Lidarsensor und die Motortreiber, die mit den Motoren verbunden sein müssen.

![Fritzing Kabelung](https://raw.githubusercontent.com/PoroTech-Industries/porotech-documentation/master/porotech-embedded/pinout.png)

### 4. Konfigurieren

In `src/main/java/com/porotech_industries_porocar/App.java` broker ip, sowie Base Path für das den Lidar angeben, z.B. `Documents/PoroCar/porotech-client/serialMQTT`

### 5. Broker starten

Einen MQTT broker starten auf dem richtigen gerät damit sich die Clients verbinden können, empfohlen wird eclipse mosquitto über docker laufen zu lassen

### 6. Bauen und Ausführen

```bash
cd porotech-client
chmod +x build.sh
chmod +x run.sh
./build.sh && ./run.sh
```