package com.product.catalog.product.service;

import com.product.catalog.product.model.Nutrient;
import com.product.catalog.product.model.Product;
import com.product.catalog.product.repos.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductApiService {

    private RestTemplate restTemplate;
    private ProductRepo productRepo;

    public ProductApiService(RestTemplateBuilder builder, ProductRepo productRepo) {
        this.restTemplate = builder.build();
        this.productRepo = productRepo;
    }


    public void fetchAndSaveProducts(){
        String apiUrl = "https://www.matvaretabellen.no/api/nb/foods.json";
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        try {
            response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("foods")) {
                List<Map<String, Object>> foods = (List<Map<String, Object>>) response.get("foods");

                for (Map<String, Object> food : foods) {
                    String productName = (String) food.get("foodName");

                    Product product = new Product();
                    product.setProductName(productName);

                    Map<String, Object> nutrientMap = (Map<String, Object>) food.get("nutrientsInfo");
                    if (nutrientMap != null) {
                        List<Nutrient> nutrientsList = extractNutritionalInfo(nutrientMap);
                        product.setNutritionalInfo(nutrientsList);
                    } else {
                        log.info("Nutrients info is missing for product: " + productName);
                    }

                    productRepo.save(product);
                }
            } else {
                log.info("No 'foods' found in the response or response is null");
            }
        } catch (Exception e) {
            log.error("Error fetching products: " + e.getMessage());
        }
    }
    private List<Nutrient> extractNutritionalInfo(Map<String, Object> nutrients) {
        List<Nutrient> nutrientList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : nutrients.entrySet()) {
            Nutrient nutrient = new Nutrient();
            nutrient.setNutrientName(entry.getKey());
            nutrient.setNutrientValue((Double) entry.getValue());

            nutrientList.add(nutrient);
        }
        return nutrientList;
    }
}
