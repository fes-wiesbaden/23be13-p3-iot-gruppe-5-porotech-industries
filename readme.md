# PoroCar Client

## Überblick

Dies ist der Client unseres PoroCar Projektes welcher auf einem RaspberryPi 5 8GB läuft

Es sorgt für die sammlung der Sensordaten (Arduino + Raspberry) und weiterleitung an das [Backend](https://github.com/PoroTech-Industries/porotech-backend), sowie die Empfangung und Ausführung von Bewegungskommandos


## Funktionen

| Funktion            | Beschreibung                                                                                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------|
| Serielle Verbindung | Baut serielle Verbindung zum [Arduino Uno](https://github.com/PoroTech-Industries/porotech-embedded) auf und ließt Sensordaten |
| Datenverarbeitung   | Parsed Sensordaten (extrahiert mqtt pfade)                                                                                     |
| MQTT-Kommunikation  | Baut MQTT-Verbindung zu Broker auf (Mosquitto)                                                                                 |
| Datenübertragugn    | Dendet Sensordaten an MQTT-Broker                                                                                              |
| Logging             | Hat Konfigurierbaren Logger eingerichtet                                                                                       |

## Bekannte Einschränkungen

| Problem                                  | Ursache                                                                                                                                                                                           |
|------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| PWM-Steuerung funktioniert nicht richtig | Bei erstellung werden hardware pwm pins (12,13,18,19) nicht als diese annerkannt und über Software gesteuert wodurch CPU auf 100% geht (Diozero Library)                                          |
| Bewegungskommandos theoretisch möglich   | Dadurch das wir beim [Backend](https://github.com/PoroTech-Industries/porotech-backend) keine richtige Karte durch fehlerhafte yaw werte bilden können wurde dies nur oberflächlich implementiert |

---

## Wie starten

### 1. Clone

Zum starten die Repo erstmal lokal klonen

```bash
git clone https://github.com/PoroTech-Industries/porotech-embedded.git
```

### 2. Dependencies Installieren

```bash
sudo apt install java maven 
```

### 3. Bauen und Ausführen

```bash
cd porotech-embedded
chmod +x build.sh
chmod +x run.sh
./build.sh && ./run.sh
```
