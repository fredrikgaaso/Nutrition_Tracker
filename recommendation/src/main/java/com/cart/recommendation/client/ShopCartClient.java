package com.cart.recommendation.client;

import com.cart.recommendation.dtos.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ShopCartClient {
    private final String restServiceUrl;
    private final RestTemplate restTemplate;

    public ShopCartClient(
            RestTemplateBuilder restTemplateBuilder, @Value("${shopcart.service.url}") final String url
    ){
        this.restTemplate = restTemplateBuilder.build();
        this.restServiceUrl = url;
    }
    public CartDTO remoteGetOneCart(Long cartId){
        String callUrl = restServiceUrl + "/cart/" + cartId;
        ResponseEntity<CartDTO> response;
        try{
            response = restTemplate.getForEntity(callUrl, CartDTO.class);
            log.info("returned " + response);
            log.info("Response received from external service: {}", response.getBody());
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
        return response.getBody();
    }

}
