FROM openjdk:21
COPY ./target/shopCart-0.0.1-SNAPSHOT.jar /usr/src/shopcart/
WORKDIR /usr/src/shopcart/
EXPOSE 8081
CMD ["java", "-jar", "shopCart-0.0.1-SNAPSHOT.jar"]