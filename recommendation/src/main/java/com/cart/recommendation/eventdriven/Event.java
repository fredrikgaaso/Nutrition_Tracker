package com.cart.recommendation.eventdriven;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.dtos.ProductEventDTO;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Event {

    private final RecommendationService recommendationService;
    private final ShopCartClient shopCartClient;

    @RabbitListener(queues = "${amqp.queue.name}")
    void receiveMessage(ProductEventDTO message) {
        CartDTO cartDTO = shopCartClient.remoteGetOneCart(message.getCartId());
        ShopCart response = cartDTO.getShopCart();
        log.info(message.toString());
        recommendationService.updateRecommendation(response);
    }
}
