package com.product.shop.clients;

import com.product.shop.dtos.productDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class shopProductClient {
    private final String restServiceUrl;

    private final RestTemplate restTemplate;

    public shopProductClient(
            RestTemplateBuilder restTemplateBuilder, @Value("${shop.service.url}") final String url
    ){
        this.restTemplate = restTemplateBuilder.build();
        this.restServiceUrl = url;
    }

    public productDTO remoteGetOneProduct(Long shopId){
        String callUrl = restServiceUrl + "/products/" + shopId;

        ResponseEntity<productDTO> response;

        try {
            response = restTemplate.getForEntity(callUrl, productDTO.class);
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
