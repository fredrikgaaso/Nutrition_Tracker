package com.cart.recommendation.service;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final ShopCartClient shopCartClient;


    public ShopCart getOneCart(Long cartId) {
        CartDTO cartDTO = shopCartClient.remoteGetOneCart(cartId);
        log.info("cartDTO received from client: {}", cartDTO);

        ShopCart response = cartDTO.getShopCart();
        log.info("Returning cart: {}", response);
        return response;

    }

    public String makeRecommendation(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        String output;
        if (products.isEmpty()) {
            output = "Your cart is empty. Add some products to get a recommendation.";
            return output;
        }

        // Aggregate scores for each parent group
        Map<String, Long> parentGroupScores = products.stream()
                .collect(Collectors.groupingBy(
                        product -> {
                            String[] parts = product.getFoodGroup().split(".");
                            return (parts.length > 1 && Character.isDigit(parts[0].charAt(0))) ? parts[0] : product.getFoodGroup();
                        },
                        Collectors.summingLong(ShopProduct::getQuantity)
                ));

        // Find the parent group with the lowest score
        Optional<Map.Entry<String, Long>> lowestScoreGroup = parentGroupScores.entrySet().stream()
                .min(Map.Entry.comparingByValue());

        if (lowestScoreGroup.isPresent()) {
            String parentGroup = lowestScoreGroup.get().getKey();
            output = "We recommend adding more products from the " + parentGroup + " food group.";
        } else {
            output = "You have a good variety of products in your cart.";
        }

        return output;
    }

    private List<ShopProduct> fetchProductsFromFoodGroup(String foodGroup) {
        // Implement logic to fetch products from the given food group
        // This is a placeholder implementation
        return List.of();
    }

}
