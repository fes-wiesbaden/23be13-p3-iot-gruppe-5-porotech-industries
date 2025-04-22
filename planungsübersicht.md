# Poro Car

![Poro Image](src/poro_car.png)

## Table of Content

1. [Projektziel]()
2. [Hardware](#hardware) <br>
   2.1 [Microcontroller, Server, usw](#microcontroller-server-usw) <br>
   2.2 [Sensoren](#sensoren) <br>
3. [Softwarearchitektur](#softwarearchitektur) <br>
4. [Kommunikation](#kommunikation) <br>
5. [Backend](#backend) <br>
6. [Frontend](#frontend) <br>
7. [Issues](#issues) <br>
   7.1 [Planung](#planerstellung) <br>
   7.2 [Umsetung](#umsetzung) <br>
   7.3 [Messe Vorbereitung](#messevorbereitung) <br>
---

## Projektziel

Ziel ist die Realisierung eines cyberphysischen Systems zur smarten Automatisierung von Erkennung, Entscheidung und Steuerung über eine intelligente Fahrzeugplattform. Das Poro Car erfüllt folgende Anforderungen:

- Sensorische Erfassung durch Mikrocontroller
- Datenübertragung via WLAN, LAN und/oder 2,4GHz Funk
- Datenerfassung über MQTT-Broker
- Speicherung in einer relationalen Datenbank (PostgreSQL)
- Verteilung & Verarbeitung von Sensordaten zur Ermittlung von Sollwerten
- Kommunikation mit Aktoren über MQTT
- Visualisierung & Interaktion über eine Webanwendung

---

## Hardware

### Microcontroller, Server, usw

- Arduino für Sensorik
- Raspberry 5 8GB als Datenverarbeitung
- Raspberry als Server
- Auto mit Fernsteuerung

### Sensoren

| Sensor              | Funktion | Wichtigkeit |
|:--------------------|:---------|:------------|
| Ultraschallsensor   |          |             |
| Infrarotsensor      |          |             |
| Motordrehzahlsensor |          |             |
| Gyroskop            |          |             |
| Magnetometer        |          |             |
| Akkustandsensor     |          |             |
| Kamera              |          |             |
| Geigerzähler        |          |             | 
| Schallsensor        |          |             |
| Feinstaubsensor     |          |             |
| Drucksensor         |          |             | 
| Extra Drohne        |          |             |
| Feuchtigkeitssensor |          |             | 
| LIDAR               |          |             |
| Farbsensor          |          |             |
| QR/Barcode scanner  |          |             |
| Temperatur          |          |             |
| Lautstärke          |          |             |
| UV-Sensor           |          |             |
| Drucksensor         |          |             |
| Mikrofon            |          |             |

## Softwarearchitektur
                   
```
Arduino (Sensorik)(Auto) 
    |   ^
    |   |  Serielle Schnitstelle
    ^   |
Raspberry PI (auch auto) 
    |   ^
    |   | MQTT
    ^   |
Raspberry Pi Server <-> DB
    |   ^
    |   | WS, REST
    ^   |
Webclient
```

---

## Kommunikation

| Kommunikationsart          | Verwendungszweck                                          |
|:---------------------------|:----------------------------------------------------------|
| serielle Schnittstelle/LAN | Arduino und Berechnungsraspberry                          |
| WLAN                       | Webinterface, Kamera streaming                            |
| MQTT                       | Kommunikation zwischen berechnungsraspberry und Server/DB |

---

## Backend

- Server: Java 
- MQTT irgendwas
- DB: PostgreSQL
    -> alle möglichen sensordaten

---

## Frontend

- HTML, CSS, Angular
- Live kommunikation via Websockets
- Auto Daten:
  - Kamera
  - Füllstand akku
  - Luftqualität
  - ... alles wofür wir sensoren haben
    
- Route Tracking
- Manuelle Steuerung
...

---

## Issues

### Planerstellung

- Planen was alles benötigt wird
- Sachen suchen/bestellung aufsetzten
- klären ob das überhaupt cps ist (keine ahnung warum nicht)


### Umsetzung
...

### Messevorbereitung
...