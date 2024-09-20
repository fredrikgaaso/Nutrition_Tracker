package com.product.shop.controller;

import com.product.shop.model.shopCart;
import com.product.shop.model.shopUser;
import com.product.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class shopCartController {
    private final CartService cartService;

    @GetMapping("/{id}")
    public shopCart getOneShopCart(@PathVariable Long id) {
        return cartService.getOneShopCart(id);
    }
    @PostMapping("/create")
    public shopCart createNewCart(@RequestBody Long userId) {
        return cartService.createNewCart(userId);
    }
    @PostMapping("/add")
    public void addProductToCart(@RequestBody shopCart cart, Long productId) {
        cartService.addProductToCart(cart, productId);
    }
}
