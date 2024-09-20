package com.product.shop.service;

import com.product.shop.clients.shopProductClient;
import com.product.shop.clients.shopUserClient;
import com.product.shop.dtos.productDTO;
import com.product.shop.dtos.userDTO;
import com.product.shop.model.shopCart;
import com.product.shop.model.shopProduct;
import com.product.shop.model.shopUser;
import com.product.shop.repos.shopCartRepo;
import com.product.shop.repos.shopProductRepo;
import com.product.shop.repos.shopUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductShopService {
    private final shopProductClient productClient;
    private final shopUserClient userClient;
    private final shopUserRepo userRepo;
    private final shopProductRepo productRepo;

    public shopProduct getOneProduct(Long shopId){
        log.info("Input product: {}", shopId);

        productDTO s = productClient.remoteGetOneProduct(shopId);

        log.info("shopDTO received from ShopClient: {}", s);


        shopProduct response = s.getProduct();

        log.info("Returning product: {}", response);

        productRepo.save(response);

        return response;
    }


    public shopUser getOneUser(Long userId){
        log.info("Input user: {}", userId);

        userDTO s = userClient.remoteGetOneUser(userId);
        log.info("userDTO received from userClient: {}", s);

        shopUser response = s.getUser();

        log.info("Returning user: {}", response);

        userRepo.save(response);

        return response;
    }

}
