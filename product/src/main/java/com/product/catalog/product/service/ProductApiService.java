package com.product.catalog.product.service;

import com.product.catalog.product.model.Nutrient;
import com.product.catalog.product.model.Product;
import com.product.catalog.product.repos.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductApiService {

    private final RestTemplate restTemplate;
    private final ProductRepo productRepo;

    public ProductApiService(RestTemplateBuilder builder, ProductRepo productRepo) {
        this.restTemplate = builder.build();
        this.productRepo = productRepo;
    }


    public void fetchAndSaveProducts(){

        String apiUrl = "https://www.matvaretabellen.no/api/nb/foods.json";
        restTemplate.getForObject(apiUrl, Map.class);
        Map response;
        try {
            response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("foods")) {

                Object products = response.get("foods");

                List<Map<String, Object>> foods = (List<Map<String, Object>>) products;

                for (Map<String, Object> food : foods) {
                    String productName = (String) food.get("foodName");

                    Product product = new Product();
                    double calories = extractCalories(food);

                    product.setProductName(productName);
                    product.setCalories(calories);
                    Object nutrientsInfo = food.get("constituents");

                    List<Map<String, Object>> constituents = (List<Map<String, Object>>) nutrientsInfo;
                    if (constituents != null) {
                        List<Nutrient> nutrientsList = extractNutritionalInfo(constituents);
                        product.setNutritionalInfo(nutrientsList);
                    } else {
                        log.warn("Nutrients info is missing for product: {}", productName);
                    }

                    productRepo.save(product);
                }
            } else {
                log.info("No 'foods' found in the response or response is null");
            }
        } catch (Exception e) {
            log.error("Error fetching products: {}", e.getMessage());
        }
    }
    private List<Nutrient> extractNutritionalInfo(List<Map<String, Object>> constituents) {
        List<Nutrient> nutrientList = new ArrayList<>();

        for (Map<String, Object> constituent : constituents) {

            Nutrient nutrient = new Nutrient();
            String nutrientId = (String) constituent.get("nutrientId");

            if (constituent.get("quantity") != null) {
                double quantity = (Double) constituent.get("quantity");
                nutrient.setNutrientValue(quantity);

            } else {
                nutrient.setNutrientValue(0.0);
            }

            nutrient.setNutrientName(nutrientId);
            nutrientList.add(nutrient);
        }
        return nutrientList;
    }

    private double extractCalories(Map<String, Object> food) {
        Map<String, Object> caloriesMap = (Map<String, Object>) food.get("calories");

        if (caloriesMap != null && caloriesMap.get("quantity") != null) {
            Object calories = caloriesMap.get("quantity");
            return((Number) calories).doubleValue();
        }
        log.info("Calories not found in the response or response is null: {}", caloriesMap);

        return 0.0;
    }
}
