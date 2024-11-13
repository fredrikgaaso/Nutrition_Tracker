package com.product.shop.controller;

import com.product.shop.dtos.ProductDTO;
import com.product.shop.model.ShopCart;
import com.product.shop.model.ShopProduct;
import com.product.shop.model.ShopUser;
import com.product.shop.service.ProductShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ProductShopService shopService;

    @GetMapping("/{productId}")
    public ShopProduct findOneProductById(@PathVariable long productId) {

        log.info("Calling productExample.getText() with Sometext: {}", productId);
        ShopProduct shopProduct = shopService.getOneProduct(productId);

        if (shopProduct == null) {
            log.error("ProductExample returned null for Sometext");
            throw new IllegalStateException("The returned Sometext is null");
        }

        log.info("Received response: id={}, name={}, nutrition={}, calories={}", shopProduct.getId(), shopProduct.getProductName(), shopProduct.getNutritionalInfo(), shopProduct.getCalories());
        return shopProduct;
    }

    @GetMapping("/user/{userId}")
    public ShopUser findOneUserById(@PathVariable long userId) {
        ShopUser shopUser = shopService.getOneUser(userId);

        if (shopUser == null) {
            log.error("getOneUser returned null");
            throw new IllegalStateException("The returned user is null");
        }
        log.info("Received response: id={}, name={}, wallet={}", shopUser.getId(), shopUser.getName(), shopUser.getWallet());
        return shopUser;
    }

    @GetMapping("/cart/{cartId}")
    public ShopCart findOneCartById(@PathVariable Long cartId) {
        return shopService.getOneShopCart(cartId);
    }

    @PostMapping("/cart/create/{userId}")
    public ShopCart createNewCart(@PathVariable Long userId) {
        return shopService.createNewCart(userId);
    }

    @PostMapping("/cart/add/{cartId}/{productId}")
    public void addProductToCart(@PathVariable Long cartId,@PathVariable Long productId) {
        shopService.addProductToCart(cartId, productId);
    }

    @GetMapping("/all")
    public List<ShopProduct> findAll() {
    return shopService.getAllProducts();
    }
}
