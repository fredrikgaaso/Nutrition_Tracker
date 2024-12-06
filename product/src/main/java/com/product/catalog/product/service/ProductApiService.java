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

    public ProductApiService(RestTemplateBuilder builder, ProductRepo productRepo) {
        this.restTemplate = builder.build();
        this.productRepo = productRepo;
    }


    public void fetchAndSaveProducts() {
        String foodApi = "https://www.matvaretabellen.no/api/nb/foods.json";
        String foodGroupApi = "https://www.matvaretabellen.no/api/nb/food-groups.json";

        try {
            ResponseEntity<FoodGroupWrapper> foodGroupResponse = restTemplate.exchange(
                    foodGroupApi, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}
            );

            List<FoodGroupResponse> foodGroups = Objects.requireNonNull(foodGroupResponse.getBody()).getFoodGroups();

            ResponseEntity<FoodResponseWrapper> foodResponse = restTemplate.exchange(
                    foodApi, HttpMethod.GET, null,
                    new ParameterizedTypeReference<>() {}
            );

            List<FoodResponse> foodResponses = Objects.requireNonNull(foodResponse.getBody()).getFoods();

            if (foodResponses != null) {
                List<Product> products = foodResponses.parallelStream()
                        .map(food -> {
                            Product product = new Product();
                            product.setProductName(food.getFoodName());
                            product.setCalories(food.getCalories().getQuantity() != null ? food.getCalories().getQuantity() : 0.0);

                            if (food.getFoodGroupId() != null && !food.getFoodGroupId().isEmpty()) {
                                foodGroups.stream()
                                        .filter(group -> group.getFoodGroupId() != null && group.getFoodGroupId().equals(food.getFoodGroupId()))
                                        .findFirst()
                                        .ifPresent(group -> product.setFoodGroup(group.getName()));
                            }

                            if (food.getConstituents() != null) {
                                List<Nutrient> nutrients = food.getConstituents().parallelStream()
                                        .filter(constituent -> constituent.getNutrientId().matches("Energi|Protein|Karbo|Sukker|Fett|Mettet|Salt"))
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
                        })
                        .collect(Collectors.toList());

                // Save products in batches
                int batchSize = 100;
                for (int i = 0; i < products.size(); i += batchSize) {
                    int end = Math.min(i + batchSize, products.size());
                    productRepo.saveAll(products.subList(i, end));
                }
            } else {
                log.warn("No foods found in API response");
            }
        } catch (Exception e) {
            log.error("Error fetching and saving products: {}", e.getMessage(), e);
        }
    }
    }