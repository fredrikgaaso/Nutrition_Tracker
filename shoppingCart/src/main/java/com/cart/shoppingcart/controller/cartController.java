package com.cart.shoppingcart.controller;

import com.cart.shoppingcart.model.cart;
import com.cart.shoppingcart.service.cartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class cartController {
    private final cartService cartService;

    @GetMapping("/{cartId}")
    public cart getCart(@PathVariable("cartId") Long cartId) {
        return cartService.getOneCartById(cartId);
    }

    @PostMapping("/create")
    public cart createCart() {
        return cartService.createCart();
    }

}
