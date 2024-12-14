package com.cart.recommendation.service;

import com.cart.recommendation.client.ShopCartClient;
import com.cart.recommendation.dtos.CartDTO;
import com.cart.recommendation.model.Nutrient;
import com.cart.recommendation.model.RecommendationData;
import com.cart.recommendation.model.ShopCart;
import com.cart.recommendation.model.ShopProduct;
import com.cart.recommendation.repos.RecommendationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationService {

    private final ShopCartClient shopCartClient;

    private final RecommendationRepo recommendationRepo;

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    private static final Map<String, List<String>> ALLERGEN_TO_FOODGROUP = new HashMap<>();

    static {
        ALLERGEN_TO_FOODGROUP.put("gluten", List.of("Korn", "Brød"));
        ALLERGEN_TO_FOODGROUP.put("lactose", List.of("Meieriprodukter", "Melk", "Ost"));
        ALLERGEN_TO_FOODGROUP.put("nuts", List.of("Nøtter", "Frø"));
        ALLERGEN_TO_FOODGROUP.put("soy", List.of("Soja"));
        ALLERGEN_TO_FOODGROUP.put("egg", List.of("Egg"));
        ALLERGEN_TO_FOODGROUP.put("fish", List.of("Fisk"));
        ALLERGEN_TO_FOODGROUP.put("shellfish", List.of("Skalldyr"));
        ALLERGEN_TO_FOODGROUP.put("sesame", List.of("sesam"));
        ALLERGEN_TO_FOODGROUP.put("Vegan", List.of("kjøtt", "fisk", "melk", "meieriprodukter", "egg"));
    }

    public RecommendationData getRecommendations(Long cartId) {
        log.info("Recommendation updated");
        return recommendationRepo.findByShopCartId(cartId);
    }

    public RecommendationData setRecommendations(ShopCart shopCart) {
        log.info("Recommendation created");
        List<String> allergens = checkAllergen(shopCart);
        List<String> nutritionalValues = checkNutritionalValue(shopCart);
        List<String> recommendedProducts = makeRecommendation(shopCart);

        RecommendationData recommendationData = new RecommendationData();
        recommendationData.setShopCartId(shopCart.getId());
        recommendationData.setAllergens(allergens);
        recommendationData.setNutritionalValues(nutritionalValues);
        recommendationData.setRecommendedProducts(recommendedProducts);

        recommendationRepo.save(recommendationData);

        return recommendationData;
    }


    public ShopCart getOneCart(Long cartId) {
        CartDTO cartDTO = shopCartClient.remoteGetOneCart(cartId);
        log.info("cartDTO received from client: {}", cartDTO);

        ShopCart response = cartDTO.getShopCart();
        log.info("Returning cart: {}", response);
        return response;

    }

    public List<String> checkAllergen(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        log.info("Allergens in shopcart: {}", shopCart.getAllergens().toString());

        Set<String> restrictedFoodGroups = shopCart.getAllergens().stream()
                .flatMap(allergen -> {
                    List<String> foodGroups = ALLERGEN_TO_FOODGROUP.get(allergen);
                    if (foodGroups == null) {
                        log.warn("No food groups found for allergen '{}'", allergen);
                        return Stream.empty();
                    }
                    log.info("Mapping allergen '{}' to food groups '{}'", allergen, foodGroups);
                    return foodGroups.stream();
                })
                .collect(Collectors.toSet());

        log.info("Restricted food groups: {}", restrictedFoodGroups);

        List<String> presentAllergens = products.stream()
                .flatMap(product -> {
                    log.info("Product food group: {}", product.getFoodGroup());
                    log.info("Product parent group: {}", product.getParentGroup());
                    return Stream.of(product.getFoodGroup(), product.getParentGroup());
                })
                .filter(foodGroup -> foodGroup != null && restrictedFoodGroups.stream()
                        .anyMatch(restrictedFoodGroup -> foodGroup.toLowerCase().contains(restrictedFoodGroup.toLowerCase())))
                .distinct()
                .toList();

        log.info("Allergens present in the cart: {}", presentAllergens);
        return presentAllergens;
    }

    private double getNutrientValue(List<Nutrient> nutritionalInfo, String nutrientName) {
        return nutritionalInfo.stream()
                .filter(nutrient -> nutrient.getNutrientName().equalsIgnoreCase(nutrientName))
                .mapToDouble(Nutrient::getNutrientValue)
                .sum();
    }

    public List<String> checkNutritionalValue(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        double totalProtein = products.stream()
                .mapToDouble(product -> getNutrientValue(product.getNutritionalInfo(), "protein"))
                .sum();
        double totalCarbs = products.stream()
                .mapToDouble(product -> getNutrientValue(product.getNutritionalInfo(), "Karbo"))
                .sum();
        double totalFat = products.stream()
                .mapToDouble(product -> getNutrientValue(product.getNutritionalInfo(), "fett"))
                .sum();

        int desiredProtein = shopCart.getDesiredProtein();
        int desiredCarbs = shopCart.getDesiredCarbs();
        int desiredFat = shopCart.getDesiredFat();

        List<String> recommendations = new ArrayList<>();
        if (totalProtein < desiredProtein) {
            recommendations.add("You are missing " + DECIMAL_FORMAT.format(desiredProtein - totalProtein) + "g of protein in your diet.");
        } else if (totalProtein > desiredProtein) {
            recommendations.add("You have exceeded the recommended protein intake by " + DECIMAL_FORMAT.format(totalProtein - desiredProtein) + "g.");
        }
        if (totalCarbs < desiredCarbs) {
            recommendations.add("You are missing " + DECIMAL_FORMAT.format(desiredCarbs - totalCarbs) + "g of carbs in your diet.");
        } else if (totalCarbs > desiredCarbs) {
            recommendations.add("You have exceeded the recommended carb intake by " + DECIMAL_FORMAT.format(totalCarbs - desiredCarbs) + "g.");
        }
        if (totalFat < desiredFat) {
            recommendations.add("You are missing " + DECIMAL_FORMAT.format(desiredFat - totalFat) + "g of fat in your diet.");
        } else if (totalFat > desiredFat) {
            recommendations.add("You have exceeded the recommended fat intake by " + DECIMAL_FORMAT.format(totalFat - desiredFat) + "g.");
        }
        return recommendations;
    }

    public List<String> makeRecommendation(ShopCart shopCart) {
        List<ShopProduct> products = shopCart.getProductsList();
        if (products.size()<3) {
            return List.of("Add at least 3 products to get recommendations.");
        }

        List<String> recommendations = new ArrayList<>();

        boolean hasFruitsOrVegetables = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("frukt") ||
                        product.getFoodGroup().toLowerCase().contains("bær") ||
                        product.getFoodGroup().toLowerCase().contains("grønnsaker")
        );
        if (!hasFruitsOrVegetables) {
            recommendations.add("Include fruits, berries, or vegetables in all meals. ");
        }

        boolean hasWholeGrains = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("korn- og bakevarer")
        );
        if (!hasWholeGrains) {
            recommendations.add("Include whole grain bread or other whole grain products in several meals each day. ");
        }

        boolean hasFish = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("fisk") ||
                        product.getFoodGroup().toLowerCase().contains("skalldyr")
        );
        boolean hasRedMeat = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("kjøtt")
        );
        if (!hasFish) {
            recommendations.add("Choose fish and seafood more often than red meat. Eat as little processed meat as possible.");
        } else if (hasRedMeat) {
            recommendations.add("Reduce the intake of red meat and processed meat. ");
        }

        boolean hasDairy = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("meieriprodukter")
        );
        if (!hasDairy) {
            recommendations.add("Have a daily intake of milk and dairy products. Choose products with less fat. ");
        }

        boolean hasSweets = products.stream().anyMatch(product ->
                product.getFoodGroup().toLowerCase().contains("kjeks og småkaker") ||
                        product.getFoodGroup().toLowerCase().contains("sjokolode og godteri") ||
                        product.getFoodGroup().toLowerCase().contains("sukker og honning") ||
                        product.getFoodGroup().toLowerCase().contains("dessert og iskrem")
        );
        if (hasSweets) {
            recommendations.add("Limit candy, snacks, and sweet baked goods. ");
        }
        return recommendations;
    }

}
