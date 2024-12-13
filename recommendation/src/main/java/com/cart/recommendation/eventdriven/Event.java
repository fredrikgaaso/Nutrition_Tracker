package com.cart.recommendation.eventdriven;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.dtos.ProductEventDTO;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.service.RecommendationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class Event {

    private final RecommendationService recommendationService;
    private final ShopCartClient shopCartClient;

    @RabbitListener(queues = "${amqp.queue.name}")
    void receiveMessage(ProductEventDTO message) {
        CartDTO cartDTO = shopCartClient.remoteGetOneCart(message.getCartId());
        if (cartDTO == null) {
            log.error("CartDTO is null for cartId: {}", message.getCartId());
            return;
        }
        ShopCart response = cartDTO.getShopCart();
        if (response == null) {
            log.error("ShopCart is null for cartId: {}", message.getCartId());
            return;
        }
        log.info(message.toString());
        recommendationService.setRecommendations(response);
    }
}
