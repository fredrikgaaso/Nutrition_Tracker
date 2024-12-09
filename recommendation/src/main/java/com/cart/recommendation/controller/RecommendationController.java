package com.cart.recommendation.controller;

import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/cart/{cartId}")
    public ShopCart findOneCart(@PathVariable Long cartId) {
       ShopCart shopCart = recommendationService.getOneCart(cartId);
        if (shopCart == null) {
            log.error("shopCart returned null");
            throw new IllegalStateException("The returned shopCart is null");
        }
       return shopCart;
    }


    @GetMapping("/recommend/{cartId}")
    public List<String> recommendProducts(@PathVariable Long cartId) {
        ShopCart shopCart = recommendationService.getOneCart(cartId);
        if (shopCart == null) {
            log.error("shopCart returned null");
            throw new IllegalStateException("The returned shopCart is null");
        }
        return recommendationService.makeRecommendation(shopCart);
    }
}
