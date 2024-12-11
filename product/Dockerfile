FROM openjdk:21
COPY ./target/Product-0.0.1-SNAPSHOT.jar /usr/src/product/
WORKDIR /usr/src/product/
EXPOSE 8082
CMD ["java", "-jar", "Product-0.0.1-SNAPSHOT.jar"]