package com.product.catalog.product.service;

import com.product.catalog.product.model.*;
import com.product.catalog.product.repos.ProductRepo;
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
        String apiUrl = "https://www.matvaretabellen.no/api/nb/foods.json";

        try {
            // Use RestTemplate to fetch the API response
            ResponseEntity<FoodResponseWrapper> response = restTemplate.exchange(
                    apiUrl, HttpMethod.GET, null,
                    new ParameterizedTypeReference<FoodResponseWrapper>() {}
            );

            // Extract the list of foods
            List<FoodResponse> foodResponses = Objects.requireNonNull(response.getBody()).getFoods();

            // Process and save each food
            if (foodResponses != null) {
                for (FoodResponse food : foodResponses) {
                    Product product = new Product();
                    product.setProductName(food.getFoodName());
                    product.setCalories(food.getCalories().getQuantity() != null ? food.getCalories().getQuantity() : 0.0);

                    if (food.getConstituents() != null) {
                        List<Nutrient> nutrients = food.getConstituents().stream()
                                .map(constituent -> {
                                    Nutrient nutrient = new Nutrient();
                                    nutrient.setNutrientName(constituent.getNutrientId());
                                    nutrient.setNutrientValue(constituent.getQuantity() != null ? constituent.getQuantity() : 0.0);
                                    return nutrient;
                                })
                                .collect(Collectors.toList());
                        product.setNutritionalInfo(nutrients);
                    }

                    productRepo.save(product);
                }
            } else {
                log.warn("No foods found in API response");
            }
        } catch (Exception e) {
            log.error("Error fetching and saving products: {}", e.getMessage(), e);
        }
    }
    }