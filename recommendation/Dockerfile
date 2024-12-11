FROM openjdk:21
COPY ./target/recommendation-0.0.1-SNAPSHOT.jar /usr/src/recommendation/
WORKDIR /usr/src/recommendation/
EXPOSE 8083
CMD ["java", "-jar", "recommendation-0.0.1-SNAPSHOT.jar"]