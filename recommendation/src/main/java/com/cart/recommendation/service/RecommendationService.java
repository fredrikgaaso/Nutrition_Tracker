package com.cart.recommendation.service;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final ShopCartClient shopCartClient;

    private static final Map<String, String> ALLERGEN_TO_FOODGROUP = new HashMap<>();

    static {
        ALLERGEN_TO_FOODGROUP.put("gluten", "Korn- og bakevarer");
        ALLERGEN_TO_FOODGROUP.put("lactose", "Meieriprodukter");
        ALLERGEN_TO_FOODGROUP.put("nuts", "Nøtter og frø");
        ALLERGEN_TO_FOODGROUP.put("soy", "Sojaprodukter");
        ALLERGEN_TO_FOODGROUP.put("egg", "Egg og eggeprodukter");
        ALLERGEN_TO_FOODGROUP.put("fish", "Fisk");
        ALLERGEN_TO_FOODGROUP.put("shellfish", "Skalldyr");
        ALLERGEN_TO_FOODGROUP.put("milk", "Meieriprodukter");
        ALLERGEN_TO_FOODGROUP.put("wheat", "Korn- og bakevarer");
        ALLERGEN_TO_FOODGROUP.put("sesame", "Sesamfrø");
        ALLERGEN_TO_FOODGROUP.put("celery", "Grønnsaker");
    }


    public ShopCart getOneCart(Long cartId) {
        CartDTO cartDTO = shopCartClient.remoteGetOneCart(cartId);
        log.info("cartDTO received from client: {}", cartDTO);

        ShopCart response = cartDTO.getShopCart();
        log.info("Returning cart: {}", response);
        return response;

    }

    public void updateRecommendation(ShopCart shopCart) {
        log.info("Updating recommendation for cart: {}", shopCart);
        makeRecommendation(shopCart);
    }

    public List<String> makeRecommendation(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        if (products.isEmpty()) {
            return List.of("No products in the cart. Please add some products to get recommendations.");
        }

        log.info("Allergens in shopcart: {}", shopCart.getAllergens().toString());

        Set<String> restrictedFoodGroups = shopCart.getAllergens().stream()
                .map(allergen -> {
                    String foodGroup = ALLERGEN_TO_FOODGROUP.get(allergen);
                    log.info("Mapping allergen '{}' to food group '{}'", allergen, foodGroup);
                    return foodGroup;
                })
                .collect(Collectors.toSet());

        log.info("Restricted food groups: {}", restrictedFoodGroups);

        List<String> presentAllergens = products.stream()
                .flatMap(product -> {
                    log.info("Product food group: {}", product.getFoodGroup());
                    log.info("Product parent group: {}", product.getParentGroup());
                    return Stream.of(product.getFoodGroup(), product.getParentGroup());
                })
                .filter(restrictedFoodGroups::contains)
                .distinct()
                .toList();

        log.info("Allergens present in the cart: {}", presentAllergens);

        if (!presentAllergens.isEmpty()) {
            return List.of("The cart contains allergens: " + presentAllergens + ". Please remove these products to get recommendations.");
        }



        List<String> recommendations = new ArrayList<>();

        boolean hasFruitsOrVegetables = products.stream().anyMatch(product ->
                product.getFoodGroup().startsWith("Frukt") ||
                        product.getFoodGroup().startsWith("Bær") ||
                        product.getFoodGroup().startsWith("Grønnsaker")
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
                product.getFoodGroup().startsWith("Fisk") ||
                        product.getFoodGroup().startsWith("Skalldyr")
        );
        boolean hasRedMeat = products.stream().anyMatch(product ->
                product.getFoodGroup().startsWith("Kjøtt")
        );
        if (!hasFish) {
            recommendations.add("Choose fish and seafood more often than red meat. Eat as little processed meat as possible.");
        } else if (hasRedMeat) {
            recommendations.add("Reduce the intake of red meat and processed meat. ");
        }

        boolean hasDairy = products.stream().anyMatch(product ->
                product.getFoodGroup().equalsIgnoreCase("Meieriprodukter")
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
        return recommendations;
    }

}
