@echo off
set MAIN_CLASS=com.porotech_industries.porocar.App
set JAR_NAME=porotech-client-1.0-SNAPSHOT.jar
set DEPENDENCY_FOLDER=target\dependency

set CLASSPATH=target\%JAR_NAME%;%DEPENDENCY_FOLDER%\*

echo java -cp %CLASSPATH% %MAIN_CLASS%
java -cp %CLASSPATH% %MAIN_CLASS%