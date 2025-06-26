
FROM maven:3.9.9-eclipse-temurin-21-jammy AS builder
WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

HEALTHCHECK --interval=30s --timeout=5s CMD curl --fail http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]