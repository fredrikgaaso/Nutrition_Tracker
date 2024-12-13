package com.cart.recommendation.controller;

import com.cart.recommendation.eventdriven.Event;
import com.cart.recommendation.model.RecommendationData;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.service.RecommendationService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/update/{cartId}")
    public RecommendationData getRecommendations(@PathVariable Long cartId) {
        if (recommendationService.getRecommendations(cartId) == null) {
             ShopCart shopCart = recommendationService.getOneCart(cartId);
            if (shopCart == null) {
                log.error("shopCart returned null");
                throw new IllegalStateException("The returned shopCart is null");
            }
           return recommendationService.setRecommendations(shopCart);
        }
        return recommendationService.getRecommendations(cartId);
    }


   /* @GetMapping("/recommend/{cartId}")
    public RecommendationData recommendProducts(@PathVariable Long cartId) {
        ShopCart shopCart = recommendationService.getOneCart(cartId);
        if (shopCart == null) {
            log.error("shopCart returned null");
            throw new IllegalStateException("The returned shopCart is null");
        }
        return recommendationService.setRecommendations(shopCart);
    }*/

  /*  @GetMapping("/allergen/{cartId}")
    public List<String> checkAllergens(@PathVariable Long cartId) {
        ShopCart shopCart = recommendationService.getOneCart(cartId);
        if (shopCart == null) {
            log.error("shopCart returned null");
            throw new IllegalStateException("The returned shopCart is null");
        }
        return recommendationService.checkAllergen(shopCart);
    }

    @GetMapping("/nutrition/{cartId}")
    public List<String> checkNutrition(@PathVariable Long cartId) {
        ShopCart shopCart = recommendationService.getOneCart(cartId);
        if (shopCart == null) {
            log.error("shopCart returned null");
            throw new IllegalStateException("The returned shopCart is null");
        }
        return recommendationService.checkNutritionalValue(shopCart);
    }*/
}
