FROM openjdk:21
COPY /target/gateway-0.0.1-SNAPSHOT.jar /app/gateway.jar
WORKDIR /usr/src/gateway/
EXPOSE 8000
CMD ["java", "-jar", "/app/gateway.jar"]