package com.cart.shopcart.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartEventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public CartEventPublisher(
            final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    public void publishProductAddedString(Message message) {
        String event = String.valueOf(buildEventString(message.getProductId(), message.getQuantity(), message.getCartId()));
        String routingKey = "product.added";
        log.info("Here's the Rabbit message: {}", event);
        amqpTemplate.convertAndSend(exchangeName, routingKey, event);
    }

    public Message buildEventString(String productName, int quantity, Long cartId) {
        Message message = new Message();
        message.setProductId(productName);
        message.setQuantity(quantity);
        message.setCartId(cartId);
        return message;
    }
}