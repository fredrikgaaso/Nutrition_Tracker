package com.product.shop.controller;

import com.product.shop.model.ShopCart;
import com.product.shop.model.ShopProduct;
import com.product.shop.model.ShopUser;
import com.product.shop.service.ProductShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
@CrossOrigin(origins = "http://localhost:1234")
public class ShopController {

    private final ProductShopService shopService;

    @GetMapping("/{id}")
    public ShopProduct findOneProductById(@PathVariable long id) {

        log.info("Calling productExample.getText() with Sometext: {}", id);
        ShopProduct shopProduct = shopService.getOneProduct(id);

        if (shopProduct == null) {
            log.error("ProductExample returned null for Sometext");
            throw new IllegalStateException("The returned Sometext is null");
        }

        log.info("Received response: id={}, name={}, nutrition={}, calories={}", shopProduct.getId(), shopProduct.getProductName(), shopProduct.getNutritionalInfo(), shopProduct.getCalories());
        return shopProduct;
    }

    @GetMapping("/user/{id}")
    public ShopUser findOneUserById(@PathVariable long id) {
        ShopUser shopUser = shopService.getOneUser(id);

        if (shopUser == null) {
            log.error("getOneUser returned null");
            throw new IllegalStateException("The returned user is null");
        }
        log.info("Received response: id={}, name={}, wallet={}", shopUser.getId(), shopUser.getName(), shopUser.getWallet());

        return shopUser;
    }

    @GetMapping("/cart/{id}")
    public ShopCart findOneCartById(@PathVariable Long id) {
        return shopService.getOneShopCart(id);
    }

    @PostMapping("/cart/create/{userId}")
    public ShopCart createNewCart(@PathVariable Long userId) {
        return shopService.createNewCart(userId);
    }

    @PostMapping("/cart/add/{cartId}/{productId}")
    public void addProductToCart(@PathVariable Long cartId,@PathVariable Long productId) {
        shopService.addProductToCart(cartId, productId);
    }
}
