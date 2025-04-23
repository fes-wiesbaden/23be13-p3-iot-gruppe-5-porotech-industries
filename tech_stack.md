# 🧱 Tech Stack Übersicht

Diese Dokumentation bietet einen Überblick über die im Projekt verwendeten Technologien sowie die Gründe für ihre Auswahl.

---

## 🗂 Versionsverwaltung

- **Git**: Verteiltes Versionskontrollsystem zur Nachverfolgung von Änderungen und zur Zusammenarbeit im Team.
- **GitHub**: Plattform zur Verwaltung des Repositories, inklusive Issues, Pull Requests und CI/CD-Pipelines.

*Begründung*: Git in Kombination mit GitHub ermöglicht effiziente Zusammenarbeit, transparente Nachverfolgung von Änderungen und Integration von Automatisierungen.

---

## ⚙️ Backend

- **Programmiersprache**: Java
- **Framework**: Spring Boot

*Begründung*: Java wurde aufgrund der Anforderungen des Arbeitsauftrags und der Ähnlichkeit zu C# gewählt. Sleither sollte sich hoffentlich gut zurechtfinden, da beide Sprachen objektorientiert sind und ähnliche Konzepte nutzen. Spring Boot ermöglicht die Erstellung von robusten und skalierbaren Backend-Anwendungen.

**Zusätzliche Vorteile von Java:**

- **Reiches Ökosystem und ausgereifte Frameworks**: Java verfügt über ein umfangreiches Ökosystem mit einer Vielzahl von Open-Source-Bibliotheken und Frameworks wie Spring Boot, Hibernate und Maven. Diese Tools unterstützen Entwickler bei der Erstellung skalierbarer und wartbarer Anwendungen. [Quelle](https://medium.com/@ShantKhayalian/breaking-down-the-java-ecosystem-must-have-tools-and-libraries-in-2024-8c4c0b9b9bb5)

- **Plattformunabhängigkeit durch die Java Virtual Machine (JVM)**: Java-Anwendungen werden in Bytecode kompiliert, der auf der JVM ausgeführt wird. Dies ermöglicht eine "Write Once, Run Anywhere"-Philosophie, bei der derselbe Code auf verschiedenen Plattformen ohne Änderungen ausgeführt werden kann. [Quelle](https://en.wikipedia.org/wiki/Write_once,_run_anywhere)

- **Bewährte Stabilität und Sicherheit**: Java ist seit Jahrzehnten im Einsatz und hat sich in zahlreichen Unternehmensanwendungen bewährt. Die Sprache bietet integrierte Sicherheitsfunktionen und eine stabile Laufzeitumgebung, was sie zur bevorzugten Wahl für kritische Backend-Systeme macht. [Quelle](https://www.linkedin.com/pulse/why-java-remains-top-choice-enterprise-applications-bruno-monteiro-ayzgf)

---

## 🖥 Frontend

- **Framework**: Angular
- **Sprache**: TypeScript

*Begründung*: Angular wurde gewählt, da Emil beruflich mit diesem Framework arbeitet und somit seine Expertise einbringen kann. Angular bietet eine strukturierte Architektur und umfangreiche Tools für die Entwicklung komplexer Frontends.

---

## 🔌 Embedded Systems

- **Programmiersprache**: C
- **Plattform**: Arduino

*Begründung*: Marcel und Paul verfügen über Erfahrung in C und übernehmen die Entwicklung der Embedded-Software. Arduino bietet eine kostenlose und triviale Steuerung der Sensoren und Aktoren.

---

## 🧠 Bildverarbeitung

- **Sprache**: Python
- **Bibliotheken**: OpenCV, ggf. weitere

*Begründung*: Dorian bringt durch seine beruflichen Aufgaben Erfahrung in Python mit und wird gemeinsam mit Sleither die Bildverarbeitung entwickeln. OpenCV bietet umfangreiche Funktionen für die Verarbeitung und Analyse von Bildern.

---

## 🗄️ Datenbank

- **Optionen**: PostgreSQL oder SQLite

*Begründung*: Die Entscheidung zwischen PostgreSQL und SQLite hängt von den spezifischen Anforderungen ab. PostgreSQL bietet umfangreiche Funktionen für komplexe Anwendungen, während SQLite eine leichtgewichtige Lösung für kleinere Anwendungen darstellt.

---

## 📡 Kommunikation

- **Protokoll**: MQTT
- **Broker**: Eclipse Mosquitto

*Begründung*: MQTT ist ein leichtgewichtiges Protokoll für die Kommunikation zwischen Geräten. Eclipse Mosquitto ist ein zuverlässiger und weit verbreiteter MQTT-Broker, der sich gut für IoT-Anwendungen eignet.

---

## 🐳 Containerisierung & Deployment

- **Containerisierung**: Docker
- **Deployment-Umgebungen**:
  - **Onboard**: Raspberry Pi auf dem Fahrzeug
  - **Zentrale Server**: NixOS-basierter Heimserver

*Begründung*: Die Nutzung von Docker ermöglicht eine konsistente und isolierte Umgebung für die Anwendungen, was die Entwicklung, das Testing und das Deployment vereinfacht. Der Raspberry Pi dient als Edge-Device für die Verarbeitung direkt am Fahrzeug, während der NixOS-Server zentrale Aufgaben wie Datenaggregation, API-Management und Datenbankverwaltung übernimmt.

---

## 🔄 CI/CD

- **Tool**: GitHub Actions

*Begründung*: GitHub Actions ermöglicht die Automatisierung von Build-, Test- und Deployment-Prozessen direkt in GitHub. Dies sorgt für eine effiziente und zuverlässige Entwicklungspipeline.

---

## 🚫 Warum nicht Node.js?

Obwohl Node.js eine beliebte Plattform für Backend-Entwicklung ist, haben wir uns dagegen entschieden. Gründe hierfür sind:

- **Eingeschränkte Leistung bei CPU-intensiven Aufgaben**: Node.js verwendet ein Single-Threaded-Event-Loop-Modell, das bei rechenintensiven Operationen wie Bildverarbeitung oder Verschlüsselung an seine Grenzen stößt. Solche Aufgaben können den Event Loop blockieren und die Gesamtleistung der Anwendung beeinträchtigen. [Quelle](https://www.peerbits.com/blog/nodejs-vs-java-backend-development-comparison.html)

- **Begrenzte Unterstützung für Multithreading**: Node.js hat keine native Unterstützung für Multithreading, was die Skalierbarkeit auf Multi-Core-Systemen einschränkt. Java hingegen bietet robuste Multithreading-Funktionen, die eine effizientere Nutzung von Systemressourcen ermöglichen. [Quelle](https://www.peerbits.com/blog/nodejs-vs-java-backend-development-comparison.html)

- **Sicherheitsbedenken**: Node.js-Anwendungen sind anfälliger für Sicherheitslücken wie Cross-Site Scripting (XSS) und Denial-of-Service (DoS)-Angriffe, insbesondere wenn unsichere Drittanbieterpakete verwendet werden. Java bietet hingegen eine stärkere Sicherheitsarchitektur mit integrierten Mechanismen zur Fehlerbehandlung und Zugriffskontrolle. [Quelle](https://yesitlabs-marketing.medium.com/node-js-vs-java-which-one-is-better-for-backend-development-2f3e3a998125)

---

*Hinweis*: Diese Dokumentation wird bei Bedarf aktualisiert, um Änderungen im Technologie-Stack zu reflektieren.