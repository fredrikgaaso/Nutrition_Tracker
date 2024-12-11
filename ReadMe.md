Gateway/consul:<br>
http://localhost:8500/

RabbitMQ<br>
http://localhost:15672/

Product<br>
http://localhost:8082

Cart <br>
http://localhost:8081

Recommendation<br>
http://localhost:8083

<strong>Start new instance with new port:</strong><br>
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=NEW_PORT_NUMBER"


<strong>How to make rabbitmq viable</strong><br>
Make rabbitmq message for when i add a product to a cart.

