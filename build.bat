@echo off
set MAVEN_COMMAND=.\mvnw.cmd clean dependency:copy-dependencies package

echo %MAVEN_COMMAND%
%MAVEN_COMMAND%