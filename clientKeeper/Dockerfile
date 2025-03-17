FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/clientKeeper-0.0.1-SNAPSHOT.jar /app/clientKeeper.jar

EXPOSE 8080

CMD ["java", "-jar", "-Dlogging.level.org.springframework=DEBUG", "/app/clientKeeper.jar"]