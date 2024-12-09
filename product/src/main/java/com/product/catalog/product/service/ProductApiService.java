package com.product.catalog.product.service;

import com.product.catalog.product.model.*;
import com.product.catalog.product.repos.ProductRepo;
import com.product.catalog.product.response.FoodGroupResponse;
import com.product.catalog.product.response.FoodGroupWrapper;
import com.product.catalog.product.response.FoodResponse;
import com.product.catalog.product.response.FoodResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductApiService {

    private final RestTemplate restTemplate;
    private final ProductRepo productRepo;

    private final String filterConstituents = "Energi|Protein|Karbo|Sukker|Fett|Mettet|Salt";

    public ProductApiService(RestTemplateBuilder builder, ProductRepo productRepo) {
        this.restTemplate = builder.build();
        this.productRepo = productRepo;
    }

    public void fetchAndSaveProducts() {
        String foodApi = "https://www.matvaretabellen.no/api/nb/foods.json";
        String foodGroupApi = "https://www.matvaretabellen.no/api/nb/food-groups.json";

        try {
            List<FoodGroupResponse> foodGroups = fetchFoodGroups(foodGroupApi);
            List<FoodResponse> foodResponses = fetchFoodResponses(foodApi);

            if (foodResponses != null) {
                List<Product> products = foodResponses.parallelStream()
                        .map(food -> mapToProduct(food, foodGroups))
                        .collect(Collectors.toList());

                saveProductsInBatches(products, 100);
            } else {
                log.warn("No foods found in API response");
            }
        } catch (Exception e) {
            log.error("Error fetching and saving products: {}", e.getMessage(), e);
        }
    }

    private List<FoodGroupResponse> fetchFoodGroups(String foodGroupApi) {
        ResponseEntity<FoodGroupWrapper> response = restTemplate.exchange(
                foodGroupApi, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody()).getFoodGroups();
    }

    private List<FoodResponse> fetchFoodResponses(String foodApi) {
        ResponseEntity<FoodResponseWrapper> response = restTemplate.exchange(
                foodApi, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}
        );
        return Objects.requireNonNull(response.getBody()).getFoods();
    }

    private Product mapToProduct(FoodResponse food, List<FoodGroupResponse> foodGroups) {
        Product product = new Product();
        product.setProductName(food.getFoodName());
        product.setCalories(food.getCalories().getQuantity() != null ? food.getCalories().getQuantity() : 0.0);

        foodGroups.stream()
                .filter(group -> group.getFoodGroupId() != null && group.getFoodGroupId().equals(food.getFoodGroupId()))
                .findFirst()
                .ifPresent(group -> {
                    product.setFoodGroup(group.getName());
                    foodGroups.stream()
                            .filter(parentGroup -> parentGroup.getFoodGroupId().equals(group.getParentId()))
                            .findFirst()
                            .ifPresent(parentGroup -> product.setParentGroup(parentGroup.getName()));
                });

        if (food.getConstituents() != null) {
            List<Nutrient> nutrients = food.getConstituents().parallelStream()
                    .filter(constituent -> constituent.getNutrientId().matches(filterConstituents))
                    .map(constituent -> {
                        Nutrient nutrient = new Nutrient();
                        nutrient.setNutrientName(constituent.getNutrientId());
                        nutrient.setNutrientValue(constituent.getQuantity() != null ? constituent.getQuantity() : 0.0);
                        return nutrient;
                    })
                    .collect(Collectors.toList());
            product.setNutritionalInfo(nutrients);
        }
        return product;
    }

    private void saveProductsInBatches(List<Product> products, int batchSize) {
        for (int i = 0; i < products.size(); i += batchSize) {
            int end = Math.min(i + batchSize, products.size());
            productRepo.saveAll(products.subList(i, end));
        }
    }
}