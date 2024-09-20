package com.product.shop.service;

import com.product.shop.clients.shopProductClient;
import com.product.shop.clients.shopUserClient;
import com.product.shop.dtos.productDTO;
import com.product.shop.dtos.userDTO;
import com.product.shop.model.shopCart;
import com.product.shop.repos.shopCartRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {
    private final shopProductClient productClient;
    private final shopUserClient userClient;
    private final shopCartRepo cartRepo;

    public shopCart createNewCart(Long userId) {
        userDTO userDTO = userClient.remoteGetOneUser(userId);
        log.info("user got: " + userDTO.getName());

        if (userDTO == null || userDTO.getUser() == null) {
            throw new RuntimeException("User not found");
        }
        shopCart cart = new shopCart();
        log.info("user got: " + userDTO.getName());
        cart.setUser(userDTO.getUser());
        return cartRepo.save(cart);
    }
    public void addProductToCart(shopCart cart, Long productId) {
        productDTO product = productClient.remoteGetOneProduct(productId);
        cartRepo.save(cart);
    }
    public shopCart getOneShopCart(Long id) {
       return cartRepo.findOneCartById(id);
    }



}
