FROM openjdk:17
WORKDIR /app
COPY target/gateway-service.jar /app
EXPOSE 8090
CMD ["java", "-jar", "gateway-service.jar"]