package com.cart.shoppingcart.service;

import com.cart.shoppingcart.model.cart;
import com.cart.shoppingcart.repo.cartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class cartService {
    private final cartRepo cartRepo;

    public cart getOneCartById(Long cartId) {
        return cartRepo.findById(cartId).get();
    }

    public cart createCart() {
        cart cart = new cart();
        return cartRepo.save(cart);
    }
}
