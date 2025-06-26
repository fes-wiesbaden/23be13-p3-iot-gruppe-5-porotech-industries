Hier ist eine strukturierte und ansprechende README.md-Datei für dein Projekt, die eine klare Anleitung zur Einrichtung der Entwicklungsumgebung unter Windows mit Scoop bietet:

⸻

🚀 PoroTech Backend – Entwicklungsumgebung einrichten (Windows)

Willkommen zum PoroTech Backend! Dieses Projekt basiert auf Java 21, Maven und Spring Boot. Um eine konsistente und einfache Einrichtung der Entwicklungsumgebung unter Windows zu gewährleisten, empfehlen wir die Verwendung von Scoop, einem benutzerfreundlichen Paketmanager für Windows.

⸻

🧰 Voraussetzungen
	•	Windows 10 oder neuer
	•	PowerShell 5.1 oder höher
	•	Keine Administratorrechte erforderlich

⸻

⚙️ Schritt-für-Schritt-Anleitung zur Einrichtung

1. PowerShell für die Ausführung von Skripten konfigurieren

Öffne PowerShell und führe folgenden Befehl aus, um die Ausführung von Skripten zu ermöglichen:

Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser

2. Scoop installieren

Installiere Scoop mit folgendem Befehl:

Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression

3. Notwendige Pakete mit Scoop installieren

Führe die folgenden Befehle aus, um die erforderlichen Tools zu installieren:

scoop install git
scoop bucket add java
scoop install java/microsoft21-jdk
scoop install maven



⸻

✅ Überprüfung der Installation

Stelle sicher, dass die Installationen erfolgreich waren:

java -version
mvn -v

Die Ausgaben sollten die installierten Versionen von Java und Maven anzeigen.

⸻

🏁 Projekt starten
	1.	Klonen Sie das Repository:

git clone https://github.com/dein-benutzername/porotech-backend.git
cd porotech-backend


	2.	Erstellen Sie das Projekt mit Maven:

mvn clean package


	3.	Starten Sie die Anwendung:

mvn spring-boot:run



⸻

🧪 Tests ausführen

Um die Anwendung zu testen, führen Sie aus:

mvn test



⸻

📄 Weitere Informationen
	•	Scoop Dokumentation
	•	Spring Boot Dokumentation
	•	Maven Dokumentation

⸻

Mit dieser Anleitung sollte die Einrichtung der Entwicklungsumgebung reibungslos verlaufen. Bei Fragen oder Problemen stehen wir gerne zur Verfügung!

⸻

