package com.product.shop.service;

import com.product.shop.clients.ShopClient;
import com.product.shop.dtos.shopDTO;
import com.product.shop.model.shopProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductExample implements ProductInterface {

    private final ShopClient shopClient;
    @Override
    public shopProduct getOneProduct(Long shopId){
        log.info("Input product: {}", shopId);

        shopDTO s = shopClient.remoteGetOneProduct(shopId);

        log.info("shopDTO received from ShopClient: {}", s);


        shopProduct response = s.returnText();

        log.info("Returning product: {}", response);
        return response;
    }

}
