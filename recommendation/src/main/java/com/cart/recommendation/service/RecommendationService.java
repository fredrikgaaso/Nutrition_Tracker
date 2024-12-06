package com.cart.recommendation.service;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> makeRecommendation(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        if (products.isEmpty()) {
            return List.of("No products in the cart. Please add some products to get recommendations.");
        }

        List<String> recommendations = new ArrayList<>();

        boolean hasFruitsOrVegetables = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Frukt og Bær") ||
                        product.getFoodGroup().equalsIgnoreCase("Grønnsaker")
        );
        if (!hasFruitsOrVegetables) {
            recommendations.add("Include fruits, berries, or vegetables in all meals. ");
        }

        boolean hasWholeGrains = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Korn- og bakevarer")
        );
        if (!hasWholeGrains) {
            recommendations.add("Include whole grain bread or other whole grain products in several meals each day. ");
        }

        boolean hasFish = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Fisk og skalldyr") ||
                        product.getFoodGroup().equalsIgnoreCase("Fisk") ||
                        product.getFoodGroup().equalsIgnoreCase("Skalldyr") ||
                        product.getFoodGroup().equalsIgnoreCase("Fiskeprodukter")
        );
        boolean hasRedMeat = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Kjøtt og fjørfre") ||
                        product.getFoodGroup().equalsIgnoreCase("Kjøttprodukter")
        );
        if (!hasFish) {
            recommendations.add("Choose fish and seafood more often than red meat. Eat as little processed meat as possible. ");
        } else if (hasRedMeat) {
            recommendations.add("Reduce the intake of red meat and processed meat. ");
        }

        boolean hasDairy = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("dairy")
        );
        if (!hasDairy) {
            recommendations.add("Have a daily intake of milk and dairy products. Choose products with less fat. ");
        }

        boolean hasSweets = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Kjeks og småkaker") ||
                        product.getFoodGroup().equalsIgnoreCase("Sjokolade og godteri") ||
                        product.getFoodGroup().equalsIgnoreCase("Sukker og honning") ||
                        product.getFoodGroup().equalsIgnoreCase("Dessert og iskrem")

        );
        if (hasSweets) {
            recommendations.add("Limit candy, snacks, and sweet baked goods. ");
        }

        boolean hasWater = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("water")
        );
        if (!hasWater) {
            recommendations.add("Drink water!");
        }

        if (recommendations.isEmpty()) {
            recommendations.add("Your cart looks great! Keep up the good work.");
        }

        return recommendations;
    }

    private List<ShopProduct> fetchProductsFromFoodGroup(String foodGroup) {
        // Implement logic to fetch products from the given food group
        // This is a placeholder implementation
        return List.of();
    }

}