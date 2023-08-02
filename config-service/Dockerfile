FROM openjdk:17
WORKDIR /app
COPY target/config-service.jar /app
EXPOSE 8090
CMD ["java", "-jar", "config-service.jar"]