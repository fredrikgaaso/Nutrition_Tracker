package com.cart.shopcart.clients;

import com.cart.shopcart.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ShopProductClient {
    private final String restServiceUrl;


    private final RestTemplate restTemplate;

    public ShopProductClient(
            RestTemplateBuilder restTemplateBuilder, @Value("${product.service.url}") final String url
    ){
        this.restTemplate = restTemplateBuilder.build();
        this.restServiceUrl = url;
    }

    public ProductDTO remoteGetOneProduct(Long productId){
        String callUrl = restServiceUrl + "/products/" + productId;

        ResponseEntity<ProductDTO> response;

        try {
            response = restTemplate.getForEntity(callUrl, ProductDTO.class);
            log.info("returned " + response);
            log.info("Response received from external service: {}", response.getBody());
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }

        return response.getBody();
    }


}