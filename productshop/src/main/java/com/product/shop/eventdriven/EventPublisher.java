package com.product.shop.eventdriven;


import com.product.shop.model.ShopCart;
import com.product.shop.model.ShopProduct;
import com.product.shop.model.ShopUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventPublisher {

    private final AmqpTemplate amqpTemplate;
    private final String exchangeName;

    public EventPublisher(
            final AmqpTemplate amqpTemplate,
            @Value("${amqp.exchange.name}") final String exchangeName
    ) {
        this.amqpTemplate = amqpTemplate;
        this.exchangeName = exchangeName;
    }

    public void publishCartEvent(ShopProduct product, ShopUser user, ShopCart cart){
        CartEvent event = buildEvent(product,user,cart);
        String routingKey = "cart." + (event.getProductList() == null ? "full" : "not full");
        log.info("heres the rabbit message (as object): {}", event);
        amqpTemplate.convertAndSend(exchangeName,routingKey,event);
    }

    /*public void publishRoundEvent(
            RoundR round,
            OutcomeR outcome
    ){
        RoundEvent event = buildEvent(round, outcome);
        String routingKey = "round." +
                (event.getWinningPlayerId() == null ?
                        "lost" : "won");
        log.info("Here's the Rabbit message (as object): {}", event);
        amqpTemplate.convertAndSend(exchangeName, routingKey, event);

    }*/

    public CartEvent buildEvent(ShopProduct product, ShopUser user, ShopCart cart){
        CartEvent event = new CartEvent(
                product.getId(),
                user.getId(),
                cart.getId(),
                cart.getProductList(),
                cart.getUser()
        );
        return event;

    }
    public String buildEventString(ShopProduct product, ShopUser user, ShopCart cart) {
        StringBuffer eventBuffer = new StringBuffer();

        eventBuffer.append("{")
                .append("\"productId\":" + product.getId() + ",")
                .append("\"productName\":" + product.getName() + ",")
                .append("\"userId\":" + user.getId() + ",")
                .append("\"userName\":" + user.getName() + ",")
                .append("\"cartProductList\":" + cart.getProductList())
                .append("}");
        return eventBuffer.toString();
    }


}