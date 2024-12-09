package com.cart.shopcart.eventdriven;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;


@Service
public class ProductEventPublisher {
    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public ProductEventPublisher(
            final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    public void publishProductEvent(ProductEvent message) {
        String routingKey = "product.added";
        amqpTemplate.convertAndSend(exchangeName, routingKey, message);
    }

}
